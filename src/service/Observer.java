package service;

import model.Pedido;

//Observer
public interface Observer {
    void atualizar(Pedido pedido);
}