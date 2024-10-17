package br.ufal.ic.p2.jackut.excecoes;

public class EmpresaNaoCadastradaException extends Exception {
    public EmpresaNaoCadastradaException() {
        super("Empresa nao cadastrada");
    }

}