package model;

public class ProdutoModel {

    private int id;
    private String nome;
    private String genero;
    private String artista;
    private int anoLancamento;
    private double preco;
    private int qtdEstoque;
    private TipoProdutoModel tipo;

    public ProdutoModel(
        int id, String nome, String genero, String artista, int anoLancamento,double preco, int qtdEstoque, TipoProdutoModel tipo){
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.artista = artista;
        this.anoLancamento = anoLancamento;
        this.preco = preco;
        this.qtdEstoque = qtdEstoque;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public TipoProdutoModel getTipo() {
        return tipo;
    }

    public void setTipo(TipoProdutoModel tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return nome + " - R$ " + preco;
    }
}