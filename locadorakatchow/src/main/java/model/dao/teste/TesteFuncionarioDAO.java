package model.dao.teste;

import model.dao.FuncionarioDAO;
import model.entity.Funcionario;

import java.time.LocalDate;
import java.util.List;

public class TesteFuncionarioDAO implements TesteDaoComponent {

    @Override
    public boolean teste() {

        try {

            System.out.println("\n----- TESTES FUNCIONÁRIO DAO -----\n");

            Funcionario funcionario;

            System.out.println("Pegando os funcionários no banco e inserindo numa lista");

            List<Funcionario> lista =
                    FuncionarioDAO.listaFuncionarios();

            if (lista.isEmpty()) {

                funcionario = new Funcionario(
                        "Lucas",
                        "22222222222",
                        "84222222222",
                        "lucas@email.com",
                        "Rua Teste, 456",
                        LocalDate.of(2000, 2, 2),
                        "Gerente"
                );

            } else {

                funcionario = lista.getFirst();
            }

            System.out.println("Inserindo funcionário no banco...");

            FuncionarioDAO.cadastrarFuncionario(funcionario);

            System.out.println(
                    "Funcionário cadastrado!" +
                            "\nId Usuário: " + funcionario.getId() +
                            "\nId Funcionário: " + funcionario.getIdFuncionario()
            );

            System.out.println("Atualizando funcionário...");

            funcionario.setNome("Angela");
            funcionario.setEmail("angela@email.com");

            FuncionarioDAO.atualizarFuncionario(funcionario);

            System.out.println("Funcionário atualizado!");

            System.out.println("Buscando funcionário por ID...");

            Funcionario funcionarioEncontrado =
                    FuncionarioDAO.buscarFuncionario(
                            funcionario.getIdFuncionario()
                    );

            if (funcionarioEncontrado != null) {

                System.out.println(funcionarioEncontrado);

                System.out.println("Funcionário encontrado!");
            }

            System.out.println("Listando funcionários...");

            List<Funcionario> funcionarios =
                    FuncionarioDAO.listaFuncionarios();

            for (Funcionario func : funcionarios) {

                System.out.println(func);
            }

            //System.out.println("Removendo funcionário...");

            //FuncionarioDAO.removerFuncionario(funcionario);

            //System.out.println("Funcionário removido com sucesso!");

            return true;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            System.out.println("Falhou em FuncionarioDAO");

            return false;
        }
    }
}