package model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Aluguel {
    private int id;
    private int idAutomovel;
    private int idCliente;
    private int idFuncionario;
    private LocalDate dataAluguel;
    private LocalDate dataPrevista;
    private LocalDate dataEntregue;
    private BigDecimal valor;

    // Campos auxiliares para exibição
    private String nomeCliente;
    private String modeloAutomovel;
    private String nomeFuncionario;

    public Aluguel(int id, int idAutomovel, int idCliente, int idFuncionario,
                   LocalDate dataAluguel, LocalDate dataPrevista,
                   LocalDate dataEntregue, BigDecimal valor) {
        this.id = id;
        this.idAutomovel = idAutomovel;
        this.idCliente = idCliente;
        this.idFuncionario = idFuncionario;
        this.dataAluguel = dataAluguel;
        this.dataPrevista = dataPrevista;
        this.dataEntregue = dataEntregue;
        this.valor = valor;
    }

    public BigDecimal calcularValor(BigDecimal valorDiaria) {
        LocalDate fim = (dataEntregue != null) ? dataEntregue : dataPrevista;
        long dias = ChronoUnit.DAYS.between(dataAluguel, fim);
        if (dias <= 0) dias = 1;
        return valorDiaria.multiply(BigDecimal.valueOf(dias));
    }

    public int getId() { 
        return id; 
    }
    public void setId(int id) { 
        this.id = id; 
    }

    public int getIdAutomovel() { 
        return idAutomovel; 
    }
    public void setIdAutomovel(int idAutomovel) { 
        this.idAutomovel = idAutomovel; 
    }

    public int getIdCliente() { 
        return idCliente; 
    }
    public void setIdCliente(int idCliente) { 
        this.idCliente = idCliente; 
    }

    public int getIdFuncionario() { 
        return idFuncionario; 
    }
    public void setIdFuncionario(int idFuncionario) { 
        this.idFuncionario = idFuncionario; 
    }

    public LocalDate getDataAluguel() { 
        return dataAluguel; 
    }
    public void setDataAluguel(LocalDate dataAluguel) { 
        this.dataAluguel = dataAluguel; 
    }

    public LocalDate getDataPrevista() { 
        return dataPrevista; 
    }
    public void setDataPrevista(LocalDate dataPrevista) { 
        this.dataPrevista = dataPrevista; 
    }

    public LocalDate getDataEntregue() { 
        return dataEntregue; 
    }
    public void setDataEntregue(LocalDate dataEntregue) { 
        this.dataEntregue = dataEntregue; 
    }

    public BigDecimal getValor() { 
        return valor; 
    }
    public void setValor(BigDecimal valor) { 
        this.valor = valor; 
    }

    public String getNomeCliente() { 
        return nomeCliente; 
    }
    public void setNomeCliente(String nomeCliente) { 
        this.nomeCliente = nomeCliente; 
    }

    public String getModeloAutomovel() { 
        return modeloAutomovel; 
    }
    public void setModeloAutomovel(String modeloAutomovel) { 
        this.modeloAutomovel = modeloAutomovel; 
    }

    public String getNomeFuncionario() { 
        return nomeFuncionario; 
    }
    public void setNomeFuncionario(String nomeFuncionario) { 
        this.nomeFuncionario = nomeFuncionario;
    }

    @Override
    public String toString() {
        return "\n=== ALUGUEL ===" +
               "\nID: " + id +
               "\nCliente: " + (nomeCliente != null ? nomeCliente : "ID " + idCliente) +
               "\nAutomóvel: " + (modeloAutomovel != null ? modeloAutomovel : "ID " + idAutomovel) +
               "\nFuncionário: " + (nomeFuncionario != null ? nomeFuncionario : "ID " + idFuncionario) +
               "\nData do Aluguel: " + dataAluguel +
               "\nData Prevista Devolução: " + dataPrevista +
               "\nData de Entrega: " + (dataEntregue != null ? dataEntregue : "Não devolvido") +
               "\nValor: " + (valor != null ? "R$ " + valor : "A calcular");
    }
}
