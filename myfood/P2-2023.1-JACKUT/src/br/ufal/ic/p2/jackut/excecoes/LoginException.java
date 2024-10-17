package br.ufal.ic.p2.jackut.excecoes;

public class LoginException extends Exception {
    public LoginException () {
        super("Login ou senha invalidos");
    }

}