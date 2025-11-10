package model;

public interface Produto {
    int getId();
    String getNome();
    double getPreco();
    String getTipo();
    String getTamanho();
    int getQuantidade();
    void setQuantidade(int quantidade);
}