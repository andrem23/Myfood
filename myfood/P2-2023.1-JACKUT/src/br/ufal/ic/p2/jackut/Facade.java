package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.autentificar.Autentificar;
import br.ufal.ic.p2.jackut.autentificar.Login;
import br.ufal.ic.p2.jackut.controle.ControleDados;
import br.ufal.ic.p2.jackut.controle.ControleEmpresa;
import br.ufal.ic.p2.jackut.controle.ControleEntregas;
import br.ufal.ic.p2.jackut.controle.ControlePedido;
import br.ufal.ic.p2.jackut.controle.ControleProdutos;
import br.ufal.ic.p2.jackut.controle.ControleUsuarios;
import br.ufal.ic.p2.jackut.excecoes.AtributoInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.AtributoNaoExisteException;
import br.ufal.ic.p2.jackut.excecoes.CategoriaInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.ClienteNaoEncontradoException;
import br.ufal.ic.p2.jackut.excecoes.ContaEmailExisteException;
import br.ufal.ic.p2.jackut.excecoes.CpfInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.DonoEmpresaNaoPodeFazerPedidoException;
import br.ufal.ic.p2.jackut.excecoes.EmailInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.EmpresaComEsseNomeJaExisteException;
import br.ufal.ic.p2.jackut.excecoes.EmpresaNaoCadastradaException;
import br.ufal.ic.p2.jackut.excecoes.EmpresaNaoEncontradaException;
import br.ufal.ic.p2.jackut.excecoes.EmpresaNaoFoiEncontradaException;
import br.ufal.ic.p2.jackut.excecoes.EnderecoEmpresaInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.EnderecoInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.EntregaNaoEncontradaException;
import br.ufal.ic.p2.jackut.excecoes.EntregadorAindaEmEntregaException;
import br.ufal.ic.p2.jackut.excecoes.EntregadorNaoEncontradoException;
import br.ufal.ic.p2.jackut.excecoes.EntregadorNaoEstarEmNenhumaEmpresaException;
import br.ufal.ic.p2.jackut.excecoes.EntregadorjaAssociadoEmpresaException;
import br.ufal.ic.p2.jackut.excecoes.FormatoEmailInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.FormatoHoraInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.HorarioInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.IndiceInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.IndiceMaiorQueEsperadoException;
import br.ufal.ic.p2.jackut.excecoes.LoginException;
import br.ufal.ic.p2.jackut.excecoes.NaoEntregadorValidoException;
import br.ufal.ic.p2.jackut.excecoes.NaoExisteEmpresaComEsseNomeException;
import br.ufal.ic.p2.jackut.excecoes.NaoExisteEntregaComEsseIDException;
import br.ufal.ic.p2.jackut.excecoes.NaoExisteNadaParaSerEntregueComEsseIDException;
import br.ufal.ic.p2.jackut.excecoes.NaoExistePedidoAbertoException;
import br.ufal.ic.p2.jackut.excecoes.NaoExistePedidoParaEntregaException;
import br.ufal.ic.p2.jackut.excecoes.NaoMercadoValidoException;
import br.ufal.ic.p2.jackut.excecoes.NaoPermitidoDoisPedidosAbertoMesmaEmpresaException;
import br.ufal.ic.p2.jackut.excecoes.NaoPossivelAdcionarProdutosPedidoFechadoException;
import br.ufal.ic.p2.jackut.excecoes.NaoPossivelLiberarProdutoQueNaoEstaSendoPreparadoException;
import br.ufal.ic.p2.jackut.excecoes.NaoPossivelRemoverProdutosPedidoFechadoException;
import br.ufal.ic.p2.jackut.excecoes.NomeEmpresaInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.NomeInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.PedidoJaLiberadoException;
import br.ufal.ic.p2.jackut.excecoes.PedidoNaoEncontradoException;
import br.ufal.ic.p2.jackut.excecoes.PedidoNaoEstaProntoParaEntregaException;
import br.ufal.ic.p2.jackut.excecoes.PlacaInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.ProdutoExistenteException;
import br.ufal.ic.p2.jackut.excecoes.ProdutoInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.ProdutoNaoCadastradoException;
import br.ufal.ic.p2.jackut.excecoes.ProdutoNaoEncontradoException;
import br.ufal.ic.p2.jackut.excecoes.ProdutoNaoPertenceEmpresaException;
import br.ufal.ic.p2.jackut.excecoes.ProibidoCadastrarDuasEmpresasComMesmoNomeLocalException;
import br.ufal.ic.p2.jackut.excecoes.SenhaInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.TipoEmpresaInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.TipoMercadoInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.excecoes.UsuarioNaoEncontradoException;
import br.ufal.ic.p2.jackut.excecoes.UsuarioNaoEntregadorException;
import br.ufal.ic.p2.jackut.excecoes.UsuarioNaoPodeCriarUmaEmpresaException;
import br.ufal.ic.p2.jackut.excecoes.ValorInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.VeiculoInvalidoException;

import br.ufal.ic.p2.jackut.pedido.Pedido;


public class Facade {

    private ControleDados controleDados;
    private Autentificar autentificar;
    private ControleUsuarios controleUsuarios;
    private ControleEmpresa controleEmpresas;
    private ControleProdutos controleProdutos;
    private ControlePedido controlePedido;
    private ControleEntregas controleEntregas;
    private Login elogin;

    public Facade() {
        this.controleDados = new ControleDados();
        this.autentificar = new Autentificar(controleDados);
        this.controleEmpresas = new ControleEmpresa(controleDados, autentificar);
        this.controleUsuarios = new ControleUsuarios(controleDados, autentificar);
        this.controleProdutos = new ControleProdutos(controleDados);
        this.controlePedido = new ControlePedido(controleDados);
        this.controleEntregas = new ControleEntregas(controleDados);
        this.elogin = new Login(controleDados);
        controleDados.carregarUsuarios("usuarios.xml");
        controleDados.carregarEmpresas("empresas.xml");
        controleDados.carregarProdutos("produtos.xml");
        controleDados.carregarPedidos("pedidos.xml");
        controleDados.carregarEntregas("entregas.xml");
    }

    public void zerarSistema() {
        controleDados.limparArquivos("usuarios.xml", "empresas.xml", "produtos.xml", "pedidos.xml", "entregas.xml");
    }

    public void encerrarSistema() {
        controleDados.persistenciaUsuarios("usuarios.xml");
        controleDados.persistenciaEmpresas("empresas.xml");
        controleDados.persistenciaProdutos("produtos.xml");
        controleDados.persistenciaPedidos("pedidos.xml");
        controleDados.persistenciaEntregas("entregas.xml");

    }

    public String getAtributoUsuario(String id, String nome) throws UsuarioNaoCadastradoException {

        return controleUsuarios.buscarAtributos(id, nome);

    }

    public String login(String email, String senha) throws LoginException {
        return elogin.login(email, senha);
    }

    public String criarUsuario(String nome, String email, String senha, String endereco)throws NomeInvalidoException, 
    SenhaInvalidoException, EnderecoInvalidoException, EmailInvalidoException, FormatoEmailInvalidoException, ContaEmailExisteException {
        return controleUsuarios.criarUsuario(nome, email, senha, endereco);
    }

    public String criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws NomeInvalidoException, 
        SenhaInvalidoException, EnderecoInvalidoException, EmailInvalidoException, CpfInvalidoException,
        FormatoEmailInvalidoException, ContaEmailExisteException {
        return controleUsuarios.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public String criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) 
    throws NomeInvalidoException, SenhaInvalidoException,EnderecoInvalidoException, EmailInvalidoException,
    ContaEmailExisteException,  VeiculoInvalidoException, PlacaInvalidoException {
        return controleUsuarios.criarUsuario(nome, email, senha, endereco, veiculo, placa);
    }    

    public void cadastrarEntregador(String empresaId, String entregadorId) throws EmpresaNaoEncontradaException, 
        UsuarioNaoEntregadorException, EntregadorjaAssociadoEmpresaException {
        controleUsuarios.cadastrarEntregador(empresaId, entregadorId);
    }


    public String getEmpresas(String entregadorId) throws UsuarioNaoEntregadorException {
        return controleUsuarios.getEmpresas(entregadorId);
    }
    
    public String getEntregadores(String empresaId) {
        return controleUsuarios.getEntregadores(empresaId);
    }
    

    public String criarEmpresa(String tipoEmpresa, String donoID, String nome, String endereco, String tipoCozinha) 
        throws UsuarioNaoPodeCriarUmaEmpresaException, NomeEmpresaInvalidoException, EnderecoInvalidoException, 
        TipoEmpresaInvalidoException , EmpresaComEsseNomeJaExisteException, ProibidoCadastrarDuasEmpresasComMesmoNomeLocalException{
        return controleEmpresas.criarEmpresa(tipoEmpresa, donoID, nome, endereco, tipoCozinha);

    }
    public String criarEmpresa(String tipoEmpresa, String donoID, String nome, String endereco, String abre, String fecha, String tipoMercado) 
            throws UsuarioNaoPodeCriarUmaEmpresaException, NomeInvalidoException, 
            TipoEmpresaInvalidoException, EnderecoEmpresaInvalidoException, HorarioInvalidoException, TipoMercadoInvalidoException,
            FormatoHoraInvalidoException, EmpresaComEsseNomeJaExisteException, ProibidoCadastrarDuasEmpresasComMesmoNomeLocalException{
        return controleEmpresas.criarEmpresa(tipoEmpresa, donoID, nome, endereco, abre, fecha, tipoMercado);

    }

     public void alterarFuncionamento(String id, String abre, String fecha) throws HorarioInvalidoException, 
        FormatoHoraInvalidoException, EmpresaNaoFoiEncontradaException, NaoMercadoValidoException {
            controleEmpresas.alterarFuncionamento(id, abre, fecha);
    }

    public String criarEmpresa(String tipoEmpresa, String donoID, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios) 
        throws UsuarioNaoPodeCriarUmaEmpresaException, NomeInvalidoException, TipoEmpresaInvalidoException, 
            EnderecoEmpresaInvalidoException,  EmpresaComEsseNomeJaExisteException, ProibidoCadastrarDuasEmpresasComMesmoNomeLocalException{
        return controleEmpresas.criarEmpresa(tipoEmpresa, donoID, nome, endereco, aberto24Horas, numeroFuncionarios);
    }
    
    public String getAtributoEmpresa(String empresa, String atributo) throws AtributoInvalidoException, EmpresaNaoCadastradaException{
        return controleEmpresas.getAtributoEmpresa(empresa, atributo);

    }

    public String getEmpresasDoUsuario(int donoID) throws UsuarioNaoPodeCriarUmaEmpresaException{
        return controleEmpresas.getEmpresasDoUsuario(String.valueOf(donoID));
    }

    public String getIdEmpresa(String donoID, String atributo, int indice) throws NomeInvalidoException, 
        IndiceInvalidoException, IndiceMaiorQueEsperadoException, NaoExisteEmpresaComEsseNomeException {
        return controleEmpresas.getIdEmpresa(donoID, atributo, indice);
    }

    public String criarProduto(String empresaId, String nome, float valor, String categoria) throws ProdutoExistenteException,
      NomeInvalidoException, ValorInvalidoException,EmpresaNaoEncontradaException, CategoriaInvalidoException {
        return controleProdutos.criarProduto(empresaId, nome, valor, categoria);
    }

    public void editarProduto(String id, String nome, float valor, String categoria)throws NomeInvalidoException, ValorInvalidoException,
    ProdutoNaoCadastradoException,CategoriaInvalidoException {
        controleProdutos.editarProduto(id, nome, valor, categoria);
    }

    public String getProduto(String nome, String empresaId, String atributo) throws NomeInvalidoException, AtributoInvalidoException, 
    EmpresaNaoEncontradaException, ProdutoNaoEncontradoException, AtributoNaoExisteException  {
        return controleProdutos.getProduto(nome, empresaId, atributo);
    }

    public String listarProdutos(String empresaId) throws EmpresaNaoEncontradaException  {
        return controleProdutos.listarProdutos(empresaId);
    }

    public String criarPedido(String clienteID, String empresaID) throws ClienteNaoEncontradoException, 
        DonoEmpresaNaoPodeFazerPedidoException, EmpresaNaoEncontradaException, NaoPermitidoDoisPedidosAbertoMesmaEmpresaException {
        return controlePedido.criarPedido(clienteID, empresaID);
    }

    public String getNumeroPedido(String clienteID, String empresaID, int indice) {
        return controlePedido.getNumeroPedido(clienteID, empresaID, indice);
    }

    public void adicionarProduto(String pedidoID, String produtoID) throws NaoExistePedidoAbertoException, 
        NaoPossivelAdcionarProdutosPedidoFechadoException, ProdutoNaoEncontradoException, ProdutoNaoPertenceEmpresaException {
        controlePedido.adicionarProduto(pedidoID, produtoID);
    }

    public String getPedidos(String pedidoID, String atributo) throws ProdutoNaoEncontradoException, AtributoInvalidoException, 
    ClienteNaoEncontradoException, EmpresaNaoEncontradaException, AtributoNaoExisteException {
        return controlePedido.getPedidos(pedidoID, atributo);
    }

    public void fecharPedido(String pedidoID)  throws PedidoNaoEncontradoException {
        controlePedido.fecharPedido(pedidoID);
    }

    public void removerProduto(String pedidoID, String produtoNome) throws PedidoNaoEncontradoException, 
        NaoPossivelRemoverProdutosPedidoFechadoException, ProdutoInvalidoException, ProdutoNaoEncontradoException {
        controlePedido.removerProduto(pedidoID, produtoNome);
    }

    public String criarEntrega(String pedidoID, String entregadorID, String destino) throws PedidoNaoEncontradoException, 
        PedidoNaoEstaProntoParaEntregaException, NaoEntregadorValidoException, EntregadorAindaEmEntregaException {
        return controleEntregas.criarEntrega(pedidoID, entregadorID, destino);
    }

    public void liberarPedido(String numero) throws PedidoNaoEncontradoException, PedidoJaLiberadoException,
         NaoPossivelLiberarProdutoQueNaoEstaSendoPreparadoException {
        controleEntregas.liberarPedido(numero);
    }

    public String obterPedido(String entregadorID) throws UsuarioNaoEncontradoException, UsuarioNaoEntregadorException, 
        EntregadorNaoEstarEmNenhumaEmpresaException, NaoExistePedidoParaEntregaException {
        Pedido pedido = controleEntregas.obterPedido(entregadorID);
        return pedido.getPedidoID(); 
    }
    
    public String getEntrega(String id, String atributos)  throws AtributoInvalidoException, EntregaNaoEncontradaException, 
        EntregadorNaoEncontradoException, AtributoNaoExisteException {
        return controleEntregas.getEntrega(id, atributos);
    }
    public String getIdEntrega(String pedidoID) throws NaoExisteEntregaComEsseIDException {
        return controleEntregas.getIdEntrega(pedidoID);
    }

    public void entregar(String entregaID) throws NaoExisteNadaParaSerEntregueComEsseIDException {
        controleEntregas.entregar(entregaID);
    }
    
    
}
