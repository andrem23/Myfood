package br.ufal.ic.p2.jackut.excecoes;

public class EmpresaNaoFoiEncontradaException extends Exception {
    public EmpresaNaoFoiEncontradaException() {
        super("A empresa não foi encontrada");
    }

}