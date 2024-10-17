package br.ufal.ic.p2.jackut.controle;

import java.util.List;
import java.util.stream.Collectors;

import br.ufal.ic.p2.jackut.autentificar.Autentificar;
import br.ufal.ic.p2.jackut.empresa.Empresa;
import br.ufal.ic.p2.jackut.excecoes.ContaEmailExisteException;
import br.ufal.ic.p2.jackut.excecoes.CpfInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.EmailInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.EmpresaNaoEncontradaException;
import br.ufal.ic.p2.jackut.excecoes.EnderecoInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.EntregadorjaAssociadoEmpresaException;
import br.ufal.ic.p2.jackut.excecoes.FormatoEmailInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.NomeInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.PlacaInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.SenhaInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.excecoes.UsuarioNaoEntregadorException;
import br.ufal.ic.p2.jackut.excecoes.VeiculoInvalidoException;
import br.ufal.ic.p2.jackut.usuario.Cliente;
import br.ufal.ic.p2.jackut.usuario.DonoEstabelecimento;
import br.ufal.ic.p2.jackut.usuario.Entregador;
import br.ufal.ic.p2.jackut.usuario.Usuario;

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

    public String criarUsuario(String nome, String email, String senha, String endereco) throws NomeInvalidoException,
            SenhaInvalidoException, EnderecoInvalidoException, EmailInvalidoException, FormatoEmailInvalidoException,
            ContaEmailExisteException {

        if (nome == null || nome.equals("")) {
            throw new NomeInvalidoException();
        }
        if (email == null) {
            throw new EmailInvalidoException();
        }
        if (senha == null || senha.equals("")) {
            throw new SenhaInvalidoException();
        }
        if (endereco == null || endereco.equals("")) {
            throw new EnderecoInvalidoException();
        }
        if (autentificar.validarEmail(email) != true) {
            throw new FormatoEmailInvalidoException();
        }
        if (autentificar.verificaEmail(email) == true) {
            throw new ContaEmailExisteException();
        }

        String id = gerarNovoId();
        Usuario cliente = new Cliente(id, nome, email, senha, endereco);
        controleDados.setUsuarios(id, cliente);
        return id;
    }

    public String criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws NomeInvalidoException,
            SenhaInvalidoException, EnderecoInvalidoException, EmailInvalidoException, CpfInvalidoException,
            FormatoEmailInvalidoException, ContaEmailExisteException {

        if (nome == null || nome.equals("")) {
            throw new NomeInvalidoException();
        }
        if (email == null) {
            throw new EmailInvalidoException();
        }
        if (senha == null || senha.equals("")) {
            throw new SenhaInvalidoException();
        }
        if (endereco == null || endereco.equals("")) {
            throw new EnderecoInvalidoException();
        }
        if (cpf == null || autentificar.validarCpf(cpf) != true) {
            throw new CpfInvalidoException();
        }
        if (autentificar.validarEmail(email) != true) {
            throw new FormatoEmailInvalidoException();
        }
        if (autentificar.verificaEmail(email) == true) {
            throw new ContaEmailExisteException();
        }

        String id = gerarNovoId();
        Usuario donoEstabelecimento = new DonoEstabelecimento(id, nome, email, senha, cpf, endereco);
        controleDados.setUsuarios(id, donoEstabelecimento);
        return id;
    }

    public String criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa)
            throws NomeInvalidoException, SenhaInvalidoException, EnderecoInvalidoException, EmailInvalidoException,
            ContaEmailExisteException, VeiculoInvalidoException, PlacaInvalidoException {

        if (nome == null || nome.equals("")) {
            throw new NomeInvalidoException();
        }
        if (email == null || !autentificar.validarEmail(email)) {
            throw new EmailInvalidoException();
        }
        if (senha == null || senha.equals("")) {
            throw new SenhaInvalidoException();
        }
        if (endereco == null || endereco.equals("")) {
            throw new EnderecoInvalidoException();
        }
        if (veiculo == null || veiculo.equals("")) {
            throw new VeiculoInvalidoException();

        }
        if (placa == null || placa.equals("")|| autentificar.verificaPlaca(placa)) {
            throw new PlacaInvalidoException();
        }

        if (autentificar.verificaEmail(email)) {
            throw new ContaEmailExisteException();
        }

        String id = gerarNovoId();

        Usuario entregador = new Entregador(id, nome, email, senha, endereco, veiculo, placa);
        controleDados.setUsuarios(id, entregador);

        return id;
    }

    public void cadastrarEntregador(String empresaId, String entregadorId) throws EmpresaNaoEncontradaException,
            UsuarioNaoEntregadorException, EntregadorjaAssociadoEmpresaException {
        Empresa empresa = controleDados.getEmpresas().get(empresaId);
        if (empresa == null) {
            throw new EmpresaNaoEncontradaException();
        }

        Usuario usuario = controleDados.getUsuarios().get(entregadorId);
        if (usuario == null || !(usuario instanceof Entregador)) {
            throw new UsuarioNaoEntregadorException();
        }

        Entregador entregador = (Entregador) usuario;

        if (empresa.getEntregadoresCadastrados().contains(entregadorId)) {
            throw new EntregadorjaAssociadoEmpresaException();
        }

        empresa.adicionarEntregador(entregadorId);
        entregador.adicionarEmpresa(empresaId);
    }

    public String getEntregadores(String empresaId) {
        Empresa empresa = controleDados.getEmpresas().get(empresaId);

        // Obter e-mails dos entregadores cadastrados usando Stream
        List<String> emailsEntregadores = empresa.getEntregadoresCadastrados().stream()
                .map(id -> {
                    Usuario usuario = controleDados.getUsuarios().get(id);
                    return (usuario instanceof Entregador) ? usuario.getEmail() : null;
                })
                .filter(email -> email != null)
                .collect(Collectors.toList());

        // Retornar os e-mails formatados como "{[email1, email2]}"
        return "{[" + String.join(", ", emailsEntregadores) + "]}";
    }

    public String getEmpresas(String entregadorId) throws UsuarioNaoEntregadorException {
        Usuario usuario = controleDados.getUsuarios().get(entregadorId);

        if (usuario == null || !(usuario instanceof Entregador)) {
            throw new UsuarioNaoEntregadorException();
        }

        Entregador entregador = (Entregador) usuario;

        List<String> empresasCadastradas = entregador.getEmpresasCadastradas();

        // Formatar a saída como "{[[NomeEmpresa, Endereco], ...]}"
        String resultado = empresasCadastradas.stream()
                .map(empresaId -> {
                    Empresa empresa = controleDados.getEmpresas().get(empresaId);
                    return "[" + empresa.getNome() + ", " + empresa.getEndereco() + "]";
                })
                .collect(Collectors.joining(", ", "{[", "]}"));

        return resultado;
    }

    public String buscarAtributos(String id, String atributo) throws UsuarioNaoCadastradoException {

        Usuario usuario = controleDados.getUsuarios().get(id);

        if (usuario == null) {
            throw new UsuarioNaoCadastradoException();
        } else if (atributo.equals("nome")) {
            return usuario.getNome();
        } else if (atributo.equals("email")) {
            return usuario.getEmail();
        } else if (atributo.equals("senha")) {
            return usuario.getSenha();
        } else if (atributo.equals("endereco")) {
            if (usuario instanceof Cliente) {
                Cliente cliente = (Cliente) usuario;
                return cliente.getEndereco();
            } else if (usuario instanceof Entregador) {
                Entregador entregador = (Entregador) usuario;
                return entregador.getEndereco();
            }

        } else if (atributo.equals("cpf")) {
            DonoEstabelecimento donoEstabelecimento = (DonoEstabelecimento) usuario;
            return donoEstabelecimento.getCpf();
        } else if (atributo.equals("veiculo")) {
            Entregador entregador = (Entregador) usuario;
            return entregador.getVeiculo();
        } else if (atributo.equals("placa")) {
            Entregador entregador = (Entregador) usuario;
            return entregador.getPlaca();
        }

        return null;
    }

}
