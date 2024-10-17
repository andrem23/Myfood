package br.ufal.ic.p2.jackut.excecoes;

public class NaoPermitidoDoisPedidosAbertoMesmaEmpresaException extends Exception {
    public  NaoPermitidoDoisPedidosAbertoMesmaEmpresaException() {
        super("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
      
    }
  
}
