package model.entity;

import java.math.BigDecimal;

public class Automovel {
    private int id;
    private String marca;
    private String modelo;
    private String cor;
    private String ano;
    private String chassi;
    private String cambio;
    private boolean disponibilidade;
    private BigDecimal quilometragem;
    private BigDecimal valorDiaria;

    public Automovel(int id, String marca, String modelo, String cor, String ano,
                     String chassi, String cambio, boolean disponibilidade,
                     BigDecimal quilometragem, BigDecimal valorDiaria) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.ano = ano;
        this.chassi = chassi;
        this.cambio = cambio;
        this.disponibilidade = disponibilidade;
        this.quilometragem = quilometragem;
        this.valorDiaria = valorDiaria;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public String getAno() { return ano; }
    public void setAno(String ano) { this.ano = ano; }

    public String getChassi() { return chassi; }
    public void setChassi(String chassi) { this.chassi = chassi; }

    public String getCambio() { return cambio; }
    public void setCambio(String cambio) { this.cambio = cambio; }

    public boolean isDisponibilidade() { return disponibilidade; }
    public void setDisponibilidade(boolean disponibilidade) { this.disponibilidade = disponibilidade; }

    public BigDecimal getQuilometragem() { return quilometragem; }
    public void setQuilometragem(BigDecimal quilometragem) { this.quilometragem = quilometragem; }

    public BigDecimal getValorDiaria() { return valorDiaria; }
    public void setValorDiaria(BigDecimal valorDiaria) { this.valorDiaria = valorDiaria; }

    @Override
    public String toString() {
        return "\n=== AUTOMÓVEL ===" +
               "\nID: " + id +
               "\nMarca: " + marca +
               "\nModelo: " + modelo +
               "\nCor: " + cor +
               "\nAno: " + ano +
               "\nChassi: " + chassi +
               "\nCâmbio: " + cambio +
               "\nQuilometragem: " + quilometragem + " km" +
               "\nValor da Diária: R$ " + valorDiaria +
               "\nDisponível: " + (disponibilidade ? "Sim" : "Não");
    }
}
