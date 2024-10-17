package br.ufal.ic.p2.jackut.excecoes;

public class ContaEmailExisteException extends Exception {
    public ContaEmailExisteException(){
        super("Conta com esse email ja existe");
    } 
    
}
