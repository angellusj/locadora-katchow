package model.entity;

import java.time.LocalDate;

public class Cliente extends Usuario {
    private int idCliente;

    public Cliente(String nome, String cpf, String telefone, String email, String endereco, LocalDate dataNascimento) {
        super(nome, cpf, telefone, email, endereco, dataNascimento);
        this.idCliente = 0;
    }

    public int getIdCliente() {return idCliente;}

    public void setIdCliente(int idCliente) {this.idCliente = idCliente;}

    @Override
    public String toString() {
        String res = " ";
        res += super.toString();
        res += "\nIdCliente: " + idCliente;
        return res;
    }
}