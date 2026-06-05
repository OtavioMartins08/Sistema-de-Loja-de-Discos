package model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VendaModel {
    private int id;
    private Date data;
    private double valorTotal;
    private transient ClienteModel cliente; 
    
    private List<ItemVendaModel> itens;
    private boolean finalizada;

    public VendaModel(int id, ClienteModel cliente) {
        this.id = id;
        this.cliente = cliente;
        this.data = new Date();
        this.itens = new ArrayList<>();
        this.finalizada = false;
    }

    public void adicionarItem(ItemVendaModel item) {
        itens.add(item);
        calcularTotal();
    }

    public void removerItem(ItemVendaModel item) {
        itens.remove(item);
        calcularTotal();
    }

    public double calcularTotal() {
        valorTotal = 0;
        for (ItemVendaModel item : itens) {
            valorTotal += item.getSubtotal();
        }
        return valorTotal;
    }

    public void finalizarVenda() {
        for (ItemVendaModel item : itens) {
            ProdutoModel produto = item.getProduto();
            produto.setQtdEstoque(produto.getQtdEstoque() - item.getQuantidade());
        }
        this.finalizada = true; 
        
        if (this.cliente != null) {
            this.cliente.adicionarCompra(this);
        }
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public double getValorTotal() { return valorTotal; }

    public ClienteModel getCliente() { return cliente; }
    public void setCliente(ClienteModel cliente) { this.cliente = cliente; }

    public List<ItemVendaModel> getItens() { return itens; }

    public boolean isFinalizada() { return finalizada; }
    public void setFinalizada(boolean finalizada) { this.finalizada = finalizada; }

    public String gerarRelatorioVendas() {
        StringBuilder sb = new StringBuilder();
        sb.append("Venda: ").append(id).append("\n");
        for (ItemVendaModel item : itens) {
            sb.append(item.toString()).append("\n");
        }
        sb.append("Total: R$ ").append(calcularTotal());
        return sb.toString();
    }

    public String emitirComprovante() {
        ComprovanteModel comprovante = new ComprovanteModel(id, calcularTotal());
        return comprovante.exibirComprovante();
    }

    @Override
    public String toString() {
        return "Venda #" + id + " | Total: R$ " + valorTotal + " | Finalizada: " + finalizada;
    }
}