package br.ufal.ic.p2.jackut.excecoes;

public class ProdutoExistenteException extends Exception {
    public ProdutoExistenteException() {
        super("Ja existe um produto com esse nome para essa empresa");
    }

}
