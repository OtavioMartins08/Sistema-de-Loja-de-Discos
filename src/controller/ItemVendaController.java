package controller;
import model.ItemVendaModel;

public class ItemVendaController {
    public double calcularSubtotal(ItemVendaModel item) {
        return item.calcularSubtotal();
    }

    public void alterarItem(ItemVendaModel item, int novaQuantidade) {
        item.alterarItem(novaQuantidade);
    }
}