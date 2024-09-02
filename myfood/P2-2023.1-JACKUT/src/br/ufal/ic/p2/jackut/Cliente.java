package br.ufal.ic.p2.jackut;

public class Cliente extends Usuario {
    public Cliente(String id, String nome, String email, String senha, String endereco) {
        super(id, nome, email, senha);
        this.endereco = endereco;
    }

    private String endereco;

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean usuarioCliente() {
        return true;
    }

}
