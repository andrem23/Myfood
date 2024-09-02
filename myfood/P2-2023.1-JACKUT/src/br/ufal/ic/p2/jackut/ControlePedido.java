package br.ufal.ic.p2.jackut;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

    // criarPedido
    public String criarPedido(String clienteID, String empresaID) {
        Usuario cliente = controleDados.getUsuarios().get(clienteID);
        Empresa empresa = controleDados.getEmpresas().get(empresaID);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente nao encontrado");
        }
        if (cliente instanceof DonoEstabelecimento) {
            throw new IllegalArgumentException("Dono de empresa nao pode fazer um pedido");
        }

        if (empresa == null) {
            throw new IllegalArgumentException("Empresa nao encontrada");
        }

        boolean pedidoAberto = controleDados.getPedidos().values().stream()
                .anyMatch(p -> p.getClienteID().equals(clienteID) &&
                        p.getEmpresaID().equals(empresaID) &&
                        p.getEstado().equals("aberto"));

        if (pedidoAberto) {
            throw new IllegalArgumentException("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
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

        if (indice < 0 || indice >= listaPedidos.size()) {
            throw new IllegalArgumentException("Índice inválido");
        }

        return listaPedidos.get(indice).getPedidoID();
    }

    public void adicionarProduto(String pedidoID, String produtoID) {
        Pedido pedido = controleDados.getPedidos().get(pedidoID);

        if (pedido == null) {
            throw new IllegalArgumentException("Nao existe pedido em aberto");
        }

        if (!pedido.getEstado().equals("aberto")) {
            throw new IllegalArgumentException("Nao e possivel adcionar produtos a um pedido fechado");
            // Corrigida a mensagem para "adicionar" ao invés de "adcionar"
        }

        Produto produto = controleDados.getProdutos().get(produtoID);
        if (produto == null) {
            throw new IllegalArgumentException("Produto nao encontrado");
        }

        if (!produto.getEmpresaID().equals(pedido.getEmpresaID())) {
            throw new IllegalArgumentException("O produto nao pertence a essa empresa");
        }

        pedido.getProdutos().add(produto);
        atualizarValorPedido(pedido);
        controleDados.getPedidos().put(pedidoID, pedido);
    }

    public String getPedidos(String pedidoID, String atributo) {
        Pedido pedido = controleDados.getPedidos().get(pedidoID);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido nao encontrado");
        }

        if (atributo == null || atributo.isEmpty()) {
            throw new IllegalArgumentException("Atributo invalido");
        }

        switch (atributo) {

            case "cliente":
                Usuario cliente = controleDados.getUsuarios().get(pedido.getClienteID());
                if (cliente == null)
                    throw new IllegalArgumentException("Cliente nao encontrado");
                return cliente.getNome();
            case "empresa":
                Empresa empresa = controleDados.getEmpresas().get(pedido.getEmpresaID());
                if (empresa == null)
                    throw new IllegalArgumentException("Empresa nao encontrada");
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
                throw new IllegalArgumentException("Atributo nao existe");
        }
    }

    public void fecharPedido(String pedidoID) {
        Pedido pedido = controleDados.getPedidos().get(pedidoID);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido nao encontrado");
        }

        pedido.setEstado("preparando");
        controleDados.getPedidos().put(pedidoID, pedido);
    }

    public void removerProduto(String pedidoID, String produtoNome) {
        Pedido pedido = controleDados.getPedidos().get(pedidoID);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido nao encontrado");
        }
        if (!pedido.getEstado().equals("aberto")) {
            throw new IllegalArgumentException("Nao e possivel remover produtos de um pedido fechado");
        }
        if (produtoNome == null || produtoNome.isEmpty()) {
            throw new IllegalArgumentException("Produto invalido");
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
            throw new IllegalArgumentException("Produto nao encontrado");
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