package view;
import model.VendaModel;
import java.util.List;

public class VendaView {
    public void mostrarVenda(VendaModel venda) {
        if (venda != null) {
            System.out.println(venda);
        } else {
            System.out.println("Venda não encontrada.");
        }
    }

    public void listarVendas(List<VendaModel> vendas) {
        if (vendas == null || vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }
        System.out.println("--- Histórico de Vendas ---");
        for (VendaModel v : vendas) {
            System.out.println(v);
        }
        System.out.println("---------------------------");
    }

    public void mostrarRelatorio(String relatorio) {
        if (relatorio != null && !relatorio.isBlank()) {
            System.out.println("\n" + relatorio + "\n");
        } else {
            System.out.println("Relatório vazio.");
        }
    }
}