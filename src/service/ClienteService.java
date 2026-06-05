package service;
import model.ClienteModel;
import repository.ClienteRepository;
import java.util.List;

public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService() {
        this(new ClienteRepository());
    }

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public boolean login(String email, String senha) {
        for (ClienteModel cliente : repository.listarClientes()) {
            if (cliente.login(email, senha)) {
                return true;
            }
        }
        return false;
    }

    public void verificarConta(String email, String senha) {
        if (login(email, senha)) {
            System.out.println("Login realizado com sucesso.");
        } else {
            System.out.println("Login inválido ou conta inativa.");
        }
    }

    public boolean cadastrarCliente(ClienteModel cliente) {
        if (repository.buscarClientePorId(cliente.getId()) != null) {
            System.out.println("Erro: Já existe um cliente com este ID.");
            return false;
        }
        repository.cadastrarCliente(cliente);
        return true;
    }

    public void consultarTodosClientes() {
        List<ClienteModel> clientes = repository.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            for (ClienteModel c : clientes) {
                System.out.println(c.getId() + " - " + c.getNome() + " (" + c.getEmail() + ")");
            }
        }
    }

    public List<ClienteModel> listarClientes() {
        return repository.listarClientes();
    }

    public boolean alterarCadastro(ClienteModel cliente) {
        if (repository.buscarClientePorId(cliente.getId()) == null) {
            System.out.println("Erro: Cliente não encontrado para alteração.");
            return false;
        }
        repository.alterarCadastro(cliente);
        return true;
    }

    public boolean removerCliente(int id) {
        if (repository.buscarClientePorId(id) == null) {
            System.out.println("Erro: Cliente não encontrado para remoção.");
            return false;
        }
        repository.removerCliente(id);
        return true;
    }

    public ClienteModel buscarClientePorId(int id) {
        return repository.buscarClientePorId(id);
    }

    public void visualizarHistoricoCompras(int idCliente) {
        ClienteModel cliente = repository.buscarClientePorId(idCliente);
        if (cliente != null) {
            System.out.println("Histórico de compras de: " + cliente.getNome());
            cliente.visualizarHistoricoCompras();
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
}