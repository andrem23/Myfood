package br.ufal.ic.p2.jackut.empresa;

public class Mercado extends Empresa {
    private String tipoMercado; 
    private String abre; 
    private String fecha;

    public Mercado(String id, String nome, String donoID, String endereco, String tipoEmpresa, String abre, String fecha, String tipoMercado) {
        super(id, nome, donoID, endereco, tipoEmpresa);
        this.abre = abre;
        this.fecha = fecha;
        this.tipoMercado = tipoMercado;
    }

    public String getTipoMercado() {
        return tipoMercado;
    }


    public void setTipoMercado(String tipoMercado) {
        this.tipoMercado = tipoMercado;
    }
    
    public String getAbre() {
        return abre;
    }

    public void setAbre(String abre) {
        this.abre = abre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    } 

}
