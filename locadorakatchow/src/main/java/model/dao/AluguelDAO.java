package model.dao;

import model.db.DB;
import model.entity.Aluguel;
import model.entity.Automovel;
import model.entity.Cliente;
import model.entity.Funcionario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AluguelDAO {

    public static Aluguel cadastrarAluguel(Aluguel a) {

        String sql = """
            INSERT INTO aluguel
            (data_inicio, data_fim, valor_total, status,
             id_cliente, id_funcionario, id_automovel)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DB.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setDate(1, Date.valueOf(a.getDataInicio()));
                ps.setDate(2, Date.valueOf(a.getDataFim()));
                ps.setDouble(3, a.getValorTotal());
                ps.setString(4, a.getStatus());
                ps.setInt(5, a.getCliente().getIdCliente());
                ps.setInt(6, a.getFuncionario().getIdFuncionario());
                ps.setInt(7, a.getAutomovel().getId());
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    a.setIdAluguel(rs.getInt(1));
                }
            }
            return a;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void registrarDevolucao(
            int idAluguel,
            LocalDate dataEntrega,
            double valorFinal) {

        String sql = """
            UPDATE aluguel
               SET data_fim = ?,
                   valor_total = ?,
                   status = 'devolvido'
             WHERE id = ?
            """;

        try (Connection conn = DB.getConnection()) {
            assert conn != null;
            conn.setAutoCommit(false);
            try {
                int idAutomovel;
                try (PreparedStatement ps = conn.prepareStatement("SELECT id_automovel FROM aluguel WHERE id = ?")) {
                    ps.setInt(1, idAluguel);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println("Aluguel não encontrado.");
                            return;
                        }
                        idAutomovel = rs.getInt("id_automovel");
                    }
                }
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setDate(1, Date.valueOf(dataEntrega));
                    ps.setDouble(2, valorFinal);
                    ps.setInt(3, idAluguel);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement("UPDATE automovel SET disponivel = TRUE WHERE id = ?")) {
                    ps.setInt(1, idAutomovel);
                    ps.executeUpdate();
                }
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void removerAluguel(Aluguel aluguel) {

        String sql = "DELETE FROM aluguel WHERE id = ?";

        try (Connection conn = DB.getConnection()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, aluguel.getIdAluguel());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static List<Aluguel> listarTodos() {
        return listar(
                "ORDER BY a.data_inicio DESC",
                null
        );
    }

    public static List<Aluguel> listarEmAberto() {
        return listar(
                "WHERE a.status <> 'devolvido' ORDER BY a.data_fim",
                null
        );
    }

    public static List<Aluguel> listarPorCliente(int idCliente) {
        return listar(
                "WHERE a.id_cliente = ? ORDER BY a.data_inicio DESC",
                idCliente
        );
    }

    public static List<Aluguel> listarPorAutomovel(int idAutomovel) {
        return listar(
                "WHERE a.id_automovel = ? ORDER BY a.data_inicio DESC",
                idAutomovel
        );
    }

    private static List<Aluguel> listar(
            String clausula,
            Integer param) {

        String sql = """
            SELECT *
            FROM aluguel a
            """ + clausula;

        List<Aluguel> lista = new ArrayList<>();

        try (Connection conn = DB.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                if (param != null) {ps.setInt(1, param);}
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {lista.add(mapear(rs));}
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return lista;
    }

    private static Aluguel mapear(ResultSet rs)
            throws SQLException {

        Cliente cliente =
                ClienteDAO.buscarCliente(rs.getInt("id_cliente"));

        Funcionario funcionario =
                FuncionarioDAO.buscarFuncionario(
                        rs.getInt("id_funcionario"));

        Automovel automovel =
                AutomovelDAO.buscarAutomovel(
                        rs.getInt("id_automovel"));

        return new Aluguel(
                rs.getInt("id"),
                rs.getDate("data_inicio").toLocalDate(),
                rs.getDate("data_fim").toLocalDate(),
                rs.getDouble("valor_total"),
                rs.getString("status"),
                cliente,
                funcionario,
                automovel
        );
    }
}