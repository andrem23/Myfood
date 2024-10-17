package br.ufal.ic.p2.jackut.usuario;

import java.util.ArrayList;
import java.util.List;

public class Entregador extends Usuario {
    private String endereco;
    private String veiculo;
    private String placa;
    private List<String> empresasCadastradas;

    public Entregador(String id, String nome, String email, String senha, String endereco, String veiculo,
            String placa) {
        super(id, nome, email, senha);
        this.endereco = endereco;
        this.veiculo = veiculo;
        this.placa = placa;
        this.empresasCadastradas = new ArrayList<>(); // Inicializa a lista aqui

    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public List<String> getEmpresasCadastradas() {
        return empresasCadastradas; // Retorna a lista de empresas cadastradas
    }

    public void setEmpresasCadastradas(List<String> empresasCadastradas) {
        this.empresasCadastradas = empresasCadastradas;
    }

    public void adicionarEmpresa(String empresaID) {
        if (!empresasCadastradas.contains(empresaID)) {
            empresasCadastradas.add(empresaID);
        }
    }

    public String getEmpresaID() {
        if (empresasCadastradas.isEmpty()) {
            return null;
        }
        return empresasCadastradas.get(0);
    }

    @Override
    public boolean usuarioEntregador() {
        return true;
    }
}
