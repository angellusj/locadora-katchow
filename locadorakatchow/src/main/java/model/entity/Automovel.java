package model.entity;

import java.math.BigDecimal;

public class Automovel {
    private int id;
    private String marca;
    private String modelo;
    private int ano;
    private boolean disponivel;
    private float valorDiaria;

    public Automovel(int id, String marca, String modelo, int ano, boolean disponivel,
                     float valorDiaria) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.disponivel = disponivel;
        this.valorDiaria = valorDiaria;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public boolean getDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    public float getValorDiaria() { return valorDiaria; }
    public void setValorDiaria(float valorDiaria) { this.valorDiaria = valorDiaria; }

    @Override
    public String toString() {
        String res = " ";
        res += "\nId do Automovel: "  + id;
        res += "\nMarca: " + marca;
        res += "\nModelo: " + modelo;
        res += "\nAno: " + ano;
        res += "\nDisponivel: " + disponivel;
        res += "\nValor da diaria: " + valorDiaria;
        return res;
    }
}
