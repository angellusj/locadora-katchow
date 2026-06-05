package model.dao.teste;

import model.dao.ClienteDAO;
import model.entity.Cliente;

import java.time.LocalDate;
import java.util.List;

public class TesteClienteDAO implements TesteDaoComponent {

    @Override
    public boolean teste() {

        try {

            System.out.println("\n----- TESTES CLIENTE DAO -----\n");

            Cliente cliente;

            System.out.println("Pegando os clientes no banco e inserindo numa lista");

            List<Cliente> lista = ClienteDAO.listarCliente();

            if (lista.isEmpty()) {

                cliente = new Cliente(
                        "Pedro",
                        "55555555555",
                        "84777777777",
                        "pedrocliente@email.com",
                        "Rua Teste, 678",
                        LocalDate.of(2000, 1, 1)
                );

            } else {

                cliente = lista.getFirst();
            }

            System.out.println("Inserindo cliente no banco...");

            ClienteDAO.cadastrarCliente(cliente);

            System.out.println(
                    "Cliente cadastrado!" +
                            "\nId Usuario: " + cliente.getId() +
                            "\nId Cliente: " + cliente.getIdCliente()
            );

            System.out.println("Atualizando cliente...");

            cliente.setNome("Thiago");
            cliente.setEmail("thiagocliente@email.com");

            ClienteDAO.atualizarCliente(cliente);

            System.out.println("Cliente atualizado!");

            System.out.println("Buscando cliente por ID...");

            Cliente clienteEncontrado =
                    ClienteDAO.buscarCliente(
                            cliente.getIdCliente()
                    );

            if (clienteEncontrado != null) {

                System.out.println(clienteEncontrado);

                System.out.println("Cliente encontrado!");
            }

            System.out.println("Listando clientes...");

            List<Cliente> clientes =
                    ClienteDAO.listarCliente();

            for (Cliente c : clientes) {

                System.out.println(c);
            }

            //System.out.println("Removendo cliente...");

            //ClienteDAO.removerCliente(cliente);

            //System.out.println("Cliente removido com sucesso!");

            return true;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            System.out.println("Falhou em ClienteDAO");

            return false;
        }
    }
}