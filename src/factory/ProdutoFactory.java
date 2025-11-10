package factory;

import model.Calca;
import model.Camiseta;
import model.Produto;

//Factory
public class ProdutoFactory {

    private int proximoId = 1;

    public Produto criarProduto(String tipo, String nome, double preco, String tamanho, int quantidade) {

        int id = proximoId++;

        if ("CAMISETA".equalsIgnoreCase(tipo)) {
            return new Camiseta(id, nome, preco, tamanho, quantidade);
        } else if ("CALCA".equalsIgnoreCase(tipo)) {
            return new Calca(id, nome, preco, tamanho, quantidade);
        }
        throw new IllegalArgumentException("Tipo de produto desconhecido.");
    }
}