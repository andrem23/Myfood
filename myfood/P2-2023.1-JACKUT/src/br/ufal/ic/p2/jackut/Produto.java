package br.ufal.ic.p2.jackut;

public class Produto {
    private String id;
    private String empresaID;
    private String nome;
    private float valor;
    private String categoria;

    public Produto(String id, String empresaID, String nome, float valor, String categoria) {
        this.id = id;
        this.empresaID = empresaID;
        this.nome = nome;
        this.valor = valor;
        this.categoria = categoria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmpresaID() {
        return empresaID;
    }

    public void setEmpresaID(String empresaID) {
        this.empresaID = empresaID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
