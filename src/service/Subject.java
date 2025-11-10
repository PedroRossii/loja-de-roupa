package service;

import model.Pedido;

// Padrão 3: Observer (Interface do Sujeito/Subject)
public interface Subject {
    void registrarObservador(Observer observador);
    void removerObservador(Observer observador);
    void notificarObservadores(Pedido pedido);
}