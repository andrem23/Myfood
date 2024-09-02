package br.ufal.ic.p2.jackut;

public class ControleUsuarios {
    private ControleDados controleDados;
    private Autentificar autentificar;
    private int contadorId;

    public ControleUsuarios(ControleDados controleDados, Autentificar autentificar) {
        this.controleDados = controleDados;
        this.autentificar = autentificar;
        this.contadorId = 1;
    }

    private String gerarNovoId() {
        return String.valueOf(contadorId++);
    }

    public String criarUsuario(String nome, String email, String senha, String endereco) {

        if (nome == null || nome.equals("")) {
            throw new IllegalArgumentException("Nome invalido");
        }
        if (email == null) {
            throw new IllegalArgumentException("Email invalido");
        }
        if (senha == null || senha.equals("")) {
            throw new IllegalArgumentException("Senha invalido");
        }
        if (endereco == null || endereco.equals("")) {
            throw new IllegalArgumentException("Endereco invalido");
        }
        if (autentificar.validarEmail(email) != true) {
            throw new IllegalArgumentException("Formato de email invalido");
        }
        if (autentificar.verificaEmail(email) == true) {
            throw new IllegalArgumentException("Conta com esse email ja existe");
        }

        String id = gerarNovoId();
        Usuario cliente = new Cliente(id, nome, email, senha, endereco);
        controleDados.setUsuarios(id, cliente);
        return id;
    }

    public String criarUsuario(String nome, String email, String senha, String endereco, String cpf) {
        if (nome == null || nome.equals("")) {
            throw new IllegalArgumentException("Nome invalido");
        }
        if (email == null) {
            throw new IllegalArgumentException("Email invalido");
        }
        if (senha == null || senha.equals("")) {
            throw new IllegalArgumentException("Senha invalido");
        }
        if (endereco == null || endereco.equals("")) {
            throw new IllegalArgumentException("Endereco invalido");
        }
        if (cpf == null || autentificar.validarCpf(cpf) != true) {
            throw new IllegalArgumentException("CPF invalido");
        }
        if (autentificar.validarEmail(email) != true) {
            throw new IllegalArgumentException("Formato de email invalido");
        }
        if (autentificar.verificaEmail(email) == true) {
            throw new IllegalArgumentException("Conta com esse email ja existe");
        }

        String id = gerarNovoId();
        Usuario donoEstabelecimento = new DonoEstabelecimento(id, nome, email, senha, cpf, endereco);
        controleDados.setUsuarios(id, donoEstabelecimento);
        return id;
    }

    public String buscarAtributos(String id, String atributo) {

        Usuario usuario = controleDados.getUsuarios().get(id);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao cadastrado.");
        } else if (atributo.equals("nome")) {
            return usuario.getNome();
        } else if (atributo.equals("email")) {
            return usuario.getEmail();
        } else if (atributo.equals("senha")) {
            return usuario.getSenha();
        } else if (atributo.equals("endereco")) {
            Cliente cliente = (Cliente) usuario;
            return cliente.getEndereco();

        } else if (atributo.equals("cpf")) {
            DonoEstabelecimento donoEstabelecimento = (DonoEstabelecimento) usuario;
            return donoEstabelecimento.getCpf();
        }

        return null;
    }

}
