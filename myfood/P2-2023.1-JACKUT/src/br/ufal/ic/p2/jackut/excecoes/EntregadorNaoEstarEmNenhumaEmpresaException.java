package br.ufal.ic.p2.jackut.excecoes;

public class EntregadorNaoEstarEmNenhumaEmpresaException extends Exception {
    public EntregadorNaoEstarEmNenhumaEmpresaException () {
        super("Entregador nao estar em nenhuma empresa.");
    }

}