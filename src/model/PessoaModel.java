package model;

public class PessoaModel {

    protected int id;
    protected String nome;
    protected String email;
    protected String senha;
    protected boolean ehAdministrador;
    protected boolean statusDaConta;

    public PessoaModel(int id, String nome, String email, String senha, boolean ehAdministrador) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ehAdministrador = ehAdministrador;
        this.statusDaConta = true;
    }

    public boolean login(String email, String senha) {
        return this.email.equals(email) &&
               this.senha.equals(senha) &&
               statusDaConta;
    }

    public void verificarConta(String email, String senha) {
        if (login(email, senha)) {
            System.out.println("Login realizado com sucesso.");
        } else {
            System.out.println("Login inválido.");
        }
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isEhAdministrador() {
        return ehAdministrador;
    }

    public boolean isStatusDaConta() {
        return statusDaConta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEhAdministrador(boolean ehAdministrador) {
        this.ehAdministrador = ehAdministrador;
    }

    public void setStatusDaConta(boolean statusDaConta) {
        this.statusDaConta = statusDaConta;
    }
}
