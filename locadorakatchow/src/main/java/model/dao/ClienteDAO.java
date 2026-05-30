package model.dao;

import model.db.DB;
import model.entity.Cliente;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    /*
     * CREATE TABLE cliente (
     *     id_cliente SERIAL PRIMARY KEY,
     *     nome VARCHAR(100) NOT NULL,
     *     cpf VARCHAR(14) NOT NULL,
     *     telefone VARCHAR(20),
     *     email VARCHAR(100),
     *     endereco VARCHAR(150),
     *     data_nascimento DATE
     * );
     */

    public static void cadastrarCliente(Cliente cliente) {
        var sql = """
                INSERT INTO cliente
                (nome, cpf, telefone, email, endereco, data_nascimento)
                VALUES (?, ?, ?, ?, ?, ?);
                """;

        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (PreparedStatement pstmt =
                         conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, cliente.getNome);
                pstmt.setString(2, cliente.getCpf());
                pstmt.setString(3, cliente.getTelefone());
                pstmt.setString(4, cliente.getEmail());
                pstmt.setString(5, cliente.getEndereco());
                pstmt.setDate(6, Date.valueOf(cliente.getDataNascimento()));
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    cliente.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static Cliente buscarCliente(int id) {
        var sql = "SELECT * FROM cliente WHERE id_cliente = ?;";

        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    id = rs.getInt("id_cliente");
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    String telefone = rs.getString("telefone");
                    String email = rs.getString("email");
                    String endereco = rs.getString("endereco");
                    LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();
                    LocalDate dataCadastro =  rs.getDate("data_cadastro").toLocalDate();

                    Cliente cliente = new Cliente(nome, cpf, telefone, email, endereco, dataNascimento, dataCadastro);
                    cliente.setId(id);
                    return cliente;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Cliente> listarCliente() {
        List<Cliente> clientes = new ArrayList<>();

        var sql = "SELECT * FROM cliente;";

        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id_cliente");
                    String nome = rs.getString("nome");
                    String cpf= rs.getString("cpf");
                    String telefone = rs.getString("telefone");
                    String email = rs.getString("email");
                    String endereco = rs.getString("endereco");
                    LocalDate dataNascimento = rs.getDate("data_nascimento").toLocalDate();
                    LocalDate dataCadastro =  rs.getDate("data_cadastro").toLocalDate();

                    var cliente = new Cliente(nome, cpf, telefone, email, endereco, dataNascimento, dataCadastro);
                    cliente.setId(id);

                    clientes.add(cliente);
                }
                return clientes;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static void atualizarCliente(Cliente cliente) {

        var sql = """
                UPDATE cliente
                SET nome = ?,
                    cpf = ?,
                    telefone = ?,
                    email = ?,
                    endereco = ?,
                    data_nascimento = ?
                WHERE id_cliente = ?;
                """;

        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, cliente.getNome());
                pstmt.setString(2, cliente.getCpf());
                pstmt.setString(3, cliente.getTelefone());
                pstmt.setString(4, cliente.getEmail());
                pstmt.setString(5, cliente.getEndereco());
                pstmt.setDate(6, Date.valueOf(cliente.getDataNascimento()));
                pstmt.setInt(7, cliente.getId());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removerCliente(Cliente cliente) {
        var sql = "DELETE FROM cliente WHERE id_cliente = ?;";

        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, cliente.getId());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}