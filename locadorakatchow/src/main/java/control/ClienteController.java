package control;

import model.dao.ClienteDAO;
import model.entity.Cliente;
import java.time.LocalDate;
import java.util.List;

public class ClienteController {
    public static Cliente cadastrarCliente(String nome, String cpf, String telefone, String email, String endereco, LocalDate dataNascimento) {
        validarCliente(nome, cpf, telefone, email, endereco, dataNascimento, false);

        Cliente cliente = new Cliente(nome, cpf, telefone, email, endereco, dataNascimento);

        ClienteDAO.cadastrarCliente(cliente);

        return cliente;
    }

    public static Cliente atualizarCliente(int id, String nome, String cpf, String telefone, String email, String endereco, LocalDate dataNascimento) {
        validarCliente(nome, cpf, telefone, email, endereco, dataNascimento, true);

        Cliente cliente = new Cliente(nome, cpf, telefone, email, endereco, dataNascimento);

        cliente.setId(id);

        ClienteDAO.atualizarCliente(cliente);

        return cliente;
    }

    public static boolean deletarCliente(int id) {
        Cliente cliente = ClienteDAO.buscarCliente(id);
        if (cliente == null) {
            return false;
        }

        ClienteDAO.removerCliente(cliente);

        return true;
    }

    public static Cliente buscarCliente(int id) {
        return ClienteDAO.buscarCliente(id);
    }

    public static List<Cliente> listarClientes() {
        return ClienteDAO.listarCliente();
    }

    private static void validarCliente(String nome, String cpf, String telefone, String email, String endereco, LocalDate dataNascimento, boolean editando) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido.");
        }

        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF inválido.");
        }

        if (!cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve conter 11 números.");
        }

        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone inválido.");
        }

        if (!telefone.matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Telefone deve conter apenas números.");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email inválido.");
        }

        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email inválido.");
        }

        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço inválido.");
        }

        if (dataNascimento == null) {
            throw new IllegalArgumentException("Data de nascimento inválida.");
        }

        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de nascimento inválida.");
        }

        if (dataNascimento.getYear() > LocalDate.now().getYear()) {
            throw new IllegalArgumentException(
                    "O ano de nascimento não pode ser maior que o ano atual.");
        }

        if (!editando) {
            List<Cliente> clientes = ClienteDAO.listarCliente();
            for (Cliente c : clientes) {
                if (c.getCpf().equals(cpf)) {
                    throw new IllegalArgumentException("Já existe um cliente cadastrado com este CPF.");
                }
            }
        }
    }
}
