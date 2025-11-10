package model;

public class Calca implements Produto {
    private int id;
    private String nome;
    private double preco;
    private String tamanho;
    private int quantidade;

    public Calca(int id, String nome, double preco, String tamanho, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.tamanho = tamanho;
        this.quantidade = quantidade;
    }

    @Override
    public int getId() { return id; }

    @Override
    public String getNome() { return nome; }
    @Override
    public double getPreco() { return preco; }
    @Override
    public String getTipo() { return "Calca"; }
    @Override
    public String getTamanho() { return tamanho; }
    @Override
    public int getQuantidade() { return quantidade; }
    @Override
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    @Override
    public String toString() {
        return String.format("[ID: %d] Calça: %s, Tamanho: %s, Preço: R$%.2f, Estoque: %d",
                id, nome, tamanho, preco, quantidade);
    }
}