
package model.dao;
import model.db.DB;
import model.entity.Funcionario;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public static void cadastrarFuncionario(Funcionario funcionario) {
        var sql = """
                INSERT INTO funcionario (nome, cpf, telefone, email, endereco, dataNascimento, login, senha, cargo)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, funcionario.getNome());
                pstmt.setString(2, funcionario.getCpf());
                pstmt.setString(3, funcionario.getTelefone());
                pstmt.setString(4, funcionario.getEmail());
                pstmt.setString(5, funcionario.getEndereco());
                pstmt.setDate(6, Date.valueOf(funcionario.getDataNascimento()));
                pstmt.setString(7, funcionario.getLogin());
                pstmt.setString(8, funcionario.getSenha());
                pstmt.setString(9, funcionario.getCargo());
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    funcionario.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Funcionario buscarFuncionario(int id) {
        var sql = "SELECT * FROM funcionario WHERE id_func = ?;";
        try(var conn = DB.getConnection()) {
            assert conn != null;
            try(var pstmt = conn.prepareStatement(sql)){
                pstmt.setInt(1, id);
                var rs = pstmt.executeQuery();
                if(rs.next()){
                    int idf = rs.getInt("id_func");
                    String nomeFunc = rs.getString("nome");
                    String cpfFunc = rs.getString("cpf");
                    String telefoneFunc = rs.getString("telefone");
                    String emailFunc = rs.getString("email");
                    String enderecoFunc = rs.getString("endereco");
                    LocalDate dataNascFunc = rs.getDate("data_nascimento").toLocalDate();
                    String login = rs.getString("login");
                    String senha = rs.getString("senha");
                    String cargo = rs.getString("cargo");
                    Funcionario func = new Funcionario(nomeFunc, cpfFunc, telefoneFunc, emailFunc, enderecoFunc, dataNascFunc, login, senha, cargo);
                    func.setId(idf);
                    return func;
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Funcionario> listaFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        var sql = "SELECT id_func AS id, nome, cpf, telefone, email, ndereco, data_nascimento, login, senha, cargo FROM funcionario;";
        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (var pstmt = conn.prepareStatement(sql)) {
                var rs = pstmt.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String cpfFunc = rs.getString("cpf");
                    String telefoneFunc = rs.getString("telefone");
                    String emailFunc = rs.getString("email");
                    String enderecoFunc = rs.getString("endereco");
                    LocalDate dataNascFunc = rs.getDate("data_nascimento").toLocalDate();
                    String login = rs.getString("login");
                    String senha = rs.getString("senha");
                    String cargo = rs.getString("cargo");
                    Funcionario func = new Funcionario(nome, cpfFunc, telefoneFunc, emailFunc, enderecoFunc, dataNascFunc,login, senha, cargo);
                    func.setId(id);
                    funcionarios.add(func);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return funcionarios;
    }

    public static void atualizarFuncionario(Funcionario funcionario) {
        String nome = funcionario.getNome();
        String cpf = funcionario.getCpf();
        String telefone = funcionario.getTelefone();
        String email = funcionario.getEmail();
        String endereco = funcionario.getEndereco();
        LocalDate dataNasc = funcionario.getDataNascimento();
        String login = funcionario.getLogin();
        String senha = funcionario.getSenha();
        String cargo = funcionario.getCargo();

        var sql = "UPDATE funcionario SET nome = ?, cpf = ?, telefone = ?, email = ?, endereco = ?, data_nascimento = ?, login = ?, senha = ?, cargo = ? WHERE id_func = ?;";
        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (var pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nome);
                pstmt.setString(2, cpf);
                pstmt.setString(3, telefone);
                pstmt.setString(4, email);
                pstmt.setString(5, endereco);
                pstmt.setDate(6, Date.valueOf(funcionario.getDataNascimento()));
                pstmt.setString(7, login);
                pstmt.setString(8, senha);
                pstmt.setString(9, cargo);
                pstmt.setInt(10, funcionario.getId());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removerFuncionario(Funcionario funcionario) {
        var sql = "DELETE FROM funcionario WHERE id_func = ?;";
        try (var conn = DB.getConnection()) {
            assert conn != null;
            try (var pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, funcionario.getId());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}