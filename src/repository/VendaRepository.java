package repository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.VendaModel;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class VendaRepository {
    private static final String FILE_PATH = "vendas.json";
    private final Gson gson;

    public VendaRepository() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        inicializarArquivoSeNaoExistir();
    }

    private void inicializarArquivoSeNaoExistir() {
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) {
                Files.write(Paths.get(FILE_PATH), "[]".getBytes());
            }
        } catch (IOException e) {
            System.err.println("Erro ao inicializar arquivo de vendas.");
        }
    }

    private List<VendaModel> carregarTodas() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<VendaModel>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void salvarTodas(List<VendaModel> vendas) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(vendas, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar vendas: " + e.getMessage());
        }
    }

    public void finalizarVenda(VendaModel venda) {
        List<VendaModel> vendas = carregarTodas();
        if (vendas.stream().noneMatch(v -> v.getId() == venda.getId())) {
            vendas.add(venda);
        } else {
            for (int i = 0; i < vendas.size(); i++) {
                if (vendas.get(i).getId() == venda.getId()) {
                    vendas.set(i, venda);
                    break;
                }
            }
        }
        salvarTodas(vendas);
    }

    public void editarVenda(VendaModel venda) {
        List<VendaModel> vendas = carregarTodas();
        for (int i = 0; i < vendas.size(); i++) {
            if (vendas.get(i).getId() == venda.getId()) {
                vendas.set(i, venda);
                salvarTodas(vendas);
                return;
            }
        }
    }

    public List<VendaModel> listarVendas() {
        return carregarTodas();
    }

    public VendaModel buscarVendaPorId(int id) {
        return carregarTodas().stream().filter(v -> v.getId() == id).findFirst().orElse(null);
    }
}