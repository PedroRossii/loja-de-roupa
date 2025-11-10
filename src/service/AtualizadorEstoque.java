package service;

import model.Pedido;
import model.Produto;
import repository.EstoqueRepository;

public class AtualizadorEstoque implements Observer {
    @Override
    public void atualizar(Pedido pedido) {
        System.out.println("[Observer: Estoque] Notificado. Baixando estoque dos itens...");
        EstoqueRepository estoque = EstoqueRepository.getInstancia();

        for (Produto itemPedido : pedido.getItens()) {

            // <<< MUDANÇA AQUI: Usa o ID para buscar o produto absoluto no estoque
            estoque.getProdutoAbsolutoPorId(itemPedido.getId())
                    .ifPresent(produtoDoEstoque -> {
                        estoque.baixarEstoque(produtoDoEstoque, 1);
                        System.out.println("  - Baixou 1 unidade de [ID: " + produtoDoEstoque.getId() + "]: " + produtoDoEstoque.getNome());
                    });
        }
    }
}