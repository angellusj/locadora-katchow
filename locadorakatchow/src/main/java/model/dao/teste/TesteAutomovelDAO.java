package model.dao.teste;

import model.dao.AutomovelDAO;
import model.entity.Automovel;

import java.util.List;

public class TesteAutomovelDAO implements TesteDaoComponent{
    @Override
    public boolean teste() {
        try {
            System.out.println("\n-----TESTES AUTOMOVEL DAO-----\n");

            Automovel automovel = new Automovel(0, "Honda", "Civic", 2010, true, 600);
            AutomovelDAO.cadastrarAutomovel(automovel);
            System.out.println("Automovel criado com sucesso!");

            automovel.setModelo("Vectra");
            AutomovelDAO.atualizarAutomovel(automovel);
            System.out.println("Automovel editado com sucesso!");

            List<Automovel> automoveis = AutomovelDAO.listaAutomoveis();
            System.out.println("Automovel buscado por id:");
            automovel = AutomovelDAO.buscarAutomovel(1);
            System.out.println(automovel);

            AutomovelDAO.removerAutomovel(automovel);

            System.out.println("Automovel deletado com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Falhou em AutomovelDAO");
        }
        return false;
    }
}
