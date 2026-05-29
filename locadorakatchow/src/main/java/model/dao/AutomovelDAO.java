package model.dao;

import model.db.DB;
import model.entity.Automovel;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutomovelDAO {

    public void cadastrar(Automovel a) {
        String sql = """
            INSERT INTO automovel (marca, modelo, cor, ano, chassi, cambio, disponibilidade, quilometragem, valor_diaria)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getMarca());
            ps.setString(2, a.getModelo());
            ps.setString(3, a.getCor());
            ps.setString(4, a.getAno());
            ps.setString(5, a.getChassi());
            ps.setString(6, a.getCambio());
            ps.setBoolean(7, a.isDisponibilidade());
            ps.setBigDecimal(8, a.getQuilometragem());
            ps.setBigDecimal(9, a.getValorDiaria());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) a.setId(rs.getInt(1));
            }
            System.out.println("Automóvel cadastrado com sucesso! ID: " + a.getId());
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar automóvel: " + e.getMessage());
        }
    }

    public Automovel buscarPorId(int id) {
        String sql = "SELECT * FROM automovel WHERE id_automovel = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar automóvel: " + e.getMessage());
        }
        return null;
    }

    public List<Automovel> listarTodos() {
        List<Automovel> lista = new ArrayList<>();
        String sql = "SELECT * FROM automovel ORDER BY marca, modelo";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar automóveis: " + e.getMessage());
        }
        return lista;
    }

    public List<Automovel> listarDisponiveis() {
        List<Automovel> lista = new ArrayList<>();
        String sql = "SELECT * FROM automovel WHERE disponibilidade = TRUE ORDER BY marca, modelo";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar automóveis disponíveis: " + e.getMessage());
        }
        return lista;
    }

    public void atualizar(Automovel a) {
        String sql = """
            UPDATE automovel SET marca=?, modelo=?, cor=?, ano=?, chassi=?, cambio=?,
                                 disponibilidade=?, quilometragem=?, valor_diaria=?
            WHERE id_automovel=?
            """;
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getMarca());
            ps.setString(2, a.getModelo());
            ps.setString(3, a.getCor());
            ps.setString(4, a.getAno());
            ps.setString(5, a.getChassi());
            ps.setString(6, a.getCambio());
            ps.setBoolean(7, a.isDisponibilidade());
            ps.setBigDecimal(8, a.getQuilometragem());
            ps.setBigDecimal(9, a.getValorDiaria());
            ps.setInt(10, a.getId());
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Automóvel atualizado com sucesso!" : "Automóvel não encontrado.");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar automóvel: " + e.getMessage());
        }
    }

    public void atualizarDisponibilidade(int idAutomovel, boolean disponivel) {
        String sql = "UPDATE automovel SET disponibilidade = ? WHERE id_automovel = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, disponivel);
            ps.setInt(2, idAutomovel);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar disponibilidade: " + e.getMessage());
        }
    }

    public void remover(int id) {
        String sql = "DELETE FROM automovel WHERE id_automovel = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Automóvel removido com sucesso!" : "Automóvel não encontrado.");
        } catch (SQLException e) {
            System.err.println("Erro ao remover automóvel: " + e.getMessage());
        }
    }

    private Automovel mapear(ResultSet rs) throws SQLException {
        return new Automovel(
            rs.getInt("id_automovel"),
            rs.getString("marca"),
            rs.getString("modelo"),
            rs.getString("cor"),
            rs.getString("ano"),
            rs.getString("chassi"),
            rs.getString("cambio"),
            rs.getBoolean("disponibilidade"),
            rs.getBigDecimal("quilometragem"),
            rs.getBigDecimal("valor_diaria")
        );
    }
}
