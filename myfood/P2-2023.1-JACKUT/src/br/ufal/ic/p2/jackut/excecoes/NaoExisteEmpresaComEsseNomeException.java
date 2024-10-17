package br.ufal.ic.p2.jackut.excecoes;

public class NaoExisteEmpresaComEsseNomeException extends Exception {
    public NaoExisteEmpresaComEsseNomeException () {
        super("Nao existe empresa com esse nome");
    }

}