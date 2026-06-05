package control;

import model.dao.AluguelDAO;
import model.entity.Aluguel;
import model.entity.Automovel;
import model.entity.Cliente;
import model.entity.Funcionario;
import java.time.LocalDate;
import java.util.List;

public class AluguelController {
    public static Aluguel cadastrarAluguel(LocalDate dataInicio, LocalDate dataFim, double valorTotal, String status, Cliente cliente, Funcionario funcionario, Automovel automovel) {
        validarAluguel(dataInicio, dataFim, valorTotal, cliente, funcionario, automovel);
        Aluguel aluguel = new Aluguel(0, dataInicio, dataFim, valorTotal, status, cliente, funcionario, automovel);

        return AluguelDAO.cadastrarAluguel(aluguel);
    }

    public static void registrarDevolucao(int idAluguel, LocalDate dataEntrega, double valorFinal) {
        if (idAluguel <= 0) {
            throw new IllegalArgumentException("ID do aluguel inválido.");
        }

        if (dataEntrega == null) {
            throw new IllegalArgumentException("Data de entrega não pode ser nula.");
        }

        if (valorFinal < 0) {
            throw new IllegalArgumentException("Valor final inválido.");
        }

        AluguelDAO.registrarDevolucao(idAluguel, dataEntrega, valorFinal);
    }

    public static Aluguel buscarAluguel(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID do aluguel inválido.");
        }
        return AluguelDAO.buscarAluguel(id);
    }

    public static void atualizarAluguel(Aluguel aluguel) {
        if (aluguel == null) {
            throw new IllegalArgumentException("Aluguel não informado.");
        }
        validarAluguel(
                aluguel.getDataInicio(),
                aluguel.getDataFim(),
                aluguel.getValorTotal(),
                aluguel.getCliente(),
                aluguel.getFuncionario(),
                aluguel.getAutomovel()
        );
        AluguelDAO.atualizarAluguel(aluguel);
    }

    public static boolean removerAluguel(Aluguel aluguel) {
        if (aluguel == null) {return false;}
        AluguelDAO.removerAluguel(aluguel);

        return true;
    }

    public static List<Aluguel> listarTodos() {
        return AluguelDAO.listarTodos();
    }

    public static List<Aluguel> listarEmAberto() {
        return AluguelDAO.listarEmAberto();
    }

    public static List<Aluguel> listarPorCliente(int idCliente) {
        if (idCliente <= 0) {
            throw new IllegalArgumentException("ID do cliente inválido.");
        }
        return AluguelDAO.listarPorCliente(idCliente);
    }

    public static List<Aluguel> listarPorAutomovel(int idAutomovel) {
        if (idAutomovel <= 0) {
            throw new IllegalArgumentException("ID do automóvel inválido.");
        }
        return AluguelDAO.listarPorAutomovel(idAutomovel);
    }

    private static void validarAluguel(LocalDate dataInicio, LocalDate dataFim, double valorTotal, Cliente cliente, Funcionario funcionario, Automovel automovel) {
        if (dataInicio == null) {
            throw new IllegalArgumentException("Data de início não pode ser nula.");
        }

        if (dataFim == null) {
            throw new IllegalArgumentException("Data de fim não pode ser nula.");
        }

        if (dataFim.isBefore(dataInicio)) {
            throw new IllegalArgumentException("A data final não pode ser anterior à data inicial.");
        }

        if (valorTotal <= 0) {
            throw new IllegalArgumentException("O valor total deve ser maior que zero.");
        }

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não informado.");
        }

        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não informado.");
        }

        if (automovel == null) {
            throw new IllegalArgumentException("Automóvel não informado.");
        }

        if (!automovel.isDisponivel()) {
            throw new IllegalArgumentException("O automóvel não está disponível para locação.");
        }
    }
}
