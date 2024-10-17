package br.ufal.ic.p2.jackut.empresa;

public class Farmacia extends Empresa {
    private Boolean aberto24Horas;
    private int numeroFuncionarios;

    public Farmacia(String id, String nome, String donoID, String endereco, String tipoEmpresa, boolean aberto24Horas,
            int numeroFuncionarios) {
        super(id, nome, donoID, endereco, tipoEmpresa);
        this.aberto24Horas = aberto24Horas;
        this.numeroFuncionarios = numeroFuncionarios;
    }

    public Boolean getAberto24Horas() {
        return aberto24Horas;
    }

    public void setAberto24Horas(Boolean aberto24Horas) {
        this.aberto24Horas = aberto24Horas;
    }

    public int getNumeroFuncionarios() {
        return numeroFuncionarios;
    }

    public void setNumeroFuncionarios(int numeroFuncionarios) {
        this.numeroFuncionarios = numeroFuncionarios;
    }

}
