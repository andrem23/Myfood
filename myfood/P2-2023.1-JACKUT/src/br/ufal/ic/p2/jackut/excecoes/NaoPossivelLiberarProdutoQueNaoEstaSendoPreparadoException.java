package br.ufal.ic.p2.jackut.excecoes;

public class NaoPossivelLiberarProdutoQueNaoEstaSendoPreparadoException extends Exception {
    public NaoPossivelLiberarProdutoQueNaoEstaSendoPreparadoException() {
        super("Nao e possivel liberar um produto que nao esta sendo preparado");
    }

}