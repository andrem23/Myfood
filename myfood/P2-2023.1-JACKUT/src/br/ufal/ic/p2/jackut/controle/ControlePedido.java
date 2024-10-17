package br.ufal.ic.p2.jackut.controle;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import br.ufal.ic.p2.jackut.empresa.Empresa;
import br.ufal.ic.p2.jackut.excecoes.AtributoInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.AtributoNaoExisteException;
import br.ufal.ic.p2.jackut.excecoes.ClienteNaoEncontradoException;
import br.ufal.ic.p2.jackut.excecoes.DonoEmpresaNaoPodeFazerPedidoException;
import br.ufal.ic.p2.jackut.excecoes.EmpresaNaoEncontradaException;
import br.ufal.ic.p2.jackut.excecoes.NaoExistePedidoAbertoException;
import br.ufal.ic.p2.jackut.excecoes.NaoPermitidoDoisPedidosAbertoMesmaEmpresaException;
import br.ufal.ic.p2.jackut.excecoes.NaoPossivelAdcionarProdutosPedidoFechadoException;
import br.ufal.ic.p2.jackut.excecoes.NaoPossivelRemoverProdutosPedidoFechadoException;
import br.ufal.ic.p2.jackut.excecoes.PedidoNaoEncontradoException;
import br.ufal.ic.p2.jackut.excecoes.ProdutoInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.ProdutoNaoEncontradoException;
import br.ufal.ic.p2.jackut.excecoes.ProdutoNaoPertenceEmpresaException;
import br.ufal.ic.p2.jackut.pedido.Pedido;
import br.ufal.ic.p2.jackut.produto.Produto;
import br.ufal.ic.p2.jackut.usuario.DonoEstabelecimento;
import br.ufal.ic.p2.jackut.usuario.Usuario;

public class ControlePedido {
    private ControleDados controleDados;
    private int contadorId;

    public ControlePedido(ControleDados controleDados) {
        this.controleDados = controleDados;
        this.contadorId = controleDados.getPedidos().size() + 1;
    }

    private String gerarNovoId() {
        return String.valueOf(contadorId++);
    }

    public String criarPedido(String clienteID, String empresaID) throws ClienteNaoEncontradoException,
            DonoEmpresaNaoPodeFazerPedidoException, EmpresaNaoEncontradaException,
            NaoPermitidoDoisPedidosAbertoMesmaEmpresaException {
        Usuario cliente = controleDados.getUsuarios().get(clienteID);
        Empresa empresa = controleDados.getEmpresas().get(empresaID);
        if (cliente == null) {
            throw new ClienteNaoEncontradoException();
        }
        if (cliente instanceof DonoEstabelecimento) {
            throw new DonoEmpresaNaoPodeFazerPedidoException();
        }

        if (empresa == null) {
            throw new EmpresaNaoEncontradaException();
        }

        boolean pedidoAberto = controleDados.getPedidos().values().stream()
                .anyMatch(p -> p.getClienteID().equals(clienteID) &&
                        p.getEmpresaID().equals(empresaID) &&
                        p.getEstado().equals("aberto"));

        if (pedidoAberto) {
            throw new NaoPermitidoDoisPedidosAbertoMesmaEmpresaException();
        }

        String pedidoID = gerarNovoId();
        Pedido pedido = new Pedido(pedidoID, clienteID, empresaID, "aberto", new ArrayList<>(), 0.0f);
        controleDados.getPedidos().put(pedidoID, pedido); // Salva no ControleDados

        return pedidoID;
    }

    public String getNumeroPedido(String clienteID, String empresaID, int indice) {
        List<Pedido> listaPedidos = controleDados.getPedidos().values().stream()
                .filter(p -> p.getClienteID().equals(clienteID) && p.getEmpresaID().equals(empresaID))
                .sorted((p1, p2) -> p1.getPedidoID().compareTo(p2.getPedidoID()))
                .collect(Collectors.toList());

        return listaPedidos.get(indice).getPedidoID();
    }

    public void adicionarProduto(String pedidoID, String produtoID) throws NaoExistePedidoAbertoException,
            NaoPossivelAdcionarProdutosPedidoFechadoException, ProdutoNaoEncontradoException,
            ProdutoNaoPertenceEmpresaException {

        Pedido pedido = controleDados.getPedidos().get(pedidoID);

        if (pedido == null) {
            throw new NaoExistePedidoAbertoException();
        }

        if (!pedido.getEstado().equals("aberto")) {
            throw new NaoPossivelAdcionarProdutosPedidoFechadoException();
            // Corrigida a mensagem para "adicionar" ao invés de "adcionar"
        }

        Produto produto = controleDados.getProdutos().get(produtoID);
        if (produto == null) {
            throw new ProdutoNaoEncontradoException();
        }

        if (!produto.getEmpresaID().equals(pedido.getEmpresaID())) {
            throw new ProdutoNaoPertenceEmpresaException();
        }

        pedido.getProdutos().add(produto);
        atualizarValorPedido(pedido);
        controleDados.getPedidos().put(pedidoID, pedido);
    }

    public String getPedidos(String pedidoID, String atributo)
            throws ProdutoNaoEncontradoException, AtributoInvalidoException,
            ClienteNaoEncontradoException, EmpresaNaoEncontradaException, AtributoNaoExisteException {

        Pedido pedido = controleDados.getPedidos().get(pedidoID);
        if (pedido == null) {
            throw new ProdutoNaoEncontradoException();
        }

        if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException();
        }

        switch (atributo) {

            case "cliente":
                Usuario cliente = controleDados.getUsuarios().get(pedido.getClienteID());
                if (cliente == null)
                    throw new ClienteNaoEncontradoException();
                return cliente.getNome();
            case "empresa":
                Empresa empresa = controleDados.getEmpresas().get(pedido.getEmpresaID());
                if (empresa == null)
                    throw new EmpresaNaoEncontradaException();
                return empresa.getNome();
            case "estado":
                return pedido.getEstado();
            case "valor":
                DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
                DecimalFormat df = new DecimalFormat("0.00", dfs);
                return df.format(pedido.getValor());
            case "produtos":
                List<String> produtoNomes = pedido.getProdutos().stream()
                        .map(Produto::getNome)
                        .collect(Collectors.toList());
                return "{[" + String.join(", ", produtoNomes) + "]}";
            default:
                throw new AtributoNaoExisteException();
        }
    }

    public void fecharPedido(String pedidoID) throws PedidoNaoEncontradoException {
        Pedido pedido = controleDados.getPedidos().get(pedidoID);
        if (pedido == null) {
            throw new PedidoNaoEncontradoException();
        }

        pedido.setEstado("preparando");
        controleDados.getPedidos().put(pedidoID, pedido);
    }

    public void removerProduto(String pedidoID, String produtoNome) throws PedidoNaoEncontradoException,
            NaoPossivelRemoverProdutosPedidoFechadoException, ProdutoInvalidoException, ProdutoNaoEncontradoException {

        Pedido pedido = controleDados.getPedidos().get(pedidoID);
        if (pedido == null) {
            throw new PedidoNaoEncontradoException();
        }
        if (!pedido.getEstado().equals("aberto")) {
            throw new NaoPossivelRemoverProdutosPedidoFechadoException();
        }
        if (produtoNome == null || produtoNome.isEmpty()) {
            throw new ProdutoInvalidoException();
        }

        boolean produtoRemovido = false;
        for (Produto produto : pedido.getProdutos()) {
            if (produto.getNome().equals(produtoNome)) {
                pedido.getProdutos().remove(produto);
                produtoRemovido = true;
                break;
            }
        }
        if (!produtoRemovido) {
            throw new ProdutoNaoEncontradoException();
        }

        atualizarValorPedido(pedido);
        controleDados.getPedidos().put(pedidoID, pedido);
    }

    private void atualizarValorPedido(Pedido pedido) {
        float valorTotal = pedido.getProdutos().stream()
                .map(Produto::getValor)
                .reduce(0.0f, Float::sum);

        pedido.setValor(valorTotal);
        controleDados.getPedidos().put(pedido.getPedidoID(), pedido);
    }
}