package br.ufal.ic.p2.jackut.excecoes;

public class EntregadorAindaEmEntregaException extends Exception {
    public EntregadorAindaEmEntregaException () {
        super("Entregador ainda em entrega");
    }

}