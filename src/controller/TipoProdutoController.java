package controller;
import model.TipoProdutoModel;
import java.util.Arrays;
import java.util.List;

public class TipoProdutoController {
    
    public List<TipoProdutoModel> listarTipos() {
        return Arrays.asList(TipoProdutoModel.values());
    }

    public void exibirTiposNoConsole() {
        for (TipoProdutoModel tipo : listarTipos()) {
            System.out.println(tipo.name().replace("_", " "));
        }
    }
}