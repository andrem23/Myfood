package br.ufal.ic.p2.jackut.controle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import br.ufal.ic.p2.jackut.empresa.Empresa;
import br.ufal.ic.p2.jackut.entrega.Entrega;
import br.ufal.ic.p2.jackut.pedido.Pedido;
import br.ufal.ic.p2.jackut.persistencia.XmlPersistencia;
import br.ufal.ic.p2.jackut.produto.Produto;
import br.ufal.ic.p2.jackut.usuario.Entregador;
import br.ufal.ic.p2.jackut.usuario.Usuario;

public class ControleDados {
    public Map<String, Usuario> usuarios;
    private Map<String, Empresa> empresas;
    private Map<String, Produto> produtos;
    private Map<String, Pedido> pedidos;
    private Map<String, Entrega> entregas;

    public ControleDados() {
        this.usuarios = new LinkedHashMap<>();
        this.empresas = new LinkedHashMap<>();
        this.produtos = new LinkedHashMap<>();
        this.pedidos = new LinkedHashMap<>();
        this.entregas = new LinkedHashMap<>();
    }

    public void limparArquivos(String pathUsuarios, String pathEmpresas, String pathProdutos, String pathPedidos,
            String pathEntregas) {
        // Limpar os mapas
        usuarios.clear();
        empresas.clear();
        produtos.clear();
        pedidos.clear();
        entregas.clear();

        // Limpar os arquivos
        try {
            // Limpar arquivo de usuários
            File usuariosFile = new File(pathUsuarios);
            if (usuariosFile.exists()) {
                try (FileWriter fileWriter = new FileWriter(usuariosFile)) {
                    fileWriter.write("");
                }
            }

            // Limpar arquivo de empresas
            File empresasFile = new File(pathEmpresas);
            if (empresasFile.exists()) {
                try (FileWriter fileWriter = new FileWriter(empresasFile)) {
                    fileWriter.write("");
                }
            }

            // Limpar arquivo de produtos
            File produtosFile = new File(pathProdutos);
            if (produtosFile.exists()) {
                try (FileWriter fileWriter = new FileWriter(produtosFile)) {
                    fileWriter.write("");
                }
            }

            // Limpar arquivo de pedidos
            File pedidosFile = new File(pathPedidos);
            if (pedidosFile.exists()) {
                try (FileWriter fileWriter = new FileWriter(pedidosFile)) {
                    fileWriter.write("");
                }
            }

            // Limpar arquivo de entregas
            File entregasFile = new File(pathEntregas);
            if (entregasFile.exists()) {
                try (FileWriter fileWriter = new FileWriter(entregasFile)) {
                    fileWriter.write("");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    // Entregas ------------------------------------------------------

    public void persistenciaEntregas(String filePathEntregas) {
        XmlPersistencia.salvarEntregas(entregas, filePathEntregas);
    }

    public void carregarEntregas(String filePathEntregas) {
        this.entregas = XmlPersistencia.carregarEntregas(filePathEntregas);
    }

    public Map<String, Entrega> getEntregas() {

        return entregas;
    }

    public void setEntregas(String id, Entrega entrega) {
        entregas.put(id, entrega);

    }
}
