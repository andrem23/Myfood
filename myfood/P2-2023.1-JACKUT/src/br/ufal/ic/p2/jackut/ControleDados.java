package br.ufal.ic.p2.jackut;

import java.util.HashMap;
import java.util.Map;

public class ControleDados {
    public Map<String, Usuario> usuarios;
    private Map<String, Empresa> empresas;
    private Map<String, Produto> produtos;
    private Map<String, Pedido> pedidos;


    public ControleDados() {
        this.usuarios = new HashMap<>();
        this.empresas = new HashMap<>();
        this.produtos = new HashMap<>();
        this.pedidos = new HashMap<>();
    }

    // Usuarios -----------------------------------------------------

    public void persistenciaUsuarios(String filePathUsuarios) {

        XmlPersistencia.salvarUsuarios(usuarios, filePathUsuarios);
    }

    public void carregarUsuarios(String filePathUsuarios) {

        this.usuarios = XmlPersistencia.carregarUsuarios(filePathUsuarios);
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(String id, Usuario usuario) {
        usuarios.put(id, usuario);

    }

    // Empresas ------------------------------------------------------

    public void persistenciaEmpresas(String filePathEmpresas) {

        XmlPersistencia.salvarEmpresas(empresas, filePathEmpresas);
    }

    public void carregarEmpresas(String filePathEmpresas) {

        this.empresas = XmlPersistencia.carregarEmpresas(filePathEmpresas);
    }

    public Map<String, Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(String id, Empresa empresa) {
        empresas.put(id, empresa);

    }

    // Produtos ------------------------------------------------------

    public void persistenciaProdutos(String filePathProdutos) {

        XmlPersistencia.salvarProdutos(produtos, filePathProdutos);
    }

    public void carregarProdutos(String filePathProdutos) {

        this.produtos = XmlPersistencia.carregarProdutos(filePathProdutos);
    }

    public Map<String, Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(String id, Produto produto) {
        produtos.put(id, produto);

    }

    // Pedidos ------------------------------------------------------

    public void persistenciaPedidos(String filePathPedidos) {
        XmlPersistencia.salvarPedidos(pedidos, filePathPedidos);
    }

    public void carregarPedidos(String filePathPedidos) {
        this.pedidos = XmlPersistencia.carregarPedidos(filePathPedidos);
    }

    public Map<String, Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(String id, Pedido pedido) {
        pedidos.put(id, pedido);
    }

}
