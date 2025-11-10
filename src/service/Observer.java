package service;

import model.Pedido;

// Padrão 3: Observer (Interface do Observador)
public interface Observer {
    void atualizar(Pedido pedido);
}