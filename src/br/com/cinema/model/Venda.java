package br.com.cinema.model;

import java.time.LocalDateTime;

public class Venda {

    private int id;
    private Sessao sessao;
    private int quantidade;
    private double valorTotal;
    private LocalDateTime dataCompra;

    public Venda(int id,
                 Sessao sessao,
                 int quantidade,
                 double precoIngresso) {

        this.id = id;
        this.sessao = sessao;
        this.quantidade = quantidade;
        this.valorTotal = quantidade * precoIngresso;
        this.dataCompra = LocalDateTime.now();

        sessao.diminuirAssentos(quantidade);
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void cancelarIngresso(){
        sessao.devolverAssentos(quantidade);
    }

    public int getId() {
        return id;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public int getQuantidade() {
        return quantidade;
    }
}