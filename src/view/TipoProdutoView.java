package view;
import model.TipoProdutoModel;

public class TipoProdutoView {
    public void mostrarTipos() {
        System.out.println("--- Tipos de Produto Disponíveis ---");
        for (TipoProdutoModel tipo : TipoProdutoModel.values()) {
            String nomeFormatado = tipo.name()
                                      .replace("_", " ")
                                      .toLowerCase();
            nomeFormatado = nomeFormatado.substring(0, 1).toUpperCase() + nomeFormatado.substring(1);
            System.out.println("• " + nomeFormatado);
        }
        System.out.println("------------------------------------");
    }
}