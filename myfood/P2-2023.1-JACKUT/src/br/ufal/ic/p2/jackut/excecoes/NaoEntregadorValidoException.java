package br.ufal.ic.p2.jackut.excecoes;

public class NaoEntregadorValidoException extends Exception {
    public NaoEntregadorValidoException () {
        super("Nao e um entregador valido");
    }

}