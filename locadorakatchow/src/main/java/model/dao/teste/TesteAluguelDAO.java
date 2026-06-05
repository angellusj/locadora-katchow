package model.dao.teste;

import model.dao.AluguelDAO;
import model.dao.AutomovelDAO;
import model.dao.ClienteDAO;
import model.dao.FuncionarioDAO;
import model.entity.Aluguel;
import model.entity.Automovel;
import model.entity.Cliente;
import model.entity.Funcionario;

import java.time.LocalDate;
import java.util.List;

public class TesteAluguelDAO implements TesteDaoComponent {

    @Override
    public boolean teste() {
        try {
            System.out.println("\n----- TESTES ALUGUEL DAO -----\n");

            Cliente cliente = ClienteDAO.buscarCliente(2);
            Funcionario funcionario = FuncionarioDAO.buscarFuncionario(2);
            Automovel automovel = AutomovelDAO.buscarAutomovel(2);

            Aluguel aluguel = new Aluguel(
                    0,
                    LocalDate.now(),
                    LocalDate.now().plusDays(3),
                    900.0,
                    "aberto",
                    cliente,
                    funcionario,
                    automovel
            );

            aluguel = AluguelDAO.cadastrarAluguel(aluguel);
            System.out.println("Aluguel criado com sucesso!");

            System.out.println("\nListando todos os aluguéis:");
            List<Aluguel> alugueis = AluguelDAO.listarTodos();

            for (Aluguel a : alugueis) {
                System.out.println(a);
            }

            AluguelDAO.registrarDevolucao(
                    aluguel.getIdAluguel(),
                    LocalDate.now().plusDays(5),
                    aluguel.getValorTotal()
            );

            System.out.println("Devolução registrada com sucesso!");

            AluguelDAO.removerAluguel(aluguel);

            System.out.println("Aluguel removido com sucesso!");

            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Falhou em AluguelDAO");
        }

        return false;
    }
}