package model.entity;

import java.time.LocalDate;

public class Funcionario extends Usuario {
    private int idFuncionario;
    private String cargo;

    public Funcionario(String nome, String cpf, String telefone, String email, String endereco, LocalDate dataNascimento, String cargo) {
        super(nome, cpf, telefone, email, endereco, dataNascimento);
        this.idFuncionario = 0;
        this.cargo = cargo;
    }

    public int getIdFuncionario() {return idFuncionario;}

    public void setIdFuncionario(int idFuncionario) {this.idFuncionario = idFuncionario;}

    public String getCargo() {return cargo;}

    public void setCargo(String cargo) {this.cargo = cargo;}

    @Override
    public String toString() {
        String res = "";
        res += super.toString();
        res += "\nFuncionario: " + idFuncionario;
        res += "\nCargo: " + cargo;
        return res;
    }
}