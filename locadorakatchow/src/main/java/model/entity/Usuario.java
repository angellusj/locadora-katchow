package model.entity;

import java.time.LocalDate;

public abstract class Usuario {
    private int id;
    private String nome;
    private String cpf;
    private String telefone1;
    private String telefone2;
    private String email1;
    private String email2;
    private String rua;
    private String cep;
    private String numero;
    private String bairro;
    private String cidade;
    private LocalDate dataNascimento;

    public Usuario(int id, String nome, String cpf, String telefone1, String telefone2,
                   String email1, String email2, String rua, String cep, String numero,
                   String bairro, String cidade, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.email1 = email1;
        this.email2 = email2;
        this.rua = rua;
        this.cep = cep;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.dataNascimento = dataNascimento;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone1() { return telefone1; }
    public void setTelefone1(String telefone1) { this.telefone1 = telefone1; }

    public String getTelefone2() { return telefone2; }
    public void setTelefone2(String telefone2) { this.telefone2 = telefone2; }

    public String getEmail1() { return email1; }
    public void setEmail1(String email1) { this.email1 = email1; }

    public String getEmail2() { return email2; }
    public void setEmail2(String email2) { this.email2 = email2; }

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    @Override
    public String toString() {
        return "\nNome: " + nome +
               "\nCPF: " + cpf +
               "\nTelefone: " + telefone1 + (telefone2 != null ? " / " + telefone2 : "") +
               "\nEmail: " + email1 + (email2 != null ? " / " + email2 : "") +
               "\nEndereço: " + rua + ", " + numero + " - " + bairro + ", " + cidade + " - CEP: " + cep +
               "\nData de Nascimento: " + dataNascimento;
    }
}
