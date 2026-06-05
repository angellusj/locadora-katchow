package model.dao.teste;

import model.dao.AutomovelDAO;
import model.entity.Automovel;

import java.util.List;

public class TesteAutomovelDAO implements TesteDaoComponent {

    @Override
    public boolean teste() {

        try {

            System.out.println("\n----- TESTES AUTOMOVEL DAO -----\n");

            Automovel automovel = new Automovel(
                    0,
                    "ABC1D23",
                    "Honda",
                    "Civic",
                    2010,
                    true,
                    600.0
            );

            System.out.println("Inserindo automóvel...");

            AutomovelDAO.cadastrarAutomovel(automovel);

            System.out.println(
                    "Automóvel cadastrado com ID: "
                            + automovel.getId()
            );

            System.out.println("Atualizando automóvel...");

            automovel.setModelo("Vectra");

            AutomovelDAO.atualizarAutomovel(automovel);

            System.out.println("Automóvel atualizado!");

            System.out.println("Buscando automóvel por ID...");

            Automovel automovelEncontrado =
                    AutomovelDAO.buscarAutomovel(
                            automovel.getId()
                    );

            if (automovelEncontrado != null) {

                System.out.println(automovelEncontrado);

                System.out.println("Automóvel encontrado!");
            }

            System.out.println("Listando automóveis...");

            List<Automovel> automoveis =
                    AutomovelDAO.listaAutomoveis();

            for (Automovel a : automoveis) {

                System.out.println(a);
            }

            //System.out.println("Removendo automóvel...");

            //AutomovelDAO.removerAutomovel(automovel);

            //System.out.println("Automóvel removido com sucesso!");

            return true;

        } catch (Exception e) {

            System.out.println(e.getMessage());

            System.out.println("Falhou em AutomovelDAO");

            return false;
        }
    }
}