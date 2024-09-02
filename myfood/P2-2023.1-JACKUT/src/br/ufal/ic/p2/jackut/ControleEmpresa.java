
package br.ufal.ic.p2.jackut;

public class ControleEmpresa {
    private ControleDados controleDados;
    private Autentificar autentificar;
    private int contadorId;

    public ControleEmpresa(ControleDados controleDados, Autentificar autentificar) {
        this.controleDados = controleDados;
        this.autentificar = autentificar;
        this.contadorId = 1;
    }

    private String gerarNovoId() {
        return String.valueOf(contadorId++);
    }

    public String criarEmpresa(String tipoEmpresa, String donoID, String nome, String endereco, String tipoCozinha) {
        Usuario usuario = controleDados.getUsuarios().get(donoID);
        if (!usuario.usuarioDonoEmpresa()) {
            throw new IllegalArgumentException("Usuario nao pode criar uma empresa");
        }

        else {

            if (nome == null || nome.equals(""))
                throw new IllegalArgumentException("Nome da empresa inválido");
            if (endereco == null || endereco.equals(""))
                throw new IllegalArgumentException("Endereço inválido");
            if (tipoEmpresa == null || tipoEmpresa.equals(""))
                throw new IllegalArgumentException("Tipo de empresa inválido");

            autentificar.verificarNomeEmpresa(nome, endereco, donoID);

            // Gera o ID da empresa
            String id = gerarNovoId();

            Empresa empresa;
            switch (tipoEmpresa) {
                case "restaurante":
                    if (tipoCozinha == null || tipoCozinha.equals("")) {
                        throw new IllegalArgumentException("Tipo de cozinha inválido para Restaurante");
                    }
                    empresa = new Restaurante(id, nome, donoID, endereco, tipoEmpresa, tipoCozinha);
                    break;
                case "farmacia":
                    empresa = new Farmacia(id, nome, donoID, endereco, tipoEmpresa);
                    break;
                case "mercado":
                    empresa = new Mercado(id, nome, donoID, endereco, tipoEmpresa);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de empresa Invalido!");
            }
            controleDados.setEmpresas(id, empresa);

            return id;
        }

    }

    public String buscarEmpresa(String id, String atributo) {
        Empresa empresa = controleDados.getEmpresas().get(id);
        if (empresa == null) {
            throw new IllegalArgumentException("Empresa nao cadastrada");
        } else if (atributo == null) {
            throw new IllegalArgumentException("Atributo invalido");
        } else if (atributo.equals("nome")) {
            return empresa.getNome();
        } else if (atributo.equals("tipoCozinha")) {
            Restaurante restaurante = (Restaurante) empresa;
            return restaurante.getTipoCozinha();
        } else if (atributo.equals("dono")) {
            return controleDados.getUsuarios().get(empresa.getDonoID()).getNome();
        } else if (atributo.equals("endereco")) {
            return empresa.getEndereco();
        }

        throw new IllegalArgumentException("Atributo invalido");
    }

    public String getEmpresasDoUsuario(String donoID) {
        Usuario usuario = controleDados.getUsuarios().get(donoID);
        if (!usuario.usuarioDonoEmpresa()) {
            throw new IllegalArgumentException("Usuario nao pode criar uma empresa");
        }

        StringBuilder empresasDoDono = new StringBuilder();

        for (Empresa empresa : controleDados.getEmpresas().values()) {
            if (empresa.getDonoID().equals(donoID)) {
                if (empresasDoDono.length() > 0) {
                    empresasDoDono.append(", ");
                }
                empresasDoDono.append("[")
                        .append(empresa.getNome())
                        .append(", ")
                        .append(empresa.getEndereco())
                        .append("]");
            }
        }
        if (empresasDoDono.length() >= 0) {
            return "{[" + empresasDoDono.toString() + "]}";
        }
        throw new IllegalArgumentException("Usuario nao pode criar uma empresa");
    }

    public String getIdEmpresa(String donoID, String nome, int indice) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome invalido");
        }
        if (indice < 0) {
            throw new IllegalArgumentException("Indice invalido");
        }

        int cont = 0;
        for (Empresa empresa : controleDados.getEmpresas().values()) {
            if (empresa.getDonoID().equals(donoID) && empresa.getNome().equals(nome)) {
                if (cont == indice) {
                    return empresa.getId();
                }
                cont++;
            }
        }

        if (cont == 0) {
            throw new IllegalArgumentException("Nao existe empresa com esse nome");
        }

        throw new IllegalArgumentException("Indice maior que o esperado");
    }

    // classe Implementada pensando em Auteraçoes futura 
    // public void deletarEmpresa(String id) {
    //     if (controleDados.getEmpresas().remove(id) == null) {
    //         throw new IllegalArgumentException("Empresa não encontrada.");
    //     }

    // }
}
