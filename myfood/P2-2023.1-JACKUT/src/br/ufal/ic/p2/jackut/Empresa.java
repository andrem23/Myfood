package br.ufal.ic.p2.jackut;

public abstract class Empresa {
    private String id;
    private String nome;
    private String donoID;
    private String endereco;
    private String tipoEmpresa;

    public Empresa(String id, String nome, String donoID, String endereco, String tipoEmpresa) {
        this.id = id;
        this.donoID = donoID;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoEmpresa = tipoEmpresa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDonoID() {
        return donoID;
    }

    public void setDonoID(Usuario usuario) {
        if (usuario instanceof DonoEstabelecimento) {
            this.donoID = usuario.getId();
        }
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(String tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }
}
