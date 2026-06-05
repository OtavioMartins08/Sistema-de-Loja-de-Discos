package model;

public class ItemVendaModel {

    private int quantidade;
    private ProdutoModel produto;
    private double subtotal;

    public ItemVendaModel(int quantidade, ProdutoModel produto) {
        this.quantidade = quantidade;
        this.produto = produto;
        this.subtotal = calcularSubtotal();
    }

    public double calcularSubtotal() {
        subtotal = quantidade * produto.getPreco();
        return subtotal;
    }

    public void alterarItem(int novaQuantidade) {
        this.quantidade = novaQuantidade;
        calcularSubtotal();
    }

    public int getQuantidade() {
        return quantidade;
    }

    public ProdutoModel getProduto() {
        return produto;
    }

    public void setProduto(ProdutoModel produto) {
        this.produto = produto;
        calcularSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        calcularSubtotal();
    }

    @Override
    public String toString() {
        return produto.getNome() +
                " x" + quantidade +
                " = R$ " + subtotal;
    }
}