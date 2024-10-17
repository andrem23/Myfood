package br.ufal.ic.p2.jackut.excecoes;

public class UsuarioNaoCadastradoException extends Exception {
    public UsuarioNaoCadastradoException(){
        super("Usuario nao cadastrado.");
    } 
}
