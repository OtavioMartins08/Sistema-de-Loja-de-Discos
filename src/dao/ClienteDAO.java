package dao;
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

public class ClienteDAO {
    private static final String BASE_DIR = "cliente";
    private final Gson gson;

    public ClienteDAO() {
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

    public boolean create(ClienteModel cliente) {
        try {
            Files.createDirectories(getClienteDir(cliente.getId()));
            try (FileWriter writer = new FileWriter(getClienteFilePath(cliente.getId()).toFile())) {
                gson.toJson(cliente, writer);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao criar cliente: " + e.getMessage());
            return false;
        }
    }

    public ClienteModel read(int id) {
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

    public boolean update(ClienteModel cliente) {
        return create(cliente);
    }

    public boolean delete(int id) {
        Path dir = getClienteDir(id);
        if (!Files.exists(dir)) return false;
        try {
            Files.walk(dir).sorted((a, b) -> -a.compareTo(b)).forEach(path -> {
                try { 
                    Files.delete(path); 
                } catch (IOException ignored) {}
            });
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}