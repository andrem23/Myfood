package br.ufal.ic.p2.jackut;

public class Login {
    private ControleDados controleDados;

    public Login(ControleDados controleDados) {
        this.controleDados = controleDados;
    }

    public String login(String email, String senha) {

        for (Usuario usuario : controleDados.getUsuarios().values()) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                return usuario.getId();
            }

        }
        throw new IllegalArgumentException("Login ou senha invalidos");

    }

}
