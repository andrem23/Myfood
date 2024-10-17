package br.ufal.ic.p2.jackut.entrega;

import java.util.List;

import br.ufal.ic.p2.jackut.produto.Produto;

public class Entrega {
    private String idEntrega; // ID da entrega
    private String nomeCliente; // Nome do cliente
    private String nomeEmpresa; // Nome da empresa
    private String pedidoID; // ID do pedido associado
    private String entregadorID; // ID do entregador
    private String destino; // Destino da entrega
    private List<Produto> produtos; // Lista de produtos a serem entregues
    private boolean entregue;

    public Entrega(String idEntrega, String nomeCliente, String nomeEmpresa, String pedidoID,
            String entregadorID, String destino, List<Produto> produto) {
        this.idEntrega = idEntrega;
        this.nomeCliente = nomeCliente;
        this.nomeEmpresa = nomeEmpresa;
        this.pedidoID = pedidoID;
        this.entregadorID = entregadorID;
        this.destino = destino;
        this.produtos = produto;
        this.entregue = false;
    }

    public String getIdEntrega() {
        return idEntrega;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public String getPedidoID() {
        return pedidoID;
    }

    public String getEntregadorID() {
        return entregadorID;
    }

    public String getDestino() {
        return destino;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public boolean isEntregue() {
        return entregue;
    }

    public void setEntregue(boolean entregue) {
        this.entregue = entregue;
    }
}
