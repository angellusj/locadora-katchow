package model.entity;

import java.time.LocalDate;

public class Funcionario extends Usuario {
    private String senha;

    public Funcionario(int id, String nome, String cpf, String telefone,
                       String email, String endereco, LocalDate dataNascimento, String senha) {
        super(nome, cpf, telefone, email, endereco, dataNascimento);
        this.senha = senha;
    }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    @Override
    public String toString() {
        String res = " ";
        res += super.toString();
        res += "\nSenha: " + this.senha;
        return res;
    }
}
