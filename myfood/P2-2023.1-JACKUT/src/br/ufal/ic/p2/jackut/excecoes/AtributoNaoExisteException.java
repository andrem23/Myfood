package br.ufal.ic.p2.jackut.excecoes;

public class AtributoNaoExisteException extends Exception{
    public AtributoNaoExisteException() {
        super("Atributo nao existe");
    }
    
}
