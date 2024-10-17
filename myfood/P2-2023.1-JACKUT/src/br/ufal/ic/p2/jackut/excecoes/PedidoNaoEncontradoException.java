package br.ufal.ic.p2.jackut.excecoes;

public class PedidoNaoEncontradoException extends Exception {
    public PedidoNaoEncontradoException() {
        super("Pedido nao encontrado");
    }
}