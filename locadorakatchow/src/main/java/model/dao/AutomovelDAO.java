package model.dao;
import model.db.DB;
import model.entity.Automovel;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutomovelDAO {

    public static void cadastrarAutomovel(Automovel automovel) {
        var sql = """
                INSERT INTO automovel (marca, modelo, ano, disponivel, valor_diaria)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (PreparedStatement pstmt =
                         conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, automovel.getMarca());
                pstmt.setString(2, automovel.getModelo());
                pstmt.setInt(3, automovel.getAno());
                pstmt.setBoolean(4, automovel.getDisponivel());
                pstmt.setFloat(5, automovel.getValorDiaria());
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) automovel.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static Automovel buscarAutomovel(int idAutomovel){
        var sql = "SELECT id_automovel, marca, modelo, ano, disponivel, valor_diaria FROM automovel WHERE id_automovel = ?;";
        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (var pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idAutomovel);
                var rs = pstmt.executeQuery();

                if (rs.next()) {
                    int id = rs.getInt("id_automovel");
                    String marca = rs.getString("marca");
                    String modelo = rs.getString("modelo");
                    int ano = rs.getInt("ano");
                    boolean disponivel = rs.getBoolean("disponivel");
                    float valor_diaria = rs.getFloat("valor_diaria");

                    return new Automovel(id, marca, modelo, ano, disponivel, valor_diaria);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Automovel> listaAutomoveis() {
        List<Automovel> automoveis = new ArrayList<>();
        var sql = "SELECT id_automovel AS id, marca, modelo, ano, disponivel, valor_diaria FROM automovel;";
        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (var pstmt = conn.prepareStatement(sql)) {
                var rs = pstmt.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String marca = rs.getString("marca");
                    String modelo = rs.getString("modelo");
                    int ano = rs.getInt("ano");
                    boolean disponivel = rs.getBoolean("disponivel");
                    float valor_diaria= rs.getFloat("valor_diaria");

                    automoveis.add(new Automovel(id, marca, modelo, ano, disponivel, valor_diaria));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return automoveis;
    }

    public static void atualizarAutomovel(Automovel automovel) {
        var sql = "UPDATE automovel SET marca = ?, modelo = ?, ano = ?, disponivel = ?, valor_diaria = ? WHERE id_automovel = ?;";
        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (var pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, automovel.getMarca());
                pstmt.setString(2, automovel.getModelo());
                pstmt.setInt(3, automovel.getAno());
                pstmt.setBoolean(4, automovel.getDisponivel());
                pstmt.setFloat(5, automovel.getValorDiaria());
                pstmt.setInt(6, automovel.getId());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removerAutomovel(Automovel automovel) {
        var sql = "DELETE FROM automovel WHERE id_automovel = ?;";
        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (var pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, automovel.getId());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}