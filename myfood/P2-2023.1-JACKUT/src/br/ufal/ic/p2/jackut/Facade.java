package br.ufal.ic.p2.jackut;

import easyaccept.EasyAccept;

public class Facade {

    private ControleDados controleDados;
    private Autentificar autentificar;
    private ControleUsuarios controleUsuarios;
    private ControleEmpresa controleEmpresas;
    private ControleProdutos controleProdutos;
    private ControlePedido controlePedido;
    private Login elogin;

    public Facade() {
        this.controleDados = new ControleDados();
        this.autentificar = new Autentificar(controleDados);
        this.controleEmpresas = new ControleEmpresa(controleDados, autentificar);
        this.controleUsuarios = new ControleUsuarios(controleDados, autentificar);
        this.controleProdutos = new ControleProdutos(controleDados);
        this.controlePedido = new ControlePedido(controleDados);
        this.elogin = new Login(controleDados);
        controleDados.carregarUsuarios("usuarios.xml");
        controleDados.carregarEmpresas("empresas.xml");
        controleDados.carregarProdutos("produtos.xml");
        controleDados.carregarPedidos("pedidos.xml");
    }

    public void zerarSistema() {
        controleDados.getUsuarios().clear();
        controleDados.getEmpresas().clear();
        controleDados.getProdutos().clear();
        controleDados.getPedidos().clear();

    }

    public void encerrarSistema() {
        controleDados.persistenciaUsuarios("usuarios.xml");
        controleDados.persistenciaEmpresas("empresas.xml");
        controleDados.persistenciaProdutos("produtos.xml");
        controleDados.persistenciaPedidos("pedidos.xml");

    }

    public String getAtributoUsuario(String id, String nome) {

        return controleUsuarios.buscarAtributos(id, nome);

    }

    public String login(String email, String senha) {
        return elogin.login(email, senha);
    }

    public String criarUsuario(String nome, String email, String senha, String endereco) {
        return controleUsuarios.criarUsuario(nome, email, senha, endereco);
    }

    public String criarUsuario(String nome, String email, String senha, String endereco, String cpf) {
        return controleUsuarios.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public String criarEmpresa(String tipoEmpresa, String donoID, String nome, String endereco, String tipoCozinha) {
        return controleEmpresas.criarEmpresa(tipoEmpresa, donoID, nome, endereco, tipoCozinha);

    }

    public String getAtributoEmpresa(String empresa, String atributo) {
        return controleEmpresas.buscarEmpresa(empresa, atributo);

    }

    public String getEmpresasDoUsuario(int donoID) {
        return controleEmpresas.getEmpresasDoUsuario(String.valueOf(donoID));
    }

    public String getIdEmpresa(String donoID, String atributo, int indice) {
        return controleEmpresas.getIdEmpresa(donoID, atributo, indice);
    }

    public String criarProduto(String empresaId, String nome, float valor, String categoria) {
        return controleProdutos.criarProduto(empresaId, nome, valor, categoria);
    }

    public void editarProduto(String id, String nome, float valor, String categoria) {
        controleProdutos.editarProduto(id, nome, valor, categoria);
    }

    public String getProduto(String nome, String empresaId, String atributo) {
        return controleProdutos.getProduto(nome, empresaId, atributo);
    }

    public String listarProdutos(String empresaId) {
        return controleProdutos.listarProdutos(empresaId);
    }

    public String criarPedido(String clienteID, String empresaID) {
        return controlePedido.criarPedido(clienteID, empresaID);
    }

    public String getNumeroPedido(String clienteID, String empresaID, int indice) {
        return controlePedido.getNumeroPedido(clienteID, empresaID, indice);
    }

    public void adicionarProduto(String pedidoID, String produtoID) {
        controlePedido.adicionarProduto(pedidoID, produtoID);
    }

    public String getPedidos(String pedidoID, String atributo) {
        return controlePedido.getPedidos(pedidoID, atributo);
    }

    public void fecharPedido(String pedidoID) {
        controlePedido.fecharPedido(pedidoID);
    }

    public void removerProduto(String pedidoID, String produtoNome) {
        controlePedido.removerProduto(pedidoID, produtoNome);
    }

}
