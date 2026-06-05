package service;
import model.ProdutoModel;
import repository.ProdutoRepository;
import java.util.List;

public class ProdutoService {
    private final ProdutoRepository repository;

    public ProdutoService() {
        this(new ProdutoRepository());
    }

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public boolean cadastrarProduto(ProdutoModel produto) {
        if (produto.getPreco() <= 0 || produto.getQtdEstoque() < 0) {
            System.out.println("Erro: Preço deve ser > 0 e Estoque >= 0.");
            return false;
        }
        repository.cadastrarProduto(produto);
        return true;
    }

    public boolean editarProduto(ProdutoModel produto) {
        if (repository.buscarProdutoPorId(produto.getId()) == null) {
            System.out.println("Erro: Produto não encontrado para edição.");
            return false;
        }
        repository.editarProduto(produto);
        return true;
    }

    public boolean excluirProduto(int id) {
        ProdutoModel produto = repository.buscarProdutoPorId(id);
        if (produto == null) {
            System.out.println("Erro: Produto não encontrado para exclusão.");
            return false;
        }
        repository.excluirProduto(id);
        return true;
    }

    public ProdutoModel buscarProdutoPorId(int id) {
        return repository.buscarProdutoPorId(id);
    }

    public List<ProdutoModel> buscarProdutos(String nomeBusca, String artistaBusca, String generoBusca) {
        return repository.buscarProdutos(nomeBusca, artistaBusca, generoBusca);
    }

    public List<ProdutoModel> listarProdutos() {
        return repository.listarProdutos();
    }
}