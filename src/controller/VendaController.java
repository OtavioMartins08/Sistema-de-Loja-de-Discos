package controller;
import model.ClienteModel;
import model.ItemVendaModel;
import model.VendaModel;
import service.VendaService;
import repository.ClienteRepository;
import repository.ProdutoRepository;
import repository.VendaRepository;
import java.util.List;

public class VendaController {
    private final VendaService service;

    public VendaController() {
        this(new VendaService(new VendaRepository(), new ProdutoRepository(), new ClienteRepository()));
    }

    public VendaController(VendaService service) {
        this.service = service;
    }

    public VendaModel iniciarVenda(ClienteModel cliente) {
        return service.iniciarVenda(cliente);
    }

    public boolean adicionarItem(VendaModel venda, int idProduto, int quantidade) {
        return service.adicionarItem(venda, idProduto, quantidade);
    }

    public void removerItem(VendaModel venda, ItemVendaModel item) {
        service.removerItem(venda, item);
    }

    public void finalizarVenda(VendaModel venda) {
        service.finalizarVenda(venda);
    }

    public List<VendaModel> listarVendas() {
        return service.listarVendas();
    }

    public String gerarRelatorioVendas() {
        return service.gerarRelatorioVendas();
    }

    public String gerarRelatorioVendaUnica(VendaModel venda) {
        return venda.gerarRelatorioVendas();
    }

    public String emitirComprovante(VendaModel venda) {
        return venda.emitirComprovante();
    }
}