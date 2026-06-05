package view;
import model.PessoaModel;

public class PessoaView {
    public void mostrarPessoa(PessoaModel pessoa) {
        if (pessoa != null) {
            System.out.println("Nome: " + pessoa.getNome());
            System.out.println("Email: " + pessoa.getEmail());
            System.out.println("Status: " + (pessoa.isStatusDaConta() ? "Ativa" : "Inativa"));
        } else {
            System.out.println("Pessoa não encontrada.");
        }
    }
}