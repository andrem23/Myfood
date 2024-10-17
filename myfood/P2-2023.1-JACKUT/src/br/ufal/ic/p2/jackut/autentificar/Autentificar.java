package br.ufal.ic.p2.jackut.autentificar;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufal.ic.p2.jackut.controle.ControleDados;
import br.ufal.ic.p2.jackut.empresa.Empresa;
import br.ufal.ic.p2.jackut.excecoes.EmpresaComEsseNomeJaExisteException;
import br.ufal.ic.p2.jackut.excecoes.ProibidoCadastrarDuasEmpresasComMesmoNomeLocalException;
import br.ufal.ic.p2.jackut.usuario.Entregador;
import br.ufal.ic.p2.jackut.usuario.Usuario;

public class Autentificar {
    private ControleDados controleDados;

    public Autentificar(ControleDados controleDados) {
        this.controleDados = controleDados;
    }

    public boolean verificaPlaca(String placa) {
        for (Usuario usuario : controleDados.getUsuarios().values()) {
            if (usuario instanceof Entregador) {
                Entregador entregador = (Entregador) usuario;
                if (entregador.getPlaca().equals(placa)) {
                    return true; // Placa já está em uso por outro entregador
                }
            }
        }
        return false; // Placa não está em uso
    }

    public boolean isAberto24Horas(Boolean aberto24Horas) {
        if (aberto24Horas == null || aberto24Horas == false) {
            return false;
        }
        return aberto24Horas;
    }

    public boolean isHorarioValido(String horario) {
        String regex = "^\\d{2}:\\d{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(horario);
        return matcher.matches();
    }

    public boolean verificarHorario(String abre, String fecha) {
        if (!isHorarioValido(abre) || !isHorarioValido(fecha)) {
            return false; // Retorna false se o formato for inválido
        }

        try {
            // Converte as strings de horário para LocalTime
            LocalTime horarioAbre = LocalTime.parse(abre, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime horarioFecha = LocalTime.parse(fecha, DateTimeFormatter.ofPattern("HH:mm"));

            // Compara os horários
            if (horarioAbre.isAfter(horarioFecha)) {
                return false;
            }
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
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

    public boolean verificarNomeEmpresa(String nome, String endereco, String donoID)
            throws EmpresaComEsseNomeJaExisteException, ProibidoCadastrarDuasEmpresasComMesmoNomeLocalException {
        for (Empresa empresa : controleDados.getEmpresas().values()) {

            if (empresa.getNome().equals(nome)) {

                if (empresa.getDonoID().equals(donoID) && !empresa.getEndereco().equals(endereco))
                    return true;
                else if (empresa.getDonoID().equals(donoID) && empresa.getEndereco().equals(endereco)) {
                    throw new ProibidoCadastrarDuasEmpresasComMesmoNomeLocalException();
                } else
                    throw new EmpresaComEsseNomeJaExisteException();
            }
        }
        return true;
    }
}