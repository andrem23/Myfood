package br.ufal.ic.p2.jackut.excecoes;

public class ProdutoNaoEncontradoException extends Exception {
    public ProdutoNaoEncontradoException() {
        super("Produto nao encontrado");
    }

}