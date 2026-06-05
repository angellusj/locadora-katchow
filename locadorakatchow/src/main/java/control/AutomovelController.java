package control;

import model.dao.AutomovelDAO;
import model.entity.Automovel;

import java.util.List;

public class AutomovelController {

    public static Automovel cadastrarAutomovel(String placa, String marca, String modelo, int ano, double valorDiaria) {
        validarAutomovel(placa, marca, modelo, ano, valorDiaria);
        boolean disponivel = true;
        Automovel automovel = new Automovel(0, placa, marca, modelo, ano, disponivel, valorDiaria);
        AutomovelDAO.cadastrarAutomovel(automovel);

        return automovel;
    }

    public static Automovel atualizarAutomovel(int id, String placa, String marca, String modelo, int ano, boolean disponivel, double valorDiaria) {
        validarAutomovel(placa, marca, modelo, ano, valorDiaria);
        Automovel automovel = new Automovel(id, placa, marca, modelo, ano, disponivel, valorDiaria);
        AutomovelDAO.atualizarAutomovel(automovel);

        return automovel;
    }

    public static boolean deletarAutomovel(int id) {
        Automovel automovel = AutomovelDAO.buscarAutomovel(id);
        if (automovel == null) {return false;}
        AutomovelDAO.removerAutomovel(automovel);

        return true;
    }

    public static Automovel buscarAutomovel(int id) {
        return AutomovelDAO.buscarAutomovel(id);
    }

    public static List<Automovel> listarAutomoveis() {
        return AutomovelDAO.listaAutomoveis();
    }

    private static void validarAutomovel(String placa, String marca, String modelo, int ano, double valorDiaria) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("Placa não pode ser vazia.");
        }

        if (placa.length() != 7) {
            throw new IllegalArgumentException("A placa deve possuir 7 caracteres.");
        }

        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("Marca não pode ser vazia.");
        }

        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("Modelo não pode ser vazio.");
        }

        if (ano < 1900 || ano > 2026) {
            throw new IllegalArgumentException("Ano inválido.");
        }

        if (valorDiaria <= 0) {
            throw new IllegalArgumentException("O valor da diária deve ser maior que zero.");
        }
    }
}
