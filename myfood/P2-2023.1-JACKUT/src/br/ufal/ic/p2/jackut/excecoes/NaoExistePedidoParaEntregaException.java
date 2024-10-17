package br.ufal.ic.p2.jackut.excecoes;

public class NaoExistePedidoParaEntregaException extends Exception {
    public NaoExistePedidoParaEntregaException  () {
        super("Nao existe pedido para entrega");
    }

}