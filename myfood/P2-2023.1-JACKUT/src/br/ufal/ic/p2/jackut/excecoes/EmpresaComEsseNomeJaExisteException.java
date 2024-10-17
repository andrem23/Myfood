package br.ufal.ic.p2.jackut.excecoes;

public class EmpresaComEsseNomeJaExisteException extends Exception {
    public EmpresaComEsseNomeJaExisteException () {
        super("Empresa com esse nome ja existe");
    }
}