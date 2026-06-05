package view;

import control.AluguelController;
import control.AutomovelController;
import control.ClienteController;
import control.FuncionarioController;
import model.dao.AutomovelDAO;
import model.dao.ClienteDAO;
import model.entity.Aluguel;
import model.entity.Automovel;
import model.entity.Cliente;
import model.entity.Funcionario;
import utils.Logg;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TelaFuncionario {

    public static void menuFuncionario(Funcionario funcionario, Scanner scanner) {
        int opcao;

        do {
            Logg.info("\n===== MENU FUNCIONÁRIO =====");
            System.out.println("1 - Gerenciar Clientes");
            System.out.println("2 - Gerenciar Automóveis");
            System.out.println("3 - Gerenciar Aluguéis");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    menuCliente(scanner);
                    break;
                case 2:
                    menuAutomovel(scanner);
                    break;
                case 3:
                    menuAluguel(funcionario, scanner);
                    break;
                case 0:
                    Logg.info("Saindo...");
                    break;
                default:
                    Logg.warning("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuCliente(Scanner scanner) {
        int opcao;

        do {
            Logg.info("\n===== CLIENTES =====");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Buscar");
            System.out.println("4 - Remover");
            System.out.println("0 - Voltar");

            opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 1:
                    cadastrarCliente(scanner);
                    break;
                case 2:
                    ClienteController.listarClientes().forEach(System.out::println);
                    break;
                case 3:
                    Logg.info("Informe o ID do cliente: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Cliente cliente = ClienteController.buscarCliente(id);

                    if (cliente != null)
                        System.out.println(cliente);
                    else
                        Logg.warning("Cliente não encontrado.");
                    break;
                case 4:
                    Logg.info("Informe o ID do cliente: ");
                    id = Integer.parseInt(scanner.nextLine());

                    if (ClienteController.deletarCliente(id))
                        Logg.info("Cliente removido.");
                    else
                        Logg.warning("Cliente não encontrado.");
                    break;
            }
        } while (opcao != 0);
    }

    private static void cadastrarCliente(Scanner scanner) {
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("CPF: ");
            String cpf = scanner.nextLine();

            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Endereço: ");
            String endereco = scanner.nextLine();

            System.out.print("Ano nascimento: ");
            int ano = Integer.parseInt(scanner.nextLine());

            System.out.print("Mês nascimento: ");
            int mes = Integer.parseInt(scanner.nextLine());

            System.out.print("Dia nascimento: ");
            int dia = Integer.parseInt(scanner.nextLine());

            LocalDate dataNascimento = LocalDate.of(ano, mes, dia);

            ClienteController.cadastrarCliente(
                    nome,
                    cpf,
                    telefone,
                    email,
                    endereco,
                    dataNascimento
            );

            Logg.info("Cliente cadastrado!");

        } catch (NumberFormatException e) {
            Logg.warning("Ano, mês e dia devem conter apenas números.");
        } catch (java.time.DateTimeException e) {
            Logg.warning("Data de nascimento inválida.");
        } catch (IllegalArgumentException e) {
            Logg.warning(e.getMessage());
        } catch (Exception e) {
            Logg.warning("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    private static void menuAutomovel(Scanner scanner) {
        int opcao;

        do {
            Logg.info("\n===== AUTOMÓVEIS =====");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Buscar");
            System.out.println("4 - Remover");
            System.out.println("0 - Voltar");

            opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 1:
                    cadastrarAutomovel(scanner);
                    break;
                case 2:
                    AutomovelController.listarAutomoveis().forEach(System.out::println);
                    break;
                case 3:
                    Logg.info("Inoforme o ID: ");
                    int id = Integer.parseInt(scanner.nextLine());

                    Automovel automovel = AutomovelController.buscarAutomovel(id);
                    System.out.println(automovel);
                    break;
                case 4:
                    Logg.info("Informe o ID: ");
                    id = Integer.parseInt(scanner.nextLine());
                    AutomovelController.deletarAutomovel(id);
                    Logg.info("Automóvel removido.");
                    break;
            }
        } while (opcao != 0);
    }

    private static void cadastrarAutomovel(Scanner scanner) {
        try {
            System.out.print("Placa: ");
            String placa = scanner.nextLine();

            System.out.print("Marca: ");
            String marca = scanner.nextLine();

            System.out.print("Modelo: ");
            String modelo = scanner.nextLine();

            System.out.print("Ano: ");
            int ano = Integer.parseInt(scanner.nextLine());

            System.out.print("Valor diária: ");
            double valorDiaria = Double.parseDouble(scanner.nextLine());

            AutomovelController.cadastrarAutomovel(
                    placa,
                    marca,
                    modelo,
                    ano,
                    valorDiaria
            );

            Logg.info("Automóvel cadastrado!");

        } catch (NumberFormatException e) {
            Logg.warning("Ano e valor da diária devem ser numéricos.");
        } catch (IllegalArgumentException e) {
            Logg.warning(e.getMessage());
        } catch (Exception e) {
            Logg.warning("Erro ao cadastrar automóvel: " + e.getMessage());
        }
    }

    private static void menuAluguel(Funcionario funcionario, Scanner scanner) {
        int opcao;

        do {
            Logg.info("\n===== ALUGUÉIS =====");
            System.out.println("1 - Criar Aluguel");
            System.out.println("2 - Buscar Aluguel");
            System.out.println("3 - Alterar Aluguel");
            System.out.println("4 - Finalizar Aluguel");
            System.out.println("5 - Listar Aluguéis");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        criarAluguel(funcionario, scanner);
                        break;

                    case 2:
                        buscarAluguel(scanner);
                        break;

                    case 3:
                        alterarAluguel(scanner);
                        break;

                    case 4:
                        finalizarAluguel(scanner);
                        break;

                    case 5:
                        listarAlugueis();
                        break;

                    case 0:
                        break;

                    default:
                        Logg.warning("Opção inválida.");
                }

            } catch (NumberFormatException e) {
                Logg.warning("Informe apenas números.");
                opcao = -1;
            }

        } while (opcao != 0);
    }

    private static void criarAluguel(Funcionario funcionario, Scanner scanner) {

        System.out.print("ID Cliente: ");
        int idCliente = Integer.parseInt(scanner.nextLine());

        System.out.print("ID Automóvel: ");
        int idAutomovel = Integer.parseInt(scanner.nextLine());

        System.out.print("Quantidade de dias: ");
        int dias = Integer.parseInt(scanner.nextLine());

        Cliente cliente = ClienteDAO.buscarCliente(idCliente);
        Automovel automovel = AutomovelDAO.buscarAutomovel(idAutomovel);

        if (cliente == null) {
            Logg.warning("Cliente não encontrado.");
            return;
        }

        if (automovel == null) {
            Logg.warning("Automóvel não encontrado.");
            return;
        }

        LocalDate dataInicio = LocalDate.now();
        LocalDate dataFim = dataInicio.plusDays(dias);

        double valorTotal = automovel.getValorDiaria() * dias;

        Aluguel aluguel = AluguelController.cadastrarAluguel(
                dataInicio,
                dataFim,
                valorTotal,
                "aberto",
                cliente,
                funcionario,
                automovel
        );

        Logg.info("Aluguel criado com sucesso!");
        System.out.println(aluguel);
    }

    private static void buscarAluguel(Scanner scanner) {
        try {
            System.out.print("ID do aluguel: ");
            int id = Integer.parseInt(scanner.nextLine());

            Aluguel aluguel =
                    AluguelController.buscarAluguel(id);

            if (aluguel != null) {
                System.out.println(aluguel);
            } else {
                Logg.warning("Aluguel não encontrado.");
            }

        } catch (NumberFormatException e) {
            Logg.warning("ID inválido.");
        }
    }

    private static void alterarAluguel(Scanner scanner) {

        try {
            System.out.print("ID do aluguel: ");
            int id = Integer.parseInt(scanner.nextLine());

            Aluguel aluguel =
                    AluguelController.buscarAluguel(id);

            if (aluguel == null) {
                Logg.warning("Aluguel não encontrado.");
                return;
            }

            System.out.print("Nova quantidade de dias: ");
            int dias = Integer.parseInt(scanner.nextLine());

            aluguel.setDataFim(
                    aluguel.getDataInicio().plusDays(dias)
            );

            aluguel.setValorTotal(
                    aluguel.getAutomovel().getValorDiaria() * dias
            );

            AluguelController.atualizarAluguel(aluguel);

            Logg.info("Aluguel alterado com sucesso!");

        } catch (NumberFormatException e) {
            Logg.warning("Informe apenas números.");
        }
    }

    private static void finalizarAluguel(Scanner scanner) {
        try {
            System.out.print("ID do aluguel: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Valor final: ");
            double valorFinal = Double.parseDouble(scanner.nextLine());
            AluguelController.registrarDevolucao(id, LocalDate.now(), valorFinal);
            Logg.info("Aluguel finalizado com sucesso!");
        } catch (NumberFormatException e) {
            Logg.warning("Valor inválido.");
        } catch (IllegalArgumentException e) {
            Logg.warning(e.getMessage());
        }
    }

    private static void listarAlugueis() {

        List<Aluguel> alugueis =
                AluguelController.listarTodos();

        alugueis.forEach(System.out::println);
    }

    public static void main(String[] args) {

        Funcionario funcionario =
                FuncionarioController.buscarFuncionario(1);

        menuFuncionario(
                funcionario,
                new Scanner(System.in)
        );
    }
}
