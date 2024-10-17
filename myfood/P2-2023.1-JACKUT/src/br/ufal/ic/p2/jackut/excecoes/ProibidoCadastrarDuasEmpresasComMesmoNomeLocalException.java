package br.ufal.ic.p2.jackut.excecoes;

public class ProibidoCadastrarDuasEmpresasComMesmoNomeLocalException extends Exception {
    public ProibidoCadastrarDuasEmpresasComMesmoNomeLocalException () {
        super("Proibido cadastrar duas empresas com o mesmo nome e local");
    }
}