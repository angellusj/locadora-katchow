package model.dao;

import model.db.DB;
import model.entity.Funcionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public static void cadastrarFuncionario(Funcionario funcionario) {

        String sqlUsuario = """
        INSERT INTO usuario
        (nome, cpf, telefone, email, endereco, data_nascimento)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

        String sqlFuncionario = """
        INSERT INTO funcionario
        (id_usuario, cargo, login, senha)
        VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int idUsuario;
                try (PreparedStatement pstmt =
                             conn.prepareStatement(
                                     sqlUsuario,
                                     Statement.RETURN_GENERATED_KEYS)) {

                    pstmt.setString(1, funcionario.getNome());
                    pstmt.setString(2, funcionario.getCpf());
                    pstmt.setString(3, funcionario.getTelefone());
                    pstmt.setString(4, funcionario.getEmail());
                    pstmt.setString(5, funcionario.getEndereco());
                    pstmt.setDate(6, Date.valueOf(funcionario.getDataNascimento()));
                    pstmt.executeUpdate();

                    ResultSet rs = pstmt.getGeneratedKeys();
                    rs.next();
                    idUsuario = rs.getInt(1);

                    funcionario.setId(idUsuario);
                }
                try (PreparedStatement pstmt =
                             conn.prepareStatement(sqlFuncionario, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setInt(1, funcionario.getId());
                    pstmt.setString(2, funcionario.getCargo());
                    pstmt.setString(3, funcionario.getLogin());
                    pstmt.setString(4, funcionario.getSenha());
                    pstmt.executeUpdate();

                    ResultSet rs = pstmt.getGeneratedKeys();
                    if (rs.next()) {
                        funcionario.setIdFuncionario(
                                rs.getInt(1));
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

    public static Funcionario buscarFuncionario(int idFuncionario) {
        String sql = """
            SELECT f.id_funcionario,
            f.cargo,
            f.login,
            f.senha,
            u.*
        FROM funcionario f
        JOIN usuario u
            ON u.id = f.id_usuario
        WHERE f.id_funcionario = ?
        """;

        try (Connection conn = DB.getConnection()) {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idFuncionario);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Funcionario funcionario = new Funcionario(
                                rs.getString("nome"),
                                rs.getString("cpf"),
                                rs.getString("telefone"),
                                rs.getString("email"),
                                rs.getString("endereco"),
                                rs.getDate("data_nascimento").toLocalDate(),
                                rs.getString("cargo"),
                                rs.getString("login"),
                                rs.getString("senha")
                        );
                funcionario.setId(rs.getInt("id"));
                funcionario.setIdFuncionario(rs.getInt("id_funcionario"));
                return funcionario;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Funcionario buscaFuncionarioByLogin(String login){
        String sql = """
        SELECT f.id_funcionario,
           f.cargo,
           f.login,
           f.senha,
           u.*
        FROM funcionario f
        JOIN usuario u
            ON u.id = f.id_usuario
        WHERE f.login = ?
        """;
        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (var pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, login);
                var rs = pstmt.executeQuery();

                if (rs.next()) {
                    Funcionario funcionario = new Funcionario(
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            rs.getString("endereco"),
                            rs.getDate("data_nascimento").toLocalDate(),
                            rs.getString("cargo"),
                            rs.getString("login"),
                            rs.getString("senha")
                    );

                    funcionario.setId(rs.getInt("id"));
                    funcionario.setIdFuncionario(rs.getInt("id_funcionario"));
                    return funcionario;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Funcionario> listaFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();

        String sql = """
            SELECT f.id_funcionario,
                f.cargo,
                f.login,
                f.senha,
                u.*
            FROM funcionario f
            JOIN usuario u
                ON u.id = f.id_usuario
            """;

        try (Connection conn = DB.getConnection()) {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Funcionario funcionario = new Funcionario(
                                rs.getString("nome"),
                                rs.getString("cpf"),
                                rs.getString("telefone"),
                                rs.getString("email"),
                                rs.getString("endereco"),
                                rs.getDate("data_nascimento").toLocalDate(),
                                rs.getString("cargo"),
                                rs.getString("login"),
                                rs.getString("senha")
                        );

                funcionario.setId(rs.getInt("id"));
                funcionario.setIdFuncionario(rs.getInt("id_funcionario"));
                funcionarios.add(funcionario);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return funcionarios;
    }

    public static void atualizarFuncionario(Funcionario funcionario) {

        String sqlUsuario = """
        UPDATE usuario
        SET nome = ?,
            cpf = ?,
            telefone = ?,
            email = ?,
            endereco = ?,
            data_nascimento = ?
        WHERE id = ?
        """;

        String sqlFuncionario = """
        UPDATE funcionario
        SET cargo = ?,
            login = ?,
            senha = ?
        WHERE id_funcionario = ?
        """;

        try (Connection conn = DB.getConnection()) {
            assert conn != null;
            conn.setAutoCommit(false);

            try {
                try (PreparedStatement pstmt = conn.prepareStatement(sqlUsuario)) {
                    pstmt.setString(1, funcionario.getNome());
                    pstmt.setString(2, funcionario.getCpf());
                    pstmt.setString(3, funcionario.getTelefone());
                    pstmt.setString(4, funcionario.getEmail());
                    pstmt.setString(5, funcionario.getEndereco());
                    pstmt.setDate(6, Date.valueOf(funcionario.getDataNascimento()));
                    pstmt.setInt(7, funcionario.getId());
                    pstmt.executeUpdate();
                }
                try (PreparedStatement pstmt = conn.prepareStatement(sqlFuncionario)) {
                    pstmt.setString(1, funcionario.getCargo());
                    pstmt.setString(2, funcionario.getLogin());
                    pstmt.setString(3, funcionario.getSenha());
                    pstmt.setInt(4, funcionario.getIdFuncionario());
                    pstmt.executeUpdate();
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

    public static void removerFuncionario(Funcionario funcionario) {
        String sql =
                "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = DB.getConnection()) {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, funcionario.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}