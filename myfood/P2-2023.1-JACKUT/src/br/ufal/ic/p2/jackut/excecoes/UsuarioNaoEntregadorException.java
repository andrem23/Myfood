package br.ufal.ic.p2.jackut.excecoes;

public class UsuarioNaoEntregadorException extends Exception {
    public UsuarioNaoEntregadorException(){
        super("Usuario nao e um entregador");
    } 
}
