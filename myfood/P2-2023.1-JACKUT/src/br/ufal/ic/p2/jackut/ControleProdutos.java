package br.ufal.ic.p2.jackut;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ControleProdutos {
    private ControleDados controleDados;
    private int contadorId;

    public ControleProdutos(ControleDados controleDados) {
        this.controleDados = controleDados;
        this.contadorId = 1;
    }

    private String gerarNovoId() {
        return String.valueOf(contadorId++);
    }

    public String criarProduto(String empresaID, String nome, float valor, String categoria) {
        Empresa empresa = controleDados.getEmpresas().get(empresaID);

        if (empresa == null) {
            throw new IllegalArgumentException("Empresa não encontrada");
        }

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome invalido");
        }
        if (valor < 0) {
            throw new IllegalArgumentException("Valor invalido");
        }
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Categoria invalido");
        }

        // Verificar se o produto já existe na empresa
        for (Produto produto : controleDados.getProdutos().values()) {
            if (produto.getNome().equals(nome) && produto.getEmpresaID().equals(empresaID)) {
                throw new IllegalArgumentException("Ja existe um produto com esse nome para essa empresa");
            }
        }

        // Criar o produto
        String id = gerarNovoId();
        Produto produto = new Produto(id, empresaID, nome, valor, categoria);

        controleDados.setProdutos(id, produto);

        return id;
    }

    public void editarProduto(String id, String nome, float valor, String categoria) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome invalido");
        }
        if (valor < 0) {
            throw new IllegalArgumentException("Valor invalido");
        }
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Categoria invalido");
        }

        Produto produto = controleDados.getProdutos().get(id);
        if (produto == null) {
            throw new IllegalArgumentException("Produto nao cadastrado");
        }

        produto.setNome(nome);
        produto.setValor(valor);
        produto.setCategoria(categoria);
    }

    public String getProduto(String nome, String empresaID, String atributo) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome invalido");
        }
        if (atributo == null || atributo.trim().isEmpty()) {
            throw new IllegalArgumentException("Atributo invalido");
        }

        for (Produto produto : controleDados.getProdutos().values()) {
            if (produto.getNome().equals(nome) && produto.getEmpresaID().equals(empresaID)) {
                switch (atributo) {
                    case "nome":
                        return produto.getNome();
                    case "valor":
                        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
                        DecimalFormat df = new DecimalFormat("0.00", dfs);
                        return df.format(produto.getValor());
                    case "categoria":
                        return produto.getCategoria();
                    case "empresa":
                        Empresa empresa = controleDados.getEmpresas().get(empresaID);
                        if (empresa != null) {
                            return empresa.getNome();
                        } else {
                            throw new IllegalArgumentException("Empresa nao encontrada");
                        }
                    default:
                        throw new IllegalArgumentException("Atributo nao existe");
                }
            }
        }

        throw new IllegalArgumentException("Produto nao encontrado");
    }

    public String listarProdutos(String empresaID) {

        Empresa empresa = controleDados.getEmpresas().get(empresaID);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa nao encontrada");
        }

        StringBuilder produtosList = new StringBuilder();
        for (Produto produto : controleDados.getProdutos().values()) {
            if (produto.getEmpresaID().equals(empresaID)) {
                if (produtosList.length() > 0) {
                    produtosList.append(", ");
                }
                produtosList.append(produto.getNome());
            }
        }

        return "{[" + produtosList.toString() + "]}";
    }

}
