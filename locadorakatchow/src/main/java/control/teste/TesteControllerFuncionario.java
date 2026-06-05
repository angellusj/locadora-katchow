package control.teste;

import control.FuncionarioController;
import model.entity.Funcionario;
import java.time.LocalDate;

public class TesteControllerFuncionario implements TesteControllerComponent {

    @Override
    public boolean teste() {

        try {

            System.out.println("\n----- TESTES FUNCIONARIO CONTROLLER -----\n");

            Funcionario funcionario =
                    FuncionarioController.cadastrarFuncionario(
                            "Angela Maria",
                            "99999999999",
                            "84999999999",
                            "angela@email.com",
                            "Rua Teste, 123",
                            LocalDate.of(2000, 2, 2),
                            "Gerente",
                            "angelaadmin",
                            "123456"
                    );

            System.out.println("Funcionário cadastrado:");
            System.out.println(funcionario);

            Funcionario funcionarioBuscado =
                    FuncionarioController.buscarFuncionario(
                            funcionario.getIdFuncionario()
                    );

            System.out.println("\nFuncionário encontrado:");
            System.out.println(funcionarioBuscado);

            funcionario =
                    FuncionarioController.atualizarFuncionario(
                            funcionario.getId(),
                            funcionario.getIdFuncionario(),
                            "Angela Souza",
                            "99999999999",
                            "84888888888",
                            "angelanova@email.com",
                            "Rua Nova, 456",
                            LocalDate.of(2000, 2, 2),
                            "Gerente",
                            "angelagerente",
                            "654321"
                    );

            System.out.println("\nFuncionário atualizado:");
            System.out.println(funcionario);

            System.out.println("\nListando funcionários:");

            for (Funcionario f :
                    FuncionarioController.listarFuncionarios()) {

                System.out.println(f);
                System.out.println("----------------------");
            }

            boolean removido =
                    FuncionarioController.deletarFuncionario(
                            funcionario.getIdFuncionario()
                    );

            if (removido) {
                System.out.println("\nFuncionário removido com sucesso!");
            }

            System.out.println("\nTUDO OK!");

            return true;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            System.out.println("Falhou em FuncionarioController");

            return false;
        }
    }
}
