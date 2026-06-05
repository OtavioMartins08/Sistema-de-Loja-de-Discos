package controller;
import model.ClienteModel;
import model.VendaModel;
import service.ClienteService;
import java.util.List;

public class ClienteController {
    private final ClienteService service;

    public ClienteController() {
        this(new ClienteService());
    }

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    public boolean login(String email, String senha) {
        return service.login(email, senha);
    }

    public boolean cadastrarCliente(ClienteModel cliente) {
        return service.cadastrarCliente(cliente);
    }

    public List<ClienteModel> listarClientes() {
        return service.listarClientes();
    }

    public boolean alterarCadastro(ClienteModel cliente) {
        return service.alterarCadastro(cliente);
    }

    public boolean removerCliente(int id) {
        return service.removerCliente(id);
    }

    public ClienteModel buscarClientePorId(int id) {
        return service.buscarClientePorId(id);
    }

    public String obterHistoricoFormatado(int idCliente) {
        ClienteModel cliente = service.buscarClientePorId(idCliente);
        if (cliente == null) return "Cliente não encontrado.";
        
        StringBuilder sb = new StringBuilder("Histórico de " + cliente.getNome() + ":\n");
        for (VendaModel venda : cliente.getHistoricoCompras()) {
            sb.append(venda.toString()).append("\n");
        }
        return sb.toString();
}
}