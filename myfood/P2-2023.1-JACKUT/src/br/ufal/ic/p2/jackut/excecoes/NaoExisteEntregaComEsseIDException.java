package br.ufal.ic.p2.jackut.excecoes;

public class NaoExisteEntregaComEsseIDException extends Exception {
    public NaoExisteEntregaComEsseIDException() {
        super("Nao existe entrega com esse id");
    }

}