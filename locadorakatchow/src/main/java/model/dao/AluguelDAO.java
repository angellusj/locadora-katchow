package model.dao;

import model.db.DB;
import model.entity.Aluguel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AluguelDAO {

    /**
     * Registra um novo aluguel e marca o automóvel como indisponível.
     */
    public static void cadastrar(Aluguel a) {
        String sqlAluguel = """
            INSERT INTO aluguel (data_inicio, data_fim, valor_total, status,
                                 id_cliente, id_funcionario, id_automovel)
            VALUES (?,?,?,?,?,?,?)
            """;
        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement ps = conn.prepareStatement(sqlAluguel, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setDate(1, Date.valueOf(a.getDataInicio()));
                    ps.setDate(2, Date.valueOf(a.getDataFim()));
                    ps.setDouble(3, a.getValorTotal());
                    ps.setString(4, a.getStatus());
                    ps.setInt(5, a.getIdCliente());
                    ps.setInt(6, a.getIdFuncionario());
                    ps.setInt(7, a.getIdAutomovel());
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) a.setId(rs.getInt(1));
                    }
                }
                // Marca automóvel como indisponível
                try (PreparedStatement ps = conn.prepareStatement(
                        "UPDATE automovel SET disponivel = FALSE WHERE id = ?")) {
                    ps.setInt(1, a.getIdAutomovel());
                    ps.executeUpdate();
                }
                conn.commit();
                System.out.println("Aluguel registrado! ID: " + a.getId());
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao registrar aluguel: " + e.getMessage());
        }
    }

    /**
     * Registra devolução: atualiza status e data_fim, libera o automóvel.
     */
    public static void registrarDevolucao(int idAluguel, LocalDate dataEntrega, double valorFinal) {
        String sql = """
            UPDATE aluguel SET data_fim = ?, valor_total = ?, status = 'devolvido'
             WHERE id = ?
            """;
        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int idAutomovel;
                try (PreparedStatement ps = conn.prepareStatement(
                        "SELECT id_automovel FROM aluguel WHERE id = ?")) {
                    ps.setInt(1, idAluguel);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) { System.out.println("Aluguel não encontrado."); return; }
                        idAutomovel = rs.getInt("id_automovel");
                    }
                }
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setDate(1, Date.valueOf(dataEntrega));
                    ps.setDouble(2, valorFinal);
                    ps.setInt(3, idAluguel);
                    ps.executeUpdate();
                }
                // Libera automóvel
                try (PreparedStatement ps = conn.prepareStatement(
                        "UPDATE automovel SET disponivel = TRUE WHERE id = ?")) {
                    ps.setInt(1, idAutomovel);
                    ps.executeUpdate();
                }
                conn.commit();
                System.out.println("Devolução registrada!");
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao registrar devolução: " + e.getMessage());
        }
    }

    /**
     * Remove um aluguel e libera o automóvel associado (se não devolvido).
     */
    public static void removerAluguel(int idAluguel) {
        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Busca id_automovel e status antes de deletar
                int idAutomovel = -1;
                boolean deveLiberar = false;
                try (PreparedStatement ps = conn.prepareStatement(
                        "SELECT id_automovel, status FROM aluguel WHERE id = ?")) {
                    ps.setInt(1, idAluguel);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) { System.out.println("Aluguel não encontrado."); return; }
                        idAutomovel = rs.getInt("id_automovel");
                        deveLiberar = !"devolvido".equalsIgnoreCase(rs.getString("status"));
                    }
                }
                // Remove o aluguel
                try (PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM aluguel WHERE id = ?")) {
                    ps.setInt(1, idAluguel);
                    ps.executeUpdate();
                }
                // Se ainda estava ativo, libera o automóvel
                if (deveLiberar) {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "UPDATE automovel SET disponivel = TRUE WHERE id = ?")) {
                        ps.setInt(1, idAutomovel);
                        ps.executeUpdate();
                    }
                }
                conn.commit();
                System.out.println("Aluguel removido!");
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover aluguel: " + e.getMessage());
        }
    }

    public static List<Aluguel> listarTodos() {
        return listar("ORDER BY a.data_inicio DESC", null, null);
    }

    public static List<Aluguel> listarEmAberto() {
        return listar("WHERE a.status != 'devolvido' ORDER BY a.data_fim", null, null);
    }

    public static List<Aluguel> listarPorCliente(int idCliente) {
        return listar("WHERE a.id_cliente = ? ORDER BY a.data_inicio DESC", "id_cliente", idCliente);
    }

    public static List<Aluguel> listarPorAutomovel(int idAutomovel) {
        return listar("WHERE a.id_automovel = ? ORDER BY a.data_inicio DESC", "id_automovel", idAutomovel);
    }

    private static List<Aluguel> listar(String clausula, String campo, Integer param) {
        String sql = """
            SELECT a.*,
                   u_c.nome AS nome_cliente,
                   u_f.nome AS nome_funcionario
              FROM aluguel a
              JOIN cliente     c   ON c.id_cliente    = a.id_cliente
              JOIN usuario     u_c ON u_c.id          = c.id_usuario
              JOIN funcionario f   ON f.id_funcionario = a.id_funcionario
              JOIN usuario     u_f ON u_f.id          = f.id_usuario
            """ + clausula;
        List<Aluguel> lista = new ArrayList<>();
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (param != null) ps.setInt(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Aluguel al = mapear(rs);
                    al.setAutomovel(AutomovelDAO.buscarAutomovel(al.getIdAutomovel()));
                    lista.add(al);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar aluguéis: " + e.getMessage());
        }
        return lista;
    }

    private static Aluguel mapear(ResultSet rs) throws SQLException {
        Aluguel a = new Aluguel(
            rs.getInt("id"),
            rs.getDate("data_inicio").toLocalDate(),
            rs.getDate("data_fim").toLocalDate(),
            rs.getDouble("valor_total"),
            rs.getString("status"),
            rs.getInt("id_cliente"),
            rs.getInt("id_funcionario"),
            rs.getInt("id_automovel")
        );
        a.setNomeCliente(rs.getString("nome_cliente"));
        a.setNomeFuncionario(rs.getString("nome_funcionario"));
        return a;
    }
}
