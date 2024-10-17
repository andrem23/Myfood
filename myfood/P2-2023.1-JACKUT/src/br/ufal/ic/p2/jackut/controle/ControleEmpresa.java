
package br.ufal.ic.p2.jackut.controle;

import br.ufal.ic.p2.jackut.autentificar.Autentificar;
import br.ufal.ic.p2.jackut.empresa.Empresa;
import br.ufal.ic.p2.jackut.empresa.Farmacia;
import br.ufal.ic.p2.jackut.empresa.Mercado;
import br.ufal.ic.p2.jackut.empresa.Restaurante;
import br.ufal.ic.p2.jackut.excecoes.AtributoInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.EmpresaComEsseNomeJaExisteException;
import br.ufal.ic.p2.jackut.excecoes.EmpresaNaoCadastradaException;
import br.ufal.ic.p2.jackut.excecoes.EmpresaNaoFoiEncontradaException;
import br.ufal.ic.p2.jackut.excecoes.EnderecoEmpresaInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.EnderecoInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.FormatoHoraInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.HorarioInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.IndiceInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.IndiceMaiorQueEsperadoException;
import br.ufal.ic.p2.jackut.excecoes.NaoExisteEmpresaComEsseNomeException;
import br.ufal.ic.p2.jackut.excecoes.NaoMercadoValidoException;
import br.ufal.ic.p2.jackut.excecoes.NomeEmpresaInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.NomeInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.ProibidoCadastrarDuasEmpresasComMesmoNomeLocalException;
import br.ufal.ic.p2.jackut.excecoes.TipoEmpresaInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.TipoMercadoInvalidoException;
import br.ufal.ic.p2.jackut.excecoes.UsuarioNaoPodeCriarUmaEmpresaException;
import br.ufal.ic.p2.jackut.usuario.Usuario;

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

    // metodo para criar uma Restaurante
    public String criarEmpresa(String tipoEmpresa, String donoID, String nome, String endereco, String tipoCozinha)
            throws UsuarioNaoPodeCriarUmaEmpresaException, NomeEmpresaInvalidoException, EnderecoInvalidoException,
            TipoEmpresaInvalidoException, EmpresaComEsseNomeJaExisteException,
            ProibidoCadastrarDuasEmpresasComMesmoNomeLocalException {

        Usuario usuario = controleDados.getUsuarios().get(donoID);
        if (!usuario.usuarioDonoEmpresa()) {
            throw new UsuarioNaoPodeCriarUmaEmpresaException();
        }

        else {

            if (nome == null || nome.equals(""))
                throw new NomeEmpresaInvalidoException();
            if (endereco == null || endereco.equals(""))
                throw new EnderecoInvalidoException();
            if (tipoEmpresa == null || tipoEmpresa.equals(""))
                throw new TipoEmpresaInvalidoException();

            autentificar.verificarNomeEmpresa(nome, endereco, donoID);

            String id = gerarNovoId();
            Empresa empresa = new Restaurante(id, nome, donoID, endereco, "restaurante", tipoCozinha);

            controleDados.setEmpresas(id, empresa);

            return id;
        }

    }

    // Método para criar um mercado
    public String criarEmpresa(String tipoEmpresa, String donoID, String nome, String endereco, String abre,
            String fecha, String tipoMercado) throws UsuarioNaoPodeCriarUmaEmpresaException, NomeInvalidoException,
            TipoEmpresaInvalidoException, EnderecoEmpresaInvalidoException, HorarioInvalidoException,
            TipoMercadoInvalidoException,
            FormatoHoraInvalidoException, EmpresaComEsseNomeJaExisteException,
            ProibidoCadastrarDuasEmpresasComMesmoNomeLocalException {

        Usuario usuario = controleDados.getUsuarios().get(donoID);

        if (!usuario.usuarioDonoEmpresa()) {
            throw new UsuarioNaoPodeCriarUmaEmpresaException();
        }

        if (nome == null || nome.isEmpty()) {
            throw new NomeInvalidoException();
        }

        if (endereco == null || endereco.isEmpty()) {
            throw new EnderecoEmpresaInvalidoException();
        }

        if (tipoEmpresa == null || tipoEmpresa.isEmpty()) {
            throw new TipoEmpresaInvalidoException();
        }

        if (tipoMercado == null || tipoMercado.isEmpty()) {
            throw new TipoMercadoInvalidoException();
        }

        if (abre == null || fecha == null) {
            throw new HorarioInvalidoException();
        }

        if (abre.isEmpty() || fecha.isEmpty() ||
                !autentificar.isHorarioValido(abre) || !autentificar.isHorarioValido(fecha)) {
            throw new FormatoHoraInvalidoException();
        }

        if (!autentificar.verificarHorario(abre, fecha)) {
            throw new HorarioInvalidoException();
        }

        // Verifica se o nome e endereço da empresa já existem
        autentificar.verificarNomeEmpresa(nome, endereco, donoID);

        String id = gerarNovoId();
        Empresa empresa = new Mercado(id, nome, donoID, endereco, "mercado", abre, fecha, tipoMercado);
        controleDados.setEmpresas(id, empresa);

        return id;
    }

    public void alterarFuncionamento(String id, String novoAbre, String novoFecha) throws HorarioInvalidoException,
            FormatoHoraInvalidoException, EmpresaNaoFoiEncontradaException, NaoMercadoValidoException {

        Empresa empresa = controleDados.getEmpresas().get(id);
        if (empresa == null) {
            throw new EmpresaNaoFoiEncontradaException();
        }

        if (!(empresa instanceof Mercado)) {
            throw new NaoMercadoValidoException();
        }

        if (novoAbre == null || novoAbre.isEmpty() || novoFecha == null || novoFecha.isEmpty()) {
            throw new HorarioInvalidoException();
        }

        if (!autentificar.isHorarioValido(novoAbre) || !autentificar.isHorarioValido(novoFecha)) {
            throw new FormatoHoraInvalidoException();
        }

        if (!autentificar.verificarHorario(novoAbre, novoFecha)) {
            throw new HorarioInvalidoException();
        }

        // Atualiza os horários
        Mercado mercado = (Mercado) empresa;
        mercado.setAbre(novoAbre);
        mercado.setFecha(novoFecha);
    }

    // Método para criar uma farmácia
    public String criarEmpresa(String tipoEmpresa, String donoID, String nome, String endereco, boolean aberto24Horas,
            int numeroFuncionarios)
            throws UsuarioNaoPodeCriarUmaEmpresaException, NomeInvalidoException, TipoEmpresaInvalidoException,
            EnderecoEmpresaInvalidoException, EmpresaComEsseNomeJaExisteException,
            ProibidoCadastrarDuasEmpresasComMesmoNomeLocalException {

        Usuario usuario = controleDados.getUsuarios().get(donoID);
        if (!usuario.usuarioDonoEmpresa()) {
            throw new UsuarioNaoPodeCriarUmaEmpresaException();
        }
        if (tipoEmpresa == null || (!tipoEmpresa.equals("farmacia"))) {
            throw new TipoEmpresaInvalidoException();
        }

        if (nome == null || nome.isEmpty())
            throw new NomeInvalidoException();
        if (endereco == null || endereco.isEmpty())
            throw new EnderecoEmpresaInvalidoException();

        autentificar.verificarNomeEmpresa(nome, endereco, donoID);

        String id = gerarNovoId();
        Empresa empresa = new Farmacia(id, nome, donoID, endereco, "farmacia", aberto24Horas, numeroFuncionarios);
        controleDados.setEmpresas(id, empresa);

        return id;
    }

    public String getAtributoEmpresa(String id, String atributo)
            throws AtributoInvalidoException, EmpresaNaoCadastradaException {
        Empresa empresa = controleDados.getEmpresas().get(id);
        if (empresa == null) {
            throw new EmpresaNaoCadastradaException();
        } else if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException();
        } else if (atributo.equals("nome")) {
            return empresa.getNome();
        } else if (atributo.equals("tipoCozinha")) {
            Restaurante restaurante = (Restaurante) empresa;
            return restaurante.getTipoCozinha();
        } else if (atributo.equals("dono")) {
            return controleDados.getUsuarios().get(empresa.getDonoID()).getNome();
        } else if (atributo.equals("endereco")) {
            return empresa.getEndereco();
        } else if (atributo.equals("abre")) {
            if (empresa instanceof Mercado) {
                return ((Mercado) empresa).getAbre();
            }
        } else if (atributo.equals("fecha")) {
            if (empresa instanceof Mercado) {
                return ((Mercado) empresa).getFecha();
            }
        } else if (atributo.equals("tipoMercado")) {
            if (empresa instanceof Mercado) {
                return ((Mercado) empresa).getTipoMercado();
            }
        } else if (atributo.equals("aberto24Horas")) {
            if (empresa instanceof Farmacia) {
                return String.valueOf(autentificar.isAberto24Horas(((Farmacia) empresa).getAberto24Horas()));
            }

        } else if (atributo.equals("numeroFuncionarios")) {
            if (empresa instanceof Farmacia) {
                return String.valueOf(((Farmacia) empresa).getNumeroFuncionarios());
            }
        }

        throw new AtributoInvalidoException();
    }

    public String getEmpresasDoUsuario(String donoID) throws UsuarioNaoPodeCriarUmaEmpresaException {
        Usuario usuario = controleDados.getUsuarios().get(donoID);
        if (!usuario.usuarioDonoEmpresa()) {
            throw new UsuarioNaoPodeCriarUmaEmpresaException();
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
        throw new UsuarioNaoPodeCriarUmaEmpresaException();
    }

    public String getIdEmpresa(String donoID, String nome, int indice) throws NomeInvalidoException,
            IndiceInvalidoException, IndiceMaiorQueEsperadoException, NaoExisteEmpresaComEsseNomeException {

        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeInvalidoException();
        }
        if (indice < 0) {
            throw new IndiceInvalidoException();
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
            throw new NaoExisteEmpresaComEsseNomeException();
        }

        throw new IndiceMaiorQueEsperadoException();
    }

}
