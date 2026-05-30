package model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Aluguel {
    private int id;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal valorTotal;
    private String status;;

    public Aluguel(int id, LocalDate dataInicio, LocalDate dataFim, BigDecimal valorTotal, String status) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorTotal = valorTotal;
        this.status = status;
    }

    public int getIdAutomovel() { 
        return id;
    }
    public void setIdAutomovel(int id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }
    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }
    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String res = " ";
        res += "\nId: " + id;
        res += "\nData Inicio: " + dataInicio;
        res += "\nData Fim: " + dataFim;
        res += "\nValor total: " + valorTotal;
        res += "\nStatus: " + status;
        return res;
    }
}
