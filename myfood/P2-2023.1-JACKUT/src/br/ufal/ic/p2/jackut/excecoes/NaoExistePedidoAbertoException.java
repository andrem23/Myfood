package br.ufal.ic.p2.jackut.excecoes;

public class NaoExistePedidoAbertoException extends Exception {
    public NaoExistePedidoAbertoException () {
        super("Nao existe pedido em aberto");
    }
}