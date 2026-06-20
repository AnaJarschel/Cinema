package br.com.cinema.service;

import br.com.cinema.model.Sessao;
import br.com.cinema.model.Venda;
import br.com.cinema.dao.VendaDAO;
import br.com.cinema.dao.SessaoDAO;

public class VendaIngressos {

    public Venda comprarIngresso(
            int id,
            Sessao sessao,
            int quantidade,
            double preco) {

        if (!sessao.possuiAssentos(quantidade)) {
            throw new IllegalArgumentException("Assentos insuficientes na sessão.");
        }

        Venda venda = new Venda(
                id,
                sessao,
                quantidade,
                preco
        );

        VendaDAO vendaDao = new VendaDAO();
        vendaDao.comprarIngresso(venda);

        SessaoDAO sessaoDao = new SessaoDAO();
        sessaoDao.atualizarAssentos(sessao);

        return venda;
    }
    public void cancelarCompra(Venda venda) {

        venda.cancelarIngresso();

        VendaDAO vendaDao = new VendaDAO();
        vendaDao.cancelarIngresso(venda);

        SessaoDAO sessaoDao = new SessaoDAO();
        sessaoDao.atualizarAssentos(venda.getSessao());
    }
}