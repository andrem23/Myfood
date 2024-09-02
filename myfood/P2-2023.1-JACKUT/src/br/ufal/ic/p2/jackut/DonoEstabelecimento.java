package br.ufal.ic.p2.jackut;

public class DonoEstabelecimento extends Usuario {
    private String cpf;
    private String endereco;

    public DonoEstabelecimento(String id, String nome, String email, String senha, String cpf, String endereco) {
        super(id, nome, email, senha);
        this.cpf = cpf;
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean usuarioDonoEmpresa() {
        return true;
    }
}
