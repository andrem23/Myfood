package br.ufal.ic.p2.jackut.excecoes;

public class PedidoNaoEstaProntoParaEntregaException extends Exception {
    public PedidoNaoEstaProntoParaEntregaException () {
        super("Pedido nao esta pronto para entrega");
    }

}