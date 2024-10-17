package br.ufal.ic.p2.jackut.excecoes;

public class UsuarioNaoEncontradoException extends Exception {
    public UsuarioNaoEncontradoException() {
        super("Usuario nao encontrado");
    }

}