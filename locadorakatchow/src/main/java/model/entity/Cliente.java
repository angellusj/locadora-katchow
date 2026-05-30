package model.entity;
import java.time.LocalDate;

public class Cliente extends Usuario{
    private LocalDate dataCadastro;

    public Cliente(String nome, String cpf, String telefone,
                   String email, String endereco, LocalDate dataNascimento, LocalDate dataCadastro) {
        super(nome, cpf, telefone, email, endereco, dataNascimento);
        this.dataCadastro = dataCadastro;
    }

    //Get e Set
    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public String toString() {
        String res = " ";
        res += "\nData de Cadastro: ";
        return super.toString() + res;
    }
}