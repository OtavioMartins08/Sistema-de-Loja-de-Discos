package service;
import model.ClienteModel;
import model.ItemVendaModel;
import model.ProdutoModel;
import model.VendaModel;
import repository.ClienteRepository;
import repository.ProdutoRepository;
import repository.VendaRepository;
import java.util.List;

public class VendaService {
    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;

    public VendaService() {
        this(new VendaRepository(), new ProdutoRepository(), new ClienteRepository());
    }

    public VendaService(VendaRepository vendaRepo, ProdutoRepository prodRepo, ClienteRepository cliRepo) {
        this.vendaRepository = vendaRepo;
        this.produtoRepository = prodRepo;
        this.clienteRepository = cliRepo;
    }

    public VendaModel iniciarVenda(ClienteModel cliente) {
        int novoId = vendaRepository.listarVendas().size() + 1;
        return new VendaModel(novoId, cliente);
    }

    public boolean adicionarItem(VendaModel venda, int idProduto, int quantidade) {
        if (venda.isFinalizada()) {
            System.out.println("Não é possível adicionar itens a uma venda já finalizada.");
            return false;
        }

        ProdutoModel produto = produtoRepository.buscarProdutoPorId(idProduto);
        if (produto == null) {
            System.out.println("Erro: Produto com ID " + idProduto + " não encontrado.");
            return false;
        }

        if (produto.getQtdEstoque() < quantidade) {
            System.out.println("Erro: Estoque insuficiente para " + produto.getNome() + 
                               ". Disponível: " + produto.getQtdEstoque());
            return false;
        }

        ItemVendaModel novoItem = new ItemVendaModel(quantidade, produto);
        venda.adicionarItem(novoItem);
        System.out.println("Item adicionado: " + novoItem.toString());
        return true;
    }

    public void removerItem(VendaModel venda, ItemVendaModel item) {
        if (!venda.isFinalizada()) {
            venda.removerItem(item);
            System.out.println("Item removido da venda.");
        } else {
            System.out.println("Não é possível remover itens de uma venda finalizada.");
        }
    }

    public void finalizarVenda(VendaModel venda) {
        if (venda.isFinalizada()) {
            System.out.println("Esta venda já foi finalizada.");
            return;
        }

        if (venda.getItens().isEmpty()) {
            System.out.println("Não é possível finalizar uma venda sem itens.");
            return;
        }

        venda.finalizarVenda();
        vendaRepository.finalizarVenda(venda);
        clienteRepository.alterarCadastro(venda.getCliente());

        for (ItemVendaModel item : venda.getItens()) {
            produtoRepository.editarProduto(item.getProduto());
        }

        System.out.println("\nVenda finalizada com sucesso!");
        System.out.println(venda.emitirComprovante());
    }

    public String gerarRelatorioVendas() {
        StringBuilder sb = new StringBuilder();
        List<VendaModel> vendas = vendaRepository.listarVendas();
        if (vendas.isEmpty()) return "Nenhuma venda registrada.";
        
        for (VendaModel venda : vendas) {
            sb.append(venda.gerarRelatorioVendas()).append("\n-------------------\n");
        }
        return sb.toString();
    }

    public List<VendaModel> listarVendas() {
        return vendaRepository.listarVendas();
    }
}