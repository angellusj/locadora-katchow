package model.dao;

import model.db.DB;
import model.entity.Automovel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutomovelDAO {

    public static void cadastrarAutomovel(Automovel automovel) {

        String sql = """
                INSERT INTO automovel
                (placa, marca, modelo, ano, disponivel, valor_diaria)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DB.getConnection()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, automovel.getPlaca());
                pstmt.setString(2, automovel.getMarca());
                pstmt.setString(3, automovel.getModelo());
                pstmt.setInt(4, automovel.getAno());
                pstmt.setBoolean(5, automovel.isDisponivel());
                pstmt.setDouble(6, automovel.getValorDiaria());
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    automovel.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static Automovel buscarAutomovel(int idAutomovel) {

        String sql = """
                SELECT *
                FROM automovel
                WHERE id = ?
                """;

        try (Connection conn = DB.getConnection()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idAutomovel);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return new Automovel(
                            rs.getInt("id"),
                            rs.getString("placa"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getInt("ano"),
                            rs.getBoolean("disponivel"),
                            rs.getDouble("valor_diaria")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Automovel buscarPorPlaca(String placa) {

        String sql = """
        SELECT *
        FROM automovel
        WHERE placa = ?
        """;

        try (Connection conn = DB.getConnection()) {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, placa);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Automovel(
                        rs.getInt("id"),
                        rs.getString("placa"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano"),
                        rs.getBoolean("disponivel"),
                        rs.getDouble("valor_diaria")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Automovel> listaAutomoveis() {

        List<Automovel> automoveis = new ArrayList<>();

        String sql = """
                SELECT *
                FROM automovel
                ORDER BY marca, modelo
                """;

        try (Connection conn = DB.getConnection()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    automoveis.add(
                            new Automovel(
                                    rs.getInt("id"),
                                    rs.getString("placa"),
                                    rs.getString("marca"),
                                    rs.getString("modelo"),
                                    rs.getInt("ano"),
                                    rs.getBoolean("disponivel"),
                                    rs.getDouble("valor_diaria")
                            )
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return automoveis;
    }

    public static List<Automovel> listarDisponiveis() {

        List<Automovel> automoveis =
                new ArrayList<>();

        String sql = """
        SELECT *
        FROM automovel
        WHERE disponivel = TRUE
        ORDER BY marca, modelo
        """;

        try (Connection conn = DB.getConnection()) {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                automoveis.add(
                        new Automovel(
                                rs.getInt("id"),
                                rs.getString("placa"),
                                rs.getString("marca"),
                                rs.getString("modelo"),
                                rs.getInt("ano"),
                                rs.getBoolean("disponivel"),
                                rs.getDouble("valor_diaria")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return automoveis;
    }

    public static void atualizarAutomovel(Automovel automovel) {

        String sql = """
                UPDATE automovel
                SET placa = ?,
                    marca = ?,
                    modelo = ?,
                    ano = ?,
                    disponivel = ?,
                    valor_diaria = ?
                WHERE id = ?
                """;

        try (Connection conn = DB.getConnection()) {

            assert conn != null;

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, automovel.getPlaca());
                pstmt.setString(2, automovel.getMarca());
                pstmt.setString(3, automovel.getModelo());
                pstmt.setInt(4, automovel.getAno());
                pstmt.setBoolean(5, automovel.isDisponivel());
                pstmt.setDouble(6, automovel.getValorDiaria());
                pstmt.setInt(7, automovel.getId());

                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removerAutomovel(Automovel automovel) {

        String sql = "DELETE FROM automovel WHERE id = ?";

        try (Connection conn = DB.getConnection()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, automovel.getId());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}