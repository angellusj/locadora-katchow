package model.dao.teste;

import model.dao.FuncionarioDAO;
import model.entity.Cliente;
import model.entity.Funcionario;

import java.time.LocalDate;
import java.util.List;

public class TesteFuncionarioDAO implements TesteDaoComponent{

    @Override
    public boolean teste() {
        try {
            System.out.println("\n-----TESTES FUNCIONÁRIO DAO-----\n");

            Funcionario funcionario;

            System.out.println("Pegando os funcionarios no banco e inserindo numa lista");
            List<Funcionario> lista = FuncionarioDAO.listaFuncionarios();
            if (lista.isEmpty())
                funcionario = new Funcionario("Lucas", "22222222222", "84222222222", "lucas@email.com", "Rua Teste, 456", LocalDate.of(2000, 2, 2), "lucasadmin123", "admin123", "Gerente");
            else {
                funcionario = lista.getFirst();
            }

            System.out.println("Inserindo funcionario no banco...");
            FuncionarioDAO.cadastrarFuncionario(funcionario);

            System.out.println("Funcionario cadastrado com ID: " + funcionario.getId());

            System.out.println("Atualizando funcionario...");
            funcionario.setNome("Angela");
            funcionario.setEmail("angela@email.com");

            FuncionarioDAO.atualizarFuncionario(funcionario);

            System.out.println("Funcionario atualizado!");

            System.out.println("Buscando funcionario por ID...");
            Funcionario funcionarioEncontrado =
                    FuncionarioDAO.buscarFuncionario(funcionario.getId());

            if (funcionarioEncontrado != null) {
                System.out.println(funcionarioEncontrado);
                System.out.println("Funcionario encontrado!");
            }

            System.out.println("Listando funcionarios...");
            List<Funcionario> funcionarios =
                    FuncionarioDAO.listaFuncionarios();

            for (Funcionario func : funcionarios) {
                System.out.println(func);
            }

            System.out.println("Removendo funcionarios...");
            FuncionarioDAO.removerFuncionario(funcionario);

            System.out.println("Funcionario removido com sucesso!");

            return true;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            System.out.println("Falhou em FuncionarioDAO");

            return false;
        }
    }
}
