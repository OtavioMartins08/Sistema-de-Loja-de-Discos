package view;
import model.ItemVendaModel;

public class ItemVendaView {
    public void mostrarItem(ItemVendaModel item) {
        if (item != null) {
            System.out.println(item);
        } else {
            System.out.println("Item inválido.");
        }
    }
}