package view;
import model.ProdutoModel;
import java.util.List;

public class ProdutoView {
    public void mostrarProduto(ProdutoModel produto) {
        if (produto != null) {
            System.out.println(produto);
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    public void listarProdutos(List<ProdutoModel> produtos) {
        if (produtos == null || produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado no momento.");
            return;
        }
        System.out.println("--- Catálogo de Produtos ---");
        for (ProdutoModel p : produtos) {
            System.out.println("[" + p.getId() + "] " + p);
        }
        System.out.println("----------------------------");
    }
}