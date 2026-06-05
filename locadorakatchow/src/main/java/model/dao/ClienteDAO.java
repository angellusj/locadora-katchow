package model.dao;

import model.db.DB;
import model.entity.Cliente;
import java.sql.*;
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

        String sqlUsuario = """
        INSERT INTO usuario
        (nome, cpf, telefone, email, endereco, data_nascimento)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

        String sqlCliente = """
        INSERT INTO cliente (id_usuario)
        VALUES (?)
        """;

        try (Connection conn = DB.getConnection()) {
            assert conn != null;
            conn.setAutoCommit(false);
            try {
                int idUsuario;
                try (PreparedStatement pstmt = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setString(1, cliente.getNome());
                    pstmt.setString(2, cliente.getCpf());
                    pstmt.setString(3, cliente.getTelefone());
                    pstmt.setString(4, cliente.getEmail());
                    pstmt.setString(5, cliente.getEndereco());
                    pstmt.setDate(6, Date.valueOf(cliente.getDataNascimento()));
                    pstmt.executeUpdate();

                    ResultSet rs = pstmt.getGeneratedKeys();
                    rs.next();
                    idUsuario = rs.getInt(1);
                    cliente.setId(idUsuario);
                }
                try (PreparedStatement pstmt = conn.prepareStatement(sqlCliente, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setInt(1, idUsuario);
                    pstmt.executeUpdate();

                    ResultSet rs = pstmt.getGeneratedKeys();
                    if (rs.next()) {
                        cliente.setIdCliente(rs.getInt(1));
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Cliente buscarCliente(int idCliente) {

        String sql = """
        SELECT c.id_cliente,
               u.*
        FROM cliente c
        JOIN usuario u
            ON c.id_usuario = u.id
        WHERE c.id_cliente = ?
        """;

        try (Connection conn = DB.getConnection()) {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idCliente);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Cliente cliente =
                        new Cliente(
                                rs.getString("nome"),
                                rs.getString("cpf"),
                                rs.getString("telefone"),
                                rs.getString("email"),
                                rs.getString("endereco"),
                                rs.getDate("data_nascimento")
                                        .toLocalDate()
                        );
                cliente.setId(rs.getInt("id"));
                cliente.setIdCliente(rs.getInt("id_cliente"));

                return cliente;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Cliente> listarCliente() {

        List<Cliente> clientes = new ArrayList<>();

        String sql = """
        SELECT c.id_cliente,
               u.*
        FROM cliente c
        JOIN usuario u
            ON c.id_usuario = u.id
        """;

        try (Connection conn = DB.getConnection()) {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Cliente cliente =
                        new Cliente(
                                rs.getString("nome"),
                                rs.getString("cpf"),
                                rs.getString("telefone"),
                                rs.getString("email"),
                                rs.getString("endereco"),
                                rs.getDate("data_nascimento").toLocalDate()
                        );
                cliente.setId(rs.getInt("id"));
                cliente.setIdCliente(rs.getInt("id_cliente"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return clientes;
    }

    public static void atualizarCliente(Cliente cliente) {

        String sql = """
        UPDATE usuario
        SET nome = ?,
            cpf = ?,
            telefone = ?,
            email = ?,
            endereco = ?,
            data_nascimento = ?
        WHERE id = ?
        """;

        try (Connection conn = DB.getConnection()) {

            assert conn != null;PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCpf());
            pstmt.setString(3, cliente.getTelefone());
            pstmt.setString(4, cliente.getEmail());
            pstmt.setString(5, cliente.getEndereco());
            pstmt.setDate(6, Date.valueOf(cliente.getDataNascimento()));
            pstmt.setInt(7, cliente.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removerCliente(Cliente cliente) {

        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = DB.getConnection()) {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cliente.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}