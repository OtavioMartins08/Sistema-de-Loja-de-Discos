package view;
import model.ClienteModel;
import java.util.List;

public class ClienteView {
    public void mostrarCliente(ClienteModel cliente) {
        if (cliente != null) {
            System.out.println(cliente);
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    public void listarClientes(List<ClienteModel> clientes) {
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        System.out.println("--- Lista de Clientes ---");
        for (ClienteModel cliente : clientes) {
            System.out.println(cliente);
        }
        System.out.println("------------------------");
    }
}