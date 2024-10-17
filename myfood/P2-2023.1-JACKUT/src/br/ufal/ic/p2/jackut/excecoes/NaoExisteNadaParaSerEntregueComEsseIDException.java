package br.ufal.ic.p2.jackut.excecoes;

public class NaoExisteNadaParaSerEntregueComEsseIDException extends Exception {
    public NaoExisteNadaParaSerEntregueComEsseIDException  () {
        super("Nao existe nada para ser entregue com esse id");
    }

}