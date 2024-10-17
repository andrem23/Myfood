package br.ufal.ic.p2.jackut.excecoes;

public class ProdutoNaoCadastradoException extends Exception {
    public ProdutoNaoCadastradoException() {
        super("Produto nao cadastrado");
    }

}