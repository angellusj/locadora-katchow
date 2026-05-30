package model.entity;

import java.math.BigDecimal;

public class Automovel {
    private int id;
    private String marca;
    private String modelo;
    private String ano;
    private boolean disponivel;
    private BigDecimal valorDiaria;

    public Automovel(int id, String marca, String modelo, String ano, boolean disponivel,
                     BigDecimal valorDiaria) {
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

    public String getAno() { return ano; }
    public void setAno(String ano) { this.ano = ano; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    public BigDecimal getValorDiaria() { return valorDiaria; }
    public void setValorDiaria(BigDecimal valorDiaria) { this.valorDiaria = valorDiaria; }

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
