package view;

public class ComprovanteView {
    public void mostrarComprovante(String comprovante) {
        if (comprovante != null) {
            System.out.println("\n" + comprovante + "\n");
        } else {
            System.out.println("Nenhum comprovante disponível.");
        }
    }
}