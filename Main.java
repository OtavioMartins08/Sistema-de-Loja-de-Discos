import controller.ClienteController;
import controller.ProdutoController;
import controller.VendaController;

import model.ClienteModel;
import model.ProdutoModel;
import model.VendaModel;
import model.TipoProdutoModel;

import java.util.Scanner;
import java.util.List;

public class Main {
    private static ClienteController clienteController;
    private static ProdutoController produtoController;
    private static VendaController vendaController;    
    private static Scanner scanner = new Scanner(System.in);
    private static ClienteModel clienteLogado = null;
    private static boolean adminLogado = false;
    private static VendaModel carrinhoAtual = null;

    public static void main(String[] args) {
        clienteController = new ClienteController();
        produtoController = new ProdutoController();
        vendaController = new VendaController();

        if (clienteController.listarClientes().isEmpty()) {
            inicializarDadosFicticios();
        }

        while (true) {
            if (!adminLogado && clienteLogado == null) {
                menuPrincipal();
            } else if (adminLogado) {
                menuAdmin();
            } else {
                menuCliente();
            }
        }
    }

    private static void menuPrincipal() {
        System.out.println("\n===== MUSICALIZA STORE =====");
        System.out.println("1. Login Cliente");
        System.out.println("2. Login Admin");
        System.out.println("3. Cadastrar-se (Criar Conta)");
        System.out.println("0. Sair do Sistema");
        System.out.print("Escolha uma opção: ");
        
        int opcao = lerOpcaoInteira();
        switch (opcao) {
            case 1: realizarLoginCliente(); break;
            case 2: realizarLoginAdmin(); break;
            case 3: crudCriarCliente(); break;
            case 0: 
                System.out.println("Encerrando aplicação... Até logo!");
                System.exit(0);
            default: System.out.println("Opção inválida!");
        }
    }

    private static void menuAdmin() {
        System.out.println("\n===== PAINEL ADMINISTRATIVO =====");
        System.out.println("1. Cadastrar Produto");
        System.out.println("2. Listar Todos os Produtos");
        System.out.println("3. Excluir Produto");
        System.out.println("4. Listar Todos os Clientes");
        System.out.println("5. Remover Conta de Cliente");
        System.out.println("6. Ver Relatório Geral de Vendas");
        System.out.println("0. Logoff (Sair da Conta)");
        System.out.print("Escolha uma opção: ");

        int opcao = lerOpcaoInteira();
        switch (opcao) {
            case 1: crudCadastrarProduto(); break;
            case 2: 
                System.out.println("\n--- PRODUTOS EM ESTOQUE ---");
                produtoController.listarProdutos().forEach(p -> 
                    System.out.println("ID: " + p.getId() + " | " + p.getNome() + " - " + p.getArtista() + " | R$ " + p.getPreco() + " | Qtd: " + p.getQtdEstoque())
                );
                break;
            case 3: crudExcluirProduto(); break;
            case 4: 
                System.out.println("\n--- CLIENTES CADASTRADOS ---");
                clienteController.listarClientes().forEach(c -> 
                    System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() + " | CPF: " + c.getCpf())
                );
                break;
            case 5: crudRemoverCliente(); break;
            case 6: 
                System.out.println("\n--- RELATÓRIO GERAL DE VENDAS ---");
                System.out.println(vendaController.gerarRelatorioVendas());
                break;
            case 0: 
                adminLogado = false;
                System.out.println("Logoff administrativo concluído.");
                break;
            default: System.out.println("Opção inválida!");
        }
    }

    private static void menuCliente() {
        System.out.println("\n===== ÁREA DO CLIENTE - Olá, " + clienteLogado.getNome() + " =====");
        System.out.println("1. Ver Catálogo / Adicionar ao Carrinho");
        System.out.println("2. Ver Meu Carrinho / Finalizar Compra");
        System.out.println("3. Visualizar Histórico de Compras");
        System.out.println("4. Alterar Meus Dados de Cadastro");
        System.out.println("0. Logoff (Sair da Conta)");
        System.out.print("Escolha uma opção: ");

        int opcao = lerOpcaoInteira();
        switch (opcao) {
            case 1: adicionarItemAoCarrinho(); break;
            case 2: gerenciarEFinalizarCarrinho(); break;
            case 3: 
                System.out.println("\n--- SEU HISTÓRICO DE COMPRAS ---");
                clienteLogado.visualizarHistoricoCompras();
                break;
            case 4: crudAlterarDadosCliente(); break;
            case 0: 
                clienteLogado = null;
                carrinhoAtual = null;
                System.out.println("Logoff efetuado com sucesso.");
                break;
            default: System.out.println("Opção inválida!");
        }
    }

    private static void realizarLoginCliente() {
        System.out.print("Digite seu E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Digite sua Senha: ");
        String senha = scanner.nextLine();

        if (clienteController.login(email, senha)) {
            for (ClienteModel c : clienteController.listarClientes()) {
                if (c.getEmail().equalsIgnoreCase(email)) {
                    clienteLogado = c;
                    break; 
                }
            }
            System.out.println("Login efetuado com sucesso!");
        } else {
            System.out.println("E-mail ou senha incorretos, ou conta inativa.");
        }
    }

    private static void realizarLoginAdmin() {
        System.out.print("E-mail do Administrador: ");
        String email = scanner.nextLine();
        System.out.print("Senha do Administrador: ");
        String senha = scanner.nextLine();

        if (email.equals("admin@email.com") && senha.equals("123456")) {
            adminLogado = true;
            System.out.println("Autenticado como Administrador!");
        } else {
            System.out.println("Credenciais administrativas inválidas.");
        }
    }

    private static void crudCriarCliente() {
        System.out.println("\n--- CADASTRO DE NOVO CLIENTE ---");
        int id = clienteController.listarClientes().size() + 1;
        System.out.print("Nome Completo: ");
        String nome = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Senha de Acesso: ");
        String senha = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        ClienteModel novoCliente = new ClienteModel(id, nome, email, senha, telefone, cpf);
        if (clienteController.cadastrarCliente(novoCliente)) {
            System.out.println("Sua conta foi criada com sucesso! Faça login para continuar.");
        }
    }

    private static void crudAlterarDadosCliente() {
        System.out.println("\n--- ATUALIZAR CADASTRO ---");
        System.out.print("Novo Nome (" + clienteLogado.getNome() + "): ");
        String nome = scanner.nextLine();
        System.out.print("Novo Telefone (" + clienteLogado.getTelefone() + "): ");
        String telefone = scanner.nextLine();

        if (!nome.isBlank()) clienteLogado.setNome(nome);
        if (!telefone.isBlank()) clienteLogado.setTelefone(telefone);

        clienteController.alterarCadastro(clienteLogado);
        System.out.println("Dados atualizados com sucesso!");
    }

    private static void crudRemoverCliente() {
        System.out.print("Digite o ID do cliente que deseja remover: ");
        int id = lerOpcaoInteira();
        if (clienteController.buscarClientePorId(id) != null) {
            clienteController.removerCliente(id);
            System.out.println("Cliente removido do sistema.");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void crudCadastrarProduto() {
        System.out.println("\n--- CADASTRAR NOVO PRODUTO ---");
        int id = produtoController.listarProdutos().size() + 1;
        System.out.print("Título do Álbum/Item: ");
        String nome = scanner.nextLine();
        System.out.print("Gênero Musical: ");
        String genero = scanner.nextLine();
        System.out.print("Artista/Banda: ");
        String artista = scanner.nextLine();
        System.out.print("Ano de Lançamento: ");
        int ano = lerOpcaoInteira();
        System.out.print("Preço Unitário: R$ ");
        double preco = Double.parseDouble(scanner.nextLine());
        System.out.print("Quantidade em Estoque Inicial: ");
        int qtd = lerOpcaoInteira();

        System.out.println("Selecione o Tipo de Produto:");
        System.out.println("1. VINIL | 2. CD | 3. FITA_CASSETE | 4. EQUIPAMENTO_AUDIO | 5. CAMISETA | 6. BONE | 7. POSTER");
        int tipoOpcao = lerOpcaoInteira();
        
        TipoProdutoModel tipo = TipoProdutoModel.VINIL;
        switch (tipoOpcao) {
            case 2: tipo = TipoProdutoModel.CD; break;
            case 3: tipo = TipoProdutoModel.FITA_CASSETE; break;
            case 4: tipo = TipoProdutoModel.EQUIPAMENTO_AUDIO; break;
            case 5: tipo = TipoProdutoModel.CAMISETA; break;
            case 6: tipo = TipoProdutoModel.BONE; break;
            case 7: tipo = TipoProdutoModel.POSTER; break;
        }

        ProdutoModel novoProduto = new ProdutoModel(id, nome, genero, artista, ano, preco, qtd, tipo);
        if (produtoController.cadastrarProduto(novoProduto)) {
            System.out.println("Produto cadastrado com sucesso no catálogo!");
        }
    }

    private static void crudExcluirProduto() {
        System.out.print("Digite o ID do produto a ser excluído do estoque: ");
        int id = lerOpcaoInteira();
        if (produtoController.buscarProdutoPorId(id) != null) {
            produtoController.excluirProduto(id);
            System.out.println("Produto removido com sucesso.");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private static void adicionarItemAoCarrinho() {
        System.out.println("\n--- CATÁLOGO DE PRODUTOS ---");
        List<ProdutoModel> produtos = produtoController.listarProdutos();
        produtos.forEach(p -> System.out.println("[" + p.getId() + "] " + p.getNome() + " - " + p.getArtista() + " (R$ " + p.getPreco() + ") | Estoque: " + p.getQtdEstoque()));

        System.out.print("\nDigite o ID do item que deseja comprar (ou 0 para voltar): ");
        int idProd = lerOpcaoInteira();
        if (idProd == 0) return;

        ProdutoModel produtoSelecionado = produtoController.buscarProdutoPorId(idProd);
        if (produtoSelecionado == null) {
            System.out.println("Produto inválido.");
            return;
        }

        System.out.print("Quantidade desejada: ");
        int quantidade = lerOpcaoInteira();
        
        if (carrinhoAtual == null) {
            carrinhoAtual = vendaController.iniciarVenda(clienteLogado);
        }
        boolean sucesso = vendaController.adicionarItem(carrinhoAtual, idProd, quantidade);
        if (sucesso) {
            System.out.println(quantidade + "x '" + produtoSelecionado.getNome() + "' adicionado ao carrinho!");
        }
    }

    private static void gerenciarEFinalizarCarrinho() {
        if (carrinhoAtual == null || carrinhoAtual.getItens().isEmpty()) {
            System.out.println("\nSeu carrinho está vazio no momento.");
            return;
        }

        System.out.println("\n===== SEU CARRINHO ATUAL =====");
        System.out.println(vendaController.gerarRelatorioVendas());
        System.out.println("==============================");
        System.out.println("1. Finalizar e Pagar a Compra");
        System.out.println("2. Esvaziar / Cancelar Carrinho");
        System.out.println("0. Voltar ao Menu");
        System.out.print("Opção: ");

        int opcao = lerOpcaoInteira();
        if (opcao == 1) {
            vendaController.finalizarVenda(carrinhoAtual);
            
            System.out.println("\n--- COMPRA CONCLUÍDA COM SUCESSO! ---");
            System.out.println(vendaController.emitirComprovante(carrinhoAtual));
            
            carrinhoAtual = null;
        } else if (opcao == 2) {
            carrinhoAtual = null;
            System.out.println("Carrinho cancelado e esvaziado.");
        }
    }

    private static int lerOpcaoInteira() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void inicializarDadosFicticios() {
        System.out.println("Inicializando dados de exemplo no sistema...");
        
        clienteController.cadastrarCliente(new ClienteModel(1, "Admin Global", "admin@email.com", "123456", "84999999999", "000.000.000-00"));        
        produtoController.cadastrarProduto(new ProdutoModel(1, "Thriller", "Pop", "Michael Jackson", 1982, 120.0, 10, TipoProdutoModel.VINIL));
        produtoController.cadastrarProduto(new ProdutoModel(2, "Back In Black", "Rock", "AC/DC", 1980, 90.0, 5, TipoProdutoModel.CD));
        produtoController.cadastrarProduto(new ProdutoModel(3, "Camiseta Nirvana", "Rock", "Nirvana", 1991, 85.0, 20, TipoProdutoModel.CAMISETA));
        
        System.out.println("Dados inicializados com sucesso!\n");
    }
}