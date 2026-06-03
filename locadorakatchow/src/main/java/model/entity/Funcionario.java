package model.entity;

import java.time.LocalDate;

public class Funcionario extends Usuario {
    private String login, senha, cargo;

    public Funcionario(String nome, String cpf, String telefone,
                       String email, String endereco, LocalDate dataNascimento, String login, String senha, String cargo) {
        super(nome, cpf, telefone, email, endereco, dataNascimento);
        this.login = login;
        this.senha = senha;
        this.cargo = cargo;
    }

    public String getLogin() {return login;}
    public void setLogin(String login) {this.login = login;}

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    @Override
    public String toString() {
        String res = " ";
        res += super.toString();
        res += "\nLogin: " + login;
        res += "\nSenha: " + senha;
        res += "\nCargo: " + cargo;
        return res;
    }
}
