package br.com.cinema.service;

import br.com.cinema.dao.FilmeDAO;
import br.com.cinema.model.Filme;

public class GerenciarFilmes {

    private FilmeDAO dao =
            new FilmeDAO();

    public void cadastrarFilme(Filme filme){

        dao.cadastrarFilme(filme);

    }
}