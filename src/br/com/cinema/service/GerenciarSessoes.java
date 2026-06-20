package br.com.cinema.service;

import br.com.cinema.dao.SessaoDAO;
import br.com.cinema.model.Sessao;

public class GerenciarSessoes {

    private SessaoDAO dao = new SessaoDAO();

    public void cadastrarSessao(Sessao sessao) {
        dao.cadastrarSessao(sessao);
    }
}