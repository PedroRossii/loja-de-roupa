package factory;

import model.Calca;
import model.Camiseta;
import model.Produto;

// Padrão 2: Factory
public class ProdutoFactory {

    // <<< MUDANÇA AQUI: Contador para gerar IDs únicos
    private int proximoId = 1;

    public Produto criarProduto(String tipo, String nome, double preco, String tamanho, int quantidade) {

        // <<< MUDANÇA AQUI: Gera um novo ID a cada criação
        int id = proximoId++;

        if ("CAMISETA".equalsIgnoreCase(tipo)) {
            // <<< MUDANÇA AQUI: Passa o ID gerado para o construtor
            return new Camiseta(id, nome, preco, tamanho, quantidade);
        } else if ("CALCA".equalsIgnoreCase(tipo)) {
            // <<< MUDANÇA AQUI: Passa o ID gerado para o construtor
            return new Calca(id, nome, preco, tamanho, quantidade);
        }
        throw new IllegalArgumentException("Tipo de produto desconhecido.");
    }
}