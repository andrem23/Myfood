package br.ufal.ic.p2.jackut.excecoes;

public class DonoEmpresaNaoPodeFazerPedidoException extends Exception {
    public  DonoEmpresaNaoPodeFazerPedidoException(){
        super("Dono de empresa nao pode fazer um pedido");
    }
}
