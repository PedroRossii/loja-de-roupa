package service;

import model.Pedido;
import java.util.ArrayList;
import java.util.List;

// Padrão 3: Observer (Subject Concreto)
// Também demonstra SOLID (SRP): Apenas lida com a lógica de negócio
public class LojaService implements Subject {
    private List<Observer> observadores = new ArrayList<>();

    @Override
    public void registrarObservador(Observer observador) {
        observadores.add(observador);
    }

    @Override
    public void removerObservador(Observer observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificarObservadores(Pedido pedido) {
        for (Observer observador : observadores) {
            observador.atualizar(pedido);
        }
    }

    public void finalizarPedido(Pedido pedido) {
        System.out.println("\nProcessando pedido para: " + pedido.getNomeCliente());
        System.out.println("Total: R$" + String.format("%.2f", pedido.getPrecoTotal()));

        // Lógica de negócio principal (ex: validar pagamento, etc.)
        // ...

        // Notifica todos os sistemas interessados (estoque, email, etc.)
        notificarObservadores(pedido);
    }
}