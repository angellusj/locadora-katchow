package model.dao;

import model.db.DB;
import model.entity.Cliente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void cadastrar(Cliente c) {
        String sql = """
            INSERT INTO cliente (nome, cnh, cpf, data_nascimento, rua, cep, numero, bairro, cidade,
                                 email1, email2, telefone1, telefone2)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getNome());
            ps.setString(2, c.getCnh());
            ps.setString(3, c.getCpf());
            ps.setDate(4, Date.valueOf(c.getDataNascimento()));
            ps.setString(5, c.getRua());
            ps.setString(6, c.getCep());
            ps.setString(7, c.getNumero());
            ps.setString(8, c.getBairro());
            ps.setString(9, c.getCidade());
            ps.setString(10, c.getEmail1());
            ps.setString(11, c.getEmail2());
            ps.setString(12, c.getTelefone1());
            ps.setString(13, c.getTelefone2());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getInt(1));
            }
            System.out.println("Cliente cadastrado com sucesso! ID: " + c.getId());
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente: " + e.getMessage());
        }
        return null;
    }

    public Cliente buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM cliente WHERE cpf = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por CPF: " + e.getMessage());
        }
        return null;
    }

    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente ORDER BY nome";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }
        return lista;
    }

    public void atualizar(Cliente c) {
        String sql = """
            UPDATE cliente SET nome=?, cnh=?, cpf=?, data_nascimento=?, rua=?, cep=?, numero=?,
                               bairro=?, cidade=?, email1=?, email2=?, telefone1=?, telefone2=?
            WHERE id_cliente=?
            """;
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getCnh());
            ps.setString(3, c.getCpf());
            ps.setDate(4, Date.valueOf(c.getDataNascimento()));
            ps.setString(5, c.getRua());
            ps.setString(6, c.getCep());
            ps.setString(7, c.getNumero());
            ps.setString(8, c.getBairro());
            ps.setString(9, c.getCidade());
            ps.setString(10, c.getEmail1());
            ps.setString(11, c.getEmail2());
            ps.setString(12, c.getTelefone1());
            ps.setString(13, c.getTelefone2());
            ps.setInt(14, c.getId());
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Cliente atualizado com sucesso!" : "Cliente não encontrado.");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    public void remover(int id) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Cliente removido com sucesso!" : "Cliente não encontrado.");
        } catch (SQLException e) {
            System.err.println("Erro ao remover cliente: " + e.getMessage());
        }
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        return new Cliente(
            rs.getInt("id_cliente"),
            rs.getString("nome"),
            rs.getString("cpf"),
            rs.getString("cnh"),
            rs.getString("telefone1"),
            rs.getString("telefone2"),
            rs.getString("email1"),
            rs.getString("email2"),
            rs.getString("rua"),
            rs.getString("cep"),
            rs.getString("numero"),
            rs.getString("bairro"),
            rs.getString("cidade"),
            rs.getDate("data_nascimento").toLocalDate()
        );
    }
}
