package controller;
import model.ComprovanteModel;

public class ComprovanteController {
    public void emitirComprovante(ComprovanteModel comprovante) {
        comprovante.emitirComprovante();
    }

    public String exibirComprovante(ComprovanteModel comprovante) {
        return comprovante.exibirComprovante();
    }
}