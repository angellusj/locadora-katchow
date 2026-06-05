package control;

import model.dao.FuncionarioDAO;
import model.entity.Funcionario;
import java.time.LocalDate;
import java.util.List;

public class FuncionarioController {
    public static Funcionario cadastrarFuncionario(String nome, String cpf, String telefone, String email, String endereco, LocalDate dataNascimento, String cargo, String login, String senha) {
        validarFuncionario(nome, cpf, telefone, email, endereco, cargo, login, senha);
        Funcionario funcionario = new Funcionario(nome, cpf, telefone, email, endereco, dataNascimento, cargo, login, senha);
        FuncionarioDAO.cadastrarFuncionario(funcionario);

        return funcionario;
    }

    public static Funcionario atualizarFuncionario(int idUsuario, int idFuncionario, String nome, String cpf, String telefone, String email, String endereco, LocalDate dataNascimento, String cargo, String login, String senha) {
        validarFuncionario(nome, cpf, telefone, email, endereco, cargo, login, senha);
        Funcionario funcionario = new Funcionario(nome, cpf, telefone, email, endereco, dataNascimento, cargo, login, senha);
        funcionario.setId(idUsuario);
        funcionario.setIdFuncionario(idFuncionario);
        FuncionarioDAO.atualizarFuncionario(funcionario);

        return funcionario;
    }

    public static boolean deletarFuncionario(int idFuncionario) {
        Funcionario funcionario = FuncionarioDAO.buscarFuncionario(idFuncionario);
        if (funcionario == null) {return false;}
        FuncionarioDAO.removerFuncionario(funcionario);

        return true;
    }

    public static Funcionario buscarFuncionario(int idFuncionario) {
        return FuncionarioDAO.buscarFuncionario(idFuncionario);
    }

    public static List<Funcionario> listarFuncionarios() {
        return FuncionarioDAO.listaFuncionarios();
    }

    public static Funcionario buscaFuncionarioByLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login não pode ser nulo ou vazio.");
        }
        Funcionario funcionario = FuncionarioDAO.buscaFuncionarioByLogin(login);
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não encontrado.");
        }

        return funcionario;
    }

    public static Funcionario realizarLogin(String login, String senha) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login não pode ser nulo ou vazio.");
        }

        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia.");
        }

        Funcionario funcionario = FuncionarioDAO.buscaFuncionarioByLogin(login);
        if (funcionario == null) {
            return null;
        }
        if (funcionario.getSenha().equals(senha)) {
            return funcionario;
        }
        return null;
    }

    private static void validarFuncionario(String nome, String cpf, String telefone, String email, String endereco, String cargo, String login, String senha) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }

        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio.");
        }

        if (!cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve possuir 11 dígitos.");
        }

        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio.");
        }

        if (!telefone.matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Telefone inválido.");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio.");
        }

        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email inválido.");
        }

        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço não pode ser vazio.");
        }

        if (cargo == null || cargo.trim().isEmpty()) {
            throw new IllegalArgumentException("Cargo não pode ser vazio.");
        }

        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login não pode ser vazio.");
        }

        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia.");
        }

        if (senha.length() < 6) {
            throw new IllegalArgumentException("A senha deve possuir pelo menos 6 caracteres.");
        }
    }
}
