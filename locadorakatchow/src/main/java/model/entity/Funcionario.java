package model.entity;

import java.time.LocalDate;

public class Funcionario extends Usuario {
    private String senha;

    public Funcionario(int id, String nome, String cpf, String telefone1, String telefone2,
                       String email1, String email2, String rua, String cep, String numero,
                       String bairro, String cidade, LocalDate dataNascimento, String senha) {
        super(id, nome, cpf, telefone1, telefone2, email1, email2, rua, cep, numero, bairro, cidade, dataNascimento);
        this.senha = senha;
    }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    @Override
    public String toString() {
        return "\n=== FUNCIONÁRIO ===" +
               "\nID: " + getId() +
               super.toString();
    }
}
