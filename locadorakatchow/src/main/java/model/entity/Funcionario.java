package model.entity;

import java.time.LocalDate;

public class Funcionario extends Usuario {
    private int idFuncionario;
    private String cargo;
    private String login;
    private String senha;

    public Funcionario(String nome, String cpf, String telefone, String email, String endereco, LocalDate dataNascimento, String cargo, String login, String senha) {
        super(nome, cpf, telefone, email, endereco, dataNascimento);
        this.idFuncionario = 0;
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
    }

    public int getIdFuncionario() {return idFuncionario;}
    public void setIdFuncionario(int idFuncionario) {this.idFuncionario = idFuncionario;}

    public String getCargo() {return cargo;}
    public void setCargo(String cargo) {this.cargo = cargo;}

    public String getLogin() {return login;}
    public void setLogin(String login) {this.login = login;}

    public String getSenha() {return senha;}
    public void setSenha(String senha) {this.senha = senha;}

    @Override
    public String toString() {
        String res = "";
        res += super.toString();
        res += "\nFuncionario: " + idFuncionario;
        res += "\nCargo: " + cargo;
        res += "\nLogin: " + login;
        res += "\nSenha: " + senha;
        return res;
    }
}