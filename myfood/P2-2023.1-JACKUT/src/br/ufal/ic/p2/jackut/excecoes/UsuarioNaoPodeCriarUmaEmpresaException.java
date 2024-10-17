package br.ufal.ic.p2.jackut.excecoes;

public class UsuarioNaoPodeCriarUmaEmpresaException extends Exception {
    public UsuarioNaoPodeCriarUmaEmpresaException() {
        super("Usuario nao pode criar uma empresa");
    }

}