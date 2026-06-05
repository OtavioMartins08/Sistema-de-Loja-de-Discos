package controller;
import model.ProdutoModel;
import service.ProdutoService;
import java.util.List;

public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController() {
        this(new ProdutoService());
    }

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    public boolean cadastrarProduto(ProdutoModel produto) {
        return service.cadastrarProduto(produto);
    }

    public boolean editarProduto(ProdutoModel produto) {
        return service.editarProduto(produto);
    }

    public boolean excluirProduto(int id) {
        return service.excluirProduto(id);
    }

    public ProdutoModel buscarProdutoPorId(int id) {
        return service.buscarProdutoPorId(id);
    }

    public List<ProdutoModel> buscarProdutos(String nome, String artista, String genero) {
        return service.buscarProdutos(nome, artista, genero);
    }

    public List<ProdutoModel> listarProdutos() {
        return service.listarProdutos();
    }
}