package br.ufal.ic.p2.jackut;

public class Restaurante extends Empresa {
    private String tipoCozinha;

    public Restaurante(String id, String nome, String donoID, String endereco, String tipoEmpresa, String tipoCozinha) {
        super(id, nome, donoID, endereco, tipoEmpresa);
        this.tipoCozinha = tipoCozinha;
    }

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }

}
