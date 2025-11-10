package model;

public interface Produto {
    int getId(); // <<< MUDANÇA AQUI: Adicionado requisito de ID
    String getNome();
    double getPreco();
    String getTipo();
    String getTamanho();
    int getQuantidade();
    void setQuantidade(int quantidade);
}