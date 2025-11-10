package repository;

import model.Produto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EstoqueRepository {
    private static EstoqueRepository instancia;
    private List<Produto> produtos;

    private EstoqueRepository() {
        produtos = new ArrayList<>();
    }

    public static synchronized EstoqueRepository getInstancia() {
        if (instancia == null) {
            instancia = new EstoqueRepository();
        }
        return instancia;
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public List<Produto> getTodosProdutos() {
        return new ArrayList<>(produtos);
    }

    public Optional<Produto> getProdutoDisponivelPorId(int id) {
        return produtos.stream()
                .filter(p -> p.getId() == id && p.getQuantidade() > 0)
                .findFirst();
    }

    public Optional<Produto> getProdutoAbsolutoPorId(int id) {
        return produtos.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    public void baixarEstoque(Produto produto, int quantidade) {
        produto.setQuantidade(produto.getQuantidade() - quantidade);
    }
}