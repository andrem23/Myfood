package br.ufal.ic.p2.jackut.controle;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import br.ufal.ic.p2.jackut.empresa.Empresa;
import br.ufal.ic.p2.jackut.excecoes.AtributoInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.AtributoNaoExisteException;
import br.ufal.ic.p2.jackut.excecoes.CategoriaInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.EmpresaNaoEncontradaException;
import br.ufal.ic.p2.jackut.excecoes.NomeInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.ProdutoExistenteException;
import br.ufal.ic.p2.jackut.excecoes.ProdutoNaoCadastradoException;
import br.ufal.ic.p2.jackut.excecoes.ProdutoNaoEncontradoException;
import br.ufal.ic.p2.jackut.excecoes.ValorInvalidoException;
import br.ufal.ic.p2.jackut.produto.Produto;

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

    public String criarProduto(String empresaID, String nome, float valor, String categoria)
            throws ProdutoExistenteException, NomeInvalidoException,
            ValorInvalidoException, EmpresaNaoEncontradaException, CategoriaInvalidoException {
        Empresa empresa = controleDados.getEmpresas().get(empresaID);

        if (empresa == null) {
            throw new EmpresaNaoEncontradaException();
        }

        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeInvalidoException();
        }
        if (valor < 0) {
            throw new ValorInvalidoException();
        }
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new CategoriaInvalidoException();
        }

        for (Produto produto : controleDados.getProdutos().values()) {
            if (produto.getNome().equals(nome) && produto.getEmpresaID().equals(empresaID)) {
                throw new ProdutoExistenteException();
            }
        }

        String id = gerarNovoId();
        Produto produto = new Produto(id, empresaID, nome, valor, categoria);

        controleDados.setProdutos(id, produto);

        return id;
    }

    public void editarProduto(String id, String nome, float valor, String categoria)
            throws NomeInvalidoException, ValorInvalidoException,
            ProdutoNaoCadastradoException, CategoriaInvalidoException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeInvalidoException();
        }
        if (valor < 0) {
            throw new ValorInvalidoException();
        }
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new CategoriaInvalidoException();
        }

        Produto produto = controleDados.getProdutos().get(id);
        if (produto == null) {
            throw new ProdutoNaoCadastradoException();
        }

        produto.setNome(nome);
        produto.setValor(valor);
        produto.setCategoria(categoria);
    }

    public String getProduto(String nome, String empresaID, String atributo)
            throws NomeInvalidoException, AtributoInvalidoException,
            EmpresaNaoEncontradaException, ProdutoNaoEncontradoException, AtributoNaoExisteException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeInvalidoException();
        }
        if (atributo == null || atributo.trim().isEmpty()) {
            throw new AtributoInvalidoException();
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
                            throw new EmpresaNaoEncontradaException();
                        }
                    default:
                        throw new AtributoNaoExisteException();
                }
            }
        }

        throw new ProdutoNaoEncontradoException();
    }

    public String listarProdutos(String empresaID) throws EmpresaNaoEncontradaException {

        Empresa empresa = controleDados.getEmpresas().get(empresaID);
        if (empresa == null) {
            throw new EmpresaNaoEncontradaException();
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
