package model.dao;

import model.db.DB;
import model.entity.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public void cadastrar(Funcionario f) {
        String sql = """
            INSERT INTO funcionario (nome, cpf, data_nascimento, cep, rua, bairro, numero, cidade,
                                     email1, email2, telefone1, telefone2, senha)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, f.getNome());
            ps.setString(2, f.getCpf());
            ps.setDate(3, Date.valueOf(f.getDataNascimento()));
            ps.setString(4, f.getCep());
            ps.setString(5, f.getRua());
            ps.setString(6, f.getBairro());
            ps.setString(7, f.getNumero());
            ps.setString(8, f.getCidade());
            ps.setString(9, f.getEmail1());
            ps.setString(10, f.getEmail2());
            ps.setString(11, f.getTelefone1());
            ps.setString(12, f.getTelefone2());
            ps.setString(13, f.getSenha());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) f.setId(rs.getInt(1));
            }
            System.out.println("Funcionário cadastrado com sucesso! ID: " + f.getId());
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }

    public Funcionario buscarPorId(int id) {
        String sql = "SELECT * FROM funcionario WHERE id_funcionario = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário: " + e.getMessage());
        }
        return null;
    }

    public Funcionario buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM funcionario WHERE cpf = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário por CPF: " + e.getMessage());
        }
        return null;
    }

    public Funcionario login(String cpf, String senha) {
        String sql = "SELECT * FROM funcionario WHERE cpf = ? AND senha = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);
            ps.setString(2, senha);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao realizar login: " + e.getMessage());
        }
        return null;
    }

    public List<Funcionario> listarTodos() {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionario ORDER BY nome";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar funcionários: " + e.getMessage());
        }
        return lista;
    }

    public void atualizar(Funcionario f) {
        String sql = """
            UPDATE funcionario SET nome=?, cpf=?, data_nascimento=?, cep=?, rua=?, bairro=?,
                                   numero=?, cidade=?, email1=?, email2=?, telefone1=?, telefone2=?, senha=?
            WHERE id_funcionario=?
            """;
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getNome());
            ps.setString(2, f.getCpf());
            ps.setDate(3, Date.valueOf(f.getDataNascimento()));
            ps.setString(4, f.getCep());
            ps.setString(5, f.getRua());
            ps.setString(6, f.getBairro());
            ps.setString(7, f.getNumero());
            ps.setString(8, f.getCidade());
            ps.setString(9, f.getEmail1());
            ps.setString(10, f.getEmail2());
            ps.setString(11, f.getTelefone1());
            ps.setString(12, f.getTelefone2());
            ps.setString(13, f.getSenha());
            ps.setInt(14, f.getId());
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Funcionário atualizado com sucesso!" : "Funcionário não encontrado.");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar funcionário: " + e.getMessage());
        }
    }

    public void remover(int id) {
        String sql = "DELETE FROM funcionario WHERE id_funcionario = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Funcionário removido com sucesso!" : "Funcionário não encontrado.");
        } catch (SQLException e) {
            System.err.println("Erro ao remover funcionário: " + e.getMessage());
        }
    }

    private Funcionario mapear(ResultSet rs) throws SQLException {
        return new Funcionario(
            rs.getInt("id_funcionario"),
            rs.getString("nome"),
            rs.getString("cpf"),
            rs.getString("telefone1"),
            rs.getString("telefone2"),
            rs.getString("email1"),
            rs.getString("email2"),
            rs.getString("rua"),
            rs.getString("cep"),
            rs.getString("numero"),
            rs.getString("bairro"),
            rs.getString("cidade"),
            rs.getDate("data_nascimento").toLocalDate(),
            rs.getString("senha")
        );
    }
}
