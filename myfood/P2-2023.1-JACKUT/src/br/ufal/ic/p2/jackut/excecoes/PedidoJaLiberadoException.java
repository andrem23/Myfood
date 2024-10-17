package br.ufal.ic.p2.jackut.excecoes;

public class PedidoJaLiberadoException extends Exception {
    public PedidoJaLiberadoException () {
        super("Pedido ja liberado");
    }

}