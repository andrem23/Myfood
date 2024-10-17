package br.ufal.ic.p2.jackut.excecoes;

public class NaoPossivelAdcionarProdutosPedidoFechadoException extends Exception {
    public NaoPossivelAdcionarProdutosPedidoFechadoException () {
        super("Nao e possivel adcionar produtos a um pedido fechado");
    }
}