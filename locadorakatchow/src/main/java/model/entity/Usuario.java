package model.entity;

import java.time.LocalDate;

public abstract class Usuario {
    private int id;
    private String nome, cpf, telefone, email, endereco;
    private LocalDate dataNascimento;

    public Usuario(int id, String nome, String cpf, String telefone,
                   String email, String endereco, LocalDate dataNascimento) {
        this.id = 0;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        String res = " ";
        res += "\nNome: " + nome;
        res += "\nCpf: " + cpf;
        res += "\nTelefone: " + telefone;
        res += "\nEmail: " + email;
        res += "\nEndereço: " + endereco;
        res += "\nData de nascimento: " + dataNascimento;
        return res;
    }
}