package service;

import model.Pedido;

//Observer
public interface Subject {
    void registrarObservador(Observer observador);
    void removerObservador(Observer observador);
    void notificarObservadores(Pedido pedido);
}