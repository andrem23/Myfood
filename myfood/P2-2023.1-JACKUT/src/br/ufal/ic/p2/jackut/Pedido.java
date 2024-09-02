package br.ufal.ic.p2.jackut;

import java.util.List;
import java.util.ArrayList;

public class Pedido {
    private String pedidoID;
    private String clienteID;
    private String empresaID;
    private String estado;
    private List<Produto> produtos;
    private float valor;

    public Pedido(String pedidoID, String clienteID, String empresaID, String estado, List<Produto> produtos,
            float valor) {
        this.pedidoID = pedidoID;
        this.clienteID = clienteID;
        this.empresaID = empresaID;
        this.estado = estado;
        this.produtos = produtos;
        this.valor = valor;
    }

    public String getPedidoID() {
        return pedidoID;
    }

    public void setPedidoID(String pedidoID) {
        this.pedidoID = pedidoID;
    }

    public String getClienteID() {
        return clienteID;
    }

    public void setClienteID(String clienteID) {
        this.clienteID = clienteID;
    }

    public String getEmpresaID() {
        return empresaID;
    }

    public void setEmpresaID(String empresaID) {
        this.empresaID = empresaID;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
