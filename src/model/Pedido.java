package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Pedido {
    private String nomeCliente;
    private List<Produto> itens = new ArrayList<>();

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void adicionarItem(Produto produto) {
        itens.add(produto);
    }

    public boolean removerItemPorId(int id) {
        Optional<Produto> itemParaRemover = itens.stream()
                .filter(p -> p.getId() == id)
                .findFirst();

        if (itemParaRemover.isPresent()) {
            itens.remove(itemParaRemover.get());
            return true;
        }
        return false;
    }

    public List<Produto> getItens() {
        return itens;
    }

    public double getPrecoTotal() {
        return itens.stream().mapToDouble(Produto::getPreco).sum();
    }
}