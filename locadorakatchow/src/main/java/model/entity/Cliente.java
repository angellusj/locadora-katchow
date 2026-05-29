package model.entity;

import java.time.LocalDate;

public class Cliente extends Usuario {
    private String cnh;

    public Cliente(int id, String nome, String cpf, String cnh, String telefone1, String telefone2,
                   String email1, String email2, String rua, String cep, String numero,
                   String bairro, String cidade, LocalDate dataNascimento) {
        super(id, nome, cpf, telefone1, telefone2, email1, email2, rua, cep, numero, bairro, cidade, dataNascimento);
        this.cnh = cnh;
    }

    public String getCnh() { return cnh; }
    public void setCnh(String cnh) { this.cnh = cnh; }

    @Override
    public String toString() {
        return "\n=== CLIENTE ===" +
               "\nID: " + getId() +
               super.toString() +
               "\nCNH: " + cnh;
    }
}
