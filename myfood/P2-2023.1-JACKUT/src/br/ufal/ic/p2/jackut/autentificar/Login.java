package br.ufal.ic.p2.jackut.autentificar;

import br.ufal.ic.p2.jackut.controle.ControleDados;
import br.ufal.ic.p2.jackut.excecoes.LoginException;
import br.ufal.ic.p2.jackut.usuario.Usuario;

public class Login {
    private ControleDados controleDados;

    public Login(ControleDados controleDados) {
        this.controleDados = controleDados;
    }

    public String login(String email, String senha) throws LoginException {

        for (Usuario usuario : controleDados.getUsuarios().values()) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                return usuario.getId();
            }

        }
        throw new LoginException();

    }

}
