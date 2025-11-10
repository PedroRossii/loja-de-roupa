package view;

import model.Pedido;
import model.Produto;
import repository.EstoqueRepository;
import service.LojaService;
import java.util.Scanner;

public class TerminalView {
    private LojaService lojaService;
    private EstoqueRepository estoque;
    private Pedido carrinhoAtual;
    private Scanner scanner;

    public TerminalView(LojaService lojaService) {
        this.lojaService = lojaService;
        this.estoque = EstoqueRepository.getInstancia();
        this.carrinhoAtual = new Pedido();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("Bem-vindo à Loja de Roupas!");
        int escolha = -1;

        while (escolha != 0) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Ver produtos em estoque");
            System.out.println("2. Adicionar produto ao carrinho");
            System.out.println("3. Remover produto do carrinho");
            System.out.println("4. Ver carrinho");
            System.out.println("5. Finalizar compra");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                escolha = Integer.parseInt(scanner.nextLine());

                switch (escolha) {
                    case 1:
                        mostrarProdutos();
                        break;
                    case 2:
                        adicionarProdutoAoCarrinho();
                        break;
                    case 3:
                        removerProdutoDoCarrinho();
                        break;
                    case 4:
                        verCarrinho();
                        break;
                    case 5:
                        finalizarCompra();
                        break;
                    case 0:
                        System.out.println("Obrigado, volte sempre!");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, insira um número.");
            }
        }
    }

    private void mostrarProdutos() {
        System.out.println("\n--- PRODUTOS EM ESTOQUE ---");
        estoque.getTodosProdutos().stream()
                .filter(p -> p.getQuantidade() > 0)
                .forEach(System.out::println);
    }

    private void adicionarProdutoAoCarrinho() {
        System.out.print("Digite o ID do produto que deseja adicionar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());

            estoque.getProdutoDisponivelPorId(id)
                    .ifPresentOrElse(
                            produto -> {
                                carrinhoAtual.adicionarItem(produto);
                                System.out.println(produto.getNome() + " adicionado ao carrinho.");
                            },
                            () -> System.out.println("Produto não encontrado ou fora de estoque.")
                    );
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Por favor, insira um número.");
        }
    }

    private void removerProdutoDoCarrinho() {
        verCarrinho();

        if (carrinhoAtual.getItens().isEmpty()) {
            System.out.println("Seu carrinho já está vazio.");
            return;
        }

        System.out.print("Digite o ID do produto que deseja remover do carrinho: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());

            boolean removido = carrinhoAtual.removerItemPorId(id);

            if (removido) {
                System.out.println("Produto removido do carrinho.");
            } else {
                System.out.println("Produto com este ID não encontrado no seu carrinho.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Por favor, insira um número.");
        }
    }

    private void verCarrinho() {
        System.out.println("\n--- SEU CARRINHO ---");
        if (carrinhoAtual.getItens().isEmpty()) {
            System.out.println("Seu carrinho está vazio.");
        } else {
            // O toString do produto agora mostra o ID
            carrinhoAtual.getItens().forEach(System.out::println);
            System.out.println("Total: R$" + String.format("%.2f", carrinhoAtual.getPrecoTotal()));
        }
    }

    private void finalizarCompra() {
        if (carrinhoAtual.getItens().isEmpty()) {
            System.out.println("Seu carrinho está vazio. Adicione itens antes de finalizar.");
            return;
        }

        System.out.print("Digite seu nome para o pedido: ");
        String nomeCliente = scanner.nextLine();
        carrinhoAtual.setNomeCliente(nomeCliente);

        lojaService.finalizarPedido(carrinhoAtual);

        carrinhoAtual = new Pedido();
        System.out.println("Compra finalizada com sucesso!");
    }
}