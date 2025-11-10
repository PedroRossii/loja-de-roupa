package service;

import model.Pedido;

//Observer
public class EnviadorEmail implements Observer {
    @Override
    public void atualizar(Pedido pedido) {
        System.out.println("[Observer: Email] Notificado. Enviando email de confirmação para " + pedido.getNomeCliente());
        // Lógica de envio de email...
        System.out.println("  - Email enviado com " + pedido.getItens().size() + " itens.");
    }
}