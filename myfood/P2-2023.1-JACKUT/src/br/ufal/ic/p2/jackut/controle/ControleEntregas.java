package br.ufal.ic.p2.jackut.controle;

import br.ufal.ic.p2.jackut.empresa.Empresa;
import br.ufal.ic.p2.jackut.entrega.Entrega;
import br.ufal.ic.p2.jackut.excecoes.AtributoInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.AtributoNaoExisteException;
import br.ufal.ic.p2.jackut.excecoes.EntregaNaoEncontradaException;
import br.ufal.ic.p2.jackut.excecoes.EntregadorAindaEmEntregaException;
import br.ufal.ic.p2.jackut.excecoes.EntregadorNaoEncontradoException;
import br.ufal.ic.p2.jackut.excecoes.EntregadorNaoEstarEmNenhumaEmpresaException;
import br.ufal.ic.p2.jackut.excecoes.NaoEntregadorValidoException;
import br.ufal.ic.p2.jackut.excecoes.NaoExisteEntregaComEsseIDException;
import br.ufal.ic.p2.jackut.excecoes.NaoExisteNadaParaSerEntregueComEsseIDException;
import br.ufal.ic.p2.jackut.excecoes.NaoExistePedidoParaEntregaException;
import br.ufal.ic.p2.jackut.excecoes.NaoPossivelLiberarProdutoQueNaoEstaSendoPreparadoException;
import br.ufal.ic.p2.jackut.excecoes.PedidoJaLiberadoException;
import br.ufal.ic.p2.jackut.excecoes.PedidoNaoEncontradoException;
import br.ufal.ic.p2.jackut.excecoes.PedidoNaoEstaProntoParaEntregaException;
import br.ufal.ic.p2.jackut.excecoes.UsuarioNaoEncontradoException;
import br.ufal.ic.p2.jackut.excecoes.UsuarioNaoEntregadorException;
import br.ufal.ic.p2.jackut.pedido.Pedido;
import br.ufal.ic.p2.jackut.usuario.Cliente;
import br.ufal.ic.p2.jackut.usuario.Entregador;
import br.ufal.ic.p2.jackut.usuario.Usuario;
import br.ufal.ic.p2.jackut.produto.Produto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ControleEntregas {
    private ControleDados controleDados;
    private int contadorId;

    public ControleEntregas(ControleDados controleDados) {
        this.controleDados = controleDados;
        this.contadorId = 1; // Inicializa o contador de IDs de entrega
    }

    private String gerarNovoId() {
        return String.valueOf(contadorId++); // Gera um novo ID único para cada entrega
    }

    public String getIdEntrega(int pedidoID) throws NaoExisteEntregaComEsseIDException {
        for (Entrega entrega : controleDados.getEntregas().values()) {
            if (entrega.getPedidoID().equals(String.valueOf(pedidoID))) {
                return entrega.getIdEntrega();
            }
        }
        throw new NaoExisteEntregaComEsseIDException();
    }

    public void entregar(String entregaID) throws NaoExisteNadaParaSerEntregueComEsseIDException {
        Entrega entrega = controleDados.getEntregas().get(entregaID);

        if (entrega == null) {
            throw new NaoExisteNadaParaSerEntregueComEsseIDException ();
        }

        // Altera o estado do pedido para "entregue"
        Pedido pedido = controleDados.getPedidos().get(entrega.getPedidoID());
        if (pedido != null) {
            pedido.setEstado("entregue");
            controleDados.setPedidos(pedido.getPedidoID(), pedido);
        }

        entrega.setEntregue(true);
        controleDados.setEntregas(entregaID, entrega);
        // controleDados.getEntregas().remove(entregaID);
    }

    public void liberarPedido(String pedidoID) throws PedidoNaoEncontradoException, PedidoJaLiberadoException,
         NaoPossivelLiberarProdutoQueNaoEstaSendoPreparadoException {

        Pedido pedido = controleDados.getPedidos().get(pedidoID);
        if (pedido == null) {
            throw new PedidoNaoEncontradoException();
        }

        // Verifica se o estado do pedido é "pronto"
        if (pedido.getEstado().equals("pronto")) {
            throw new PedidoJaLiberadoException ();
        }

        // Verifica se o estado do pedido é "preparando"
        if (!pedido.getEstado().equals("preparando")) {
            throw new NaoPossivelLiberarProdutoQueNaoEstaSendoPreparadoException();
        }

        // Se tudo estiver certo, atualiza o estado do pedido para "pronto"
        pedido.setEstado("pronto");
        controleDados.setPedidos(pedidoID, pedido); // Atualiza o pedido no controle de dados
    }

    private String obterEnderecoCliente(String pedidoID) {
        Pedido pedido = controleDados.getPedidos().get(pedidoID);

        // Obtendo o cliente e assumindo que ele é uma instância de Cliente
        Cliente cliente = (Cliente) controleDados.getUsuarios().get(pedido.getClienteID());

        // Retorna o endereço do cliente diretamente
        return cliente.getEndereco();
    }

    
    public String criarEntrega(String pedidoID, String entregadorID, String destino) throws PedidoNaoEncontradoException, 
        PedidoNaoEstaProntoParaEntregaException, NaoEntregadorValidoException, EntregadorAindaEmEntregaException {
        
        Pedido pedido = controleDados.getPedidos().get(pedidoID);

        if (pedido == null) {
            throw new PedidoNaoEncontradoException();
        }

        if (!pedido.getEstado().equals("pronto")) {
            throw new PedidoNaoEstaProntoParaEntregaException();
        }

        Usuario usuario = controleDados.getUsuarios().get(entregadorID);
        if (usuario == null || !(usuario instanceof Entregador)) {
            throw new NaoEntregadorValidoException ();
        }

        boolean entregadorEmEntrega = controleDados.getEntregas().values().stream()
                .anyMatch(entrega -> entrega.getEntregadorID().equals(entregadorID) && !entrega.isEntregue());

        if (entregadorEmEntrega) {
            throw new EntregadorAindaEmEntregaException ();
        }

        if (destino == null || destino.isEmpty()) {
            Cliente cliente = (Cliente) controleDados.getUsuarios().get(pedido.getClienteID());
            destino = cliente.getEndereco();
        }

        String nomeCliente = controleDados.getUsuarios().get(pedido.getClienteID()).getNome();
        String nomeEmpresa = controleDados.getEmpresas().get(pedido.getEmpresaID()).getNome();

        Entrega entrega = new Entrega(gerarNovoId(), nomeCliente, nomeEmpresa, pedidoID, entregadorID, destino,
                pedido.getProdutos());
        controleDados.setEntregas(entrega.getIdEntrega(), entrega);
        pedido.setEstado("entregando");
        controleDados.setPedidos(pedidoID, pedido);

        return entrega.getIdEntrega();
    }

    public Pedido obterPedido(String entregadorID) throws UsuarioNaoEncontradoException, UsuarioNaoEntregadorException, 
        EntregadorNaoEstarEmNenhumaEmpresaException, NaoExistePedidoParaEntregaException  {

        Usuario usuario = controleDados.getUsuarios().get(entregadorID);

        if (usuario == null) {
            throw new UsuarioNaoEncontradoException();
        }

        if (!(usuario instanceof Entregador)) {
            throw new UsuarioNaoEntregadorException();
        }

        Entregador entregador = (Entregador) usuario;

        if (entregador.getEmpresasCadastradas().isEmpty()) {
            throw new EntregadorNaoEstarEmNenhumaEmpresaException ();
        }

        // Filtra os pedidos prontos e os separa por tipo
        List<Pedido> pedidosProntos = controleDados.getPedidos().values().stream()
                .filter(pedido -> pedido.getEstado().equals("pronto"))

                .filter(pedido -> entregador.getEmpresasCadastradas().contains(pedido.getEmpresaID()))
                .sorted(Comparator.comparing(pedido -> {
                    Empresa empresa = controleDados.getEmpresas().get(pedido.getEmpresaID());
                    return empresa.getTipoEmpresa().equals("farmacia") ? 0 : 1; // Prioriza farmácias
                }))
                .collect(Collectors.toList());

        if (pedidosProntos.isEmpty()) {
            throw new NaoExistePedidoParaEntregaException ();
        }

        return pedidosProntos.get(0); // Retorna o primeiro pedido pronto
    }

    public String getEntrega(String id, String atributo) throws AtributoInvalidoException, EntregaNaoEncontradaException, 
        EntregadorNaoEncontradoException, AtributoNaoExisteException {

        Entrega entrega = controleDados.getEntregas().get(id);

        if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException();
        }

        if (entrega == null) {

            throw new EntregaNaoEncontradaException();

        }

        // Verifica qual atributo foi solicitado e retorna o respectivo valor
        switch (atributo) {
            case "cliente":
                return entrega.getNomeCliente();
            case "empresa":
                return entrega.getNomeEmpresa(); 
            case "pedido":
                return entrega.getPedidoID();
            case "entregador":
                Usuario entregador = controleDados.getUsuarios().get(entrega.getEntregadorID());
                if (entregador == null) {
                    throw new EntregadorNaoEncontradoException();
                }
                return entregador.getNome();
            case "destino":
                return entrega.getDestino();
            case "produtos":
                String nomesProdutos = entrega.getProdutos().stream()
                        .map(Produto::getNome)
                        .collect(Collectors.joining(", "));
                return "{" + "[" + nomesProdutos + "]" + "}";
            default:
                throw new AtributoNaoExisteException();
        }
    }

    public String getIdEntrega(String pedidoID) throws NaoExisteEntregaComEsseIDException {
        // Procura a entrega correspondente ao pedidoID
        for (Entrega entrega : controleDados.getEntregas().values()) {
            if (entrega.getPedidoID().equals(pedidoID)) {
                return entrega.getIdEntrega(); // Retorna o ID da entrega correspondente
            }
        }

        throw new NaoExisteEntregaComEsseIDException();
    }

}
