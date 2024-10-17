package br.ufal.ic.p2.jackut.excecoes;

public class NaoPossivelRemoverProdutosPedidoFechadoException extends Exception {
    public NaoPossivelRemoverProdutosPedidoFechadoException () {
        super("Nao e possivel remover produtos de um pedido fechado");
    }
}