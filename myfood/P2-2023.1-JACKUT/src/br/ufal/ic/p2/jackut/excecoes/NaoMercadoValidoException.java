package br.ufal.ic.p2.jackut.excecoes;

public class NaoMercadoValidoException extends Exception {
    public NaoMercadoValidoException () {
        super("Nao e um mercado valido");
    }

}