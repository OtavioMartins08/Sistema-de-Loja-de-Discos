package dao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.ProdutoModel;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoDAO {
    private static final String FILE_PATH = "produtos.json";
    private final Gson gson;

    public ProdutoDAO() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        inicializarArquivoSeNaoExistir();
    }

    private void inicializarArquivoSeNaoExistir() {
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) {
                Files.write(Paths.get(FILE_PATH), "[]".getBytes());
            }
        } catch (IOException e) {
            System.err.println("Erro ao inicializar arquivo de produtos.");
        }
    }

    private List<ProdutoModel> carregarTodos() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<ProdutoModel>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void salvarTodos(List<ProdutoModel> produtos) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(produtos, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar produtos: " + e.getMessage());
        }
    }

    public boolean create(ProdutoModel produto) {
        List<ProdutoModel> produtos = carregarTodos();
        produtos.add(produto);
        salvarTodos(produtos);
        return true;
    }

    public ProdutoModel read(int id) {
        return carregarTodos().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public boolean update(ProdutoModel produto) {
        List<ProdutoModel> produtos = carregarTodos();
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == produto.getId()) {
                produtos.set(i, produto);
                salvarTodos(produtos);
                return true;
            }
        }
        return false;
    }

    public boolean delete(int id) {
        List<ProdutoModel> produtos = carregarTodos();
        boolean removed = produtos.removeIf(p -> p.getId() == id);
        if (removed) salvarTodos(produtos);
        return removed;
    }

    public List<ProdutoModel> pesquisarPorNome(String nome) {
        return carregarTodos().stream()
                .filter(p -> p.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<ProdutoModel> pesquisarPorArtista(String artista) {
        return carregarTodos().stream()
                .filter(p -> p.getArtista().toLowerCase().contains(artista.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<ProdutoModel> pesquisarPorGenero(String genero) {
        return carregarTodos().stream()
                .filter(p -> p.getGenero().toLowerCase().contains(genero.toLowerCase()))
                .collect(Collectors.toList());
    }
}