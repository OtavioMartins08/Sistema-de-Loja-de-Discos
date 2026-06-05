package controller;
import service.ClienteService;

public class PessoaController {
    private final ClienteService service;

    public PessoaController() {
        this(new ClienteService());
    }

    public PessoaController(ClienteService service) {
        this.service = service;
    }

    public boolean login(String email, String senha) {
        return service.login(email, senha);
    }

    public void verificarStatusConta(String email, String senha) {
        service.verificarConta(email, senha);
    }
}