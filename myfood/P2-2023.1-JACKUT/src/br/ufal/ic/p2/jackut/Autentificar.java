package br.ufal.ic.p2.jackut;

import java.util.regex.Pattern;

public class Autentificar {
    private ControleDados controleDados;

    public Autentificar(ControleDados controleDados) {
        this.controleDados = controleDados;
    }

    public boolean validarEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regex, email);
    }

    public boolean validarCpf(String cpf) {
        String cpfLimpo = cpf.replaceAll("\\D", "");

        // Verifica se o CPF limpo tem exatamente 11 dígitos
        return cpfLimpo.length() == 11;
    }

    public boolean verificaEmail(String email) {
        for (Usuario usuario : controleDados.getUsuarios().values()) {
            if (usuario.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean verificarNomeEmpresa(String nome, String endereco, String donoID) {
        for (Empresa empresa : controleDados.getEmpresas().values()) {

            if (empresa.getNome().equals(nome)) {

                if (empresa.getDonoID().equals(donoID) && !empresa.getEndereco().equals(endereco))
                    return true;
                else if (empresa.getDonoID().equals(donoID) && empresa.getEndereco().equals(endereco)) {
                    throw new IllegalArgumentException("Proibido cadastrar duas empresas com o mesmo nome e local");
                } else
                    throw new IllegalArgumentException("Empresa com esse nome ja existe");
            }
        }
        return true;
    }
}