package repository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.ClienteModel;
import model.VendaModel;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {
    private static final String BASE_DIR = "cliente";
    private final Gson gson;

    public ClienteRepository() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    private Path getClienteDir(int id) {
        return Paths.get(BASE_DIR + id);
    }

    private Path getClienteFilePath(int id) {
        return getClienteDir(id).resolve("dados.json");
    }

    public void cadastrarCliente(ClienteModel cliente) {
        try {
            Files.createDirectories(getClienteDir(cliente.getId()));
            try (FileWriter writer = new FileWriter(getClienteFilePath(cliente.getId()).toFile())) {
                gson.toJson(cliente, writer);
            }
        } catch (IOException e) {
            System.err.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    public ClienteModel buscarClientePorId(int id) {
        Path filePath = getClienteFilePath(id);
        if (!Files.exists(filePath)) return null;

        try (FileReader reader = new FileReader(filePath.toFile())) {
            ClienteModel cliente = gson.fromJson(reader, ClienteModel.class);
            
            if (cliente != null && cliente.getHistoricoCompras() != null) {
                for (VendaModel venda : cliente.getHistoricoCompras()) {
                    venda.setCliente(cliente);
                }
            }
            return cliente;
        } catch (IOException e) {
            return null;
        }
    }

    public void alterarCadastro(ClienteModel clienteAtualizado) {
        cadastrarCliente(clienteAtualizado);
    }

    public void removerCliente(int id) {
        Path dir = getClienteDir(id);
        if (!Files.exists(dir)) return;
        
        try {
            Files.walk(dir).sorted((a, b) -> -a.compareTo(b)).forEach(path -> {
                try { Files.delete(path); } catch (IOException ignored) {}
            });
        } catch (IOException e) {
            System.err.println("Erro ao remover pasta do cliente: " + e.getMessage());
        }
    }

    public List<ClienteModel> listarClientes() {
        List<ClienteModel> clientes = new ArrayList<>();
        try {
            Files.newDirectoryStream(Paths.get("."), "cliente*").forEach(dir -> {
                if (Files.isDirectory(dir)) {
                    try {
                        String dirName = dir.getFileName().toString();
                        int id = Integer.parseInt(dirName.substring("cliente".length()));
                        ClienteModel c = buscarClientePorId(id);
                        if (c != null) clientes.add(c);
                    } catch (NumberFormatException e) {
                    }
                }
            });
        } catch (IOException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    public void consultarTodosClientes() {
        for (ClienteModel cliente : listarClientes()) {
            System.out.println(cliente.getId() + " - " + cliente.getNome() + " (" + cliente.getEmail() + ")");
        }
    }
}