package model.entity;

import java.time.LocalDate;

public class Aluguel {
    private int id;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private double valorTotal;
    private String status;
    private Cliente cliente;
    private Funcionario funcionario;
    private Automovel automovel;

    public Aluguel(int id, LocalDate dataInicio, LocalDate dataFim, double valorTotal, String status, Cliente cliente, Funcionario funcionario, Automovel automovel) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorTotal = valorTotal;
        this.status = status;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.automovel = automovel;
    }

    public int getIdAluguel() {return id;}
    public void setIdAluguel(int id) {this.id = id;}

    public LocalDate getDataInicio() {return dataInicio;}
    public void setDataInicio(LocalDate dataInicio) {this.dataInicio = dataInicio;}

    public LocalDate getDataFim() {return dataFim;}
    public void setDataFim(LocalDate dataFim) {this.dataFim = dataFim;}

    public double getValorTotal() {return valorTotal;}
    public void setValorTotal(double valorTotal) {this.valorTotal = valorTotal;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

    public Cliente getCliente() {return cliente;}

    public Funcionario getFuncionario() {return funcionario;}

    public Automovel getAutomovel() {return automovel;}

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
