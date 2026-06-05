package repository;
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

public class ProdutoRepository {
    private static final String FILE_PATH = "produtos.json";
    private final Gson gson;

    public ProdutoRepository() {
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

    public void cadastrarProduto(ProdutoModel produto) {
        List<ProdutoModel> produtos = carregarTodos();
        produtos.add(produto);
        salvarTodos(produtos);
    }

    public void editarProduto(ProdutoModel produtoAtualizado) {
        List<ProdutoModel> produtos = carregarTodos();
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == produtoAtualizado.getId()) {
                produtos.set(i, produtoAtualizado);
                salvarTodos(produtos);
                return;
            }
        }
    }

    public void excluirProduto(int id) {
        List<ProdutoModel> produtos = carregarTodos();
        produtos.removeIf(p -> p.getId() == id);
        salvarTodos(produtos);
    }

    public ProdutoModel buscarProdutoPorId(int id) {
        return carregarTodos().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public List<ProdutoModel> buscarProdutos(String nomeBusca, String artistaBusca, String generoBusca) {
        return carregarTodos().stream().filter(produto -> {
            boolean combinaNome = nomeBusca == null || nomeBusca.isBlank() || produto.getNome().toLowerCase().contains(nomeBusca.toLowerCase());
            boolean combinaArtista = artistaBusca == null || artistaBusca.isBlank() || produto.getArtista().toLowerCase().contains(artistaBusca.toLowerCase());
            boolean combinaGenero = generoBusca == null || generoBusca.isBlank() || produto.getGenero().toLowerCase().contains(generoBusca.toLowerCase());
            return combinaNome && combinaArtista && combinaGenero;
        }).toList();
    }

    public List<ProdutoModel> listarProdutos() {
        return carregarTodos();
    }
}