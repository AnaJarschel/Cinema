package br.com.cinema.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Sessao {

    private int id;
    private Filme filme;
    private LocalDate dataSessao;
    private LocalTime horario;
    private String sala;
    private int assentosDisponiveis;

    public Sessao(int id,
                  Filme filme,
                  LocalDate dataSessao,
                  LocalTime horario,
                  String sala,
                  int assentosDisponiveis) {

        this.id = id;
        this.filme = filme;
        this.dataSessao = dataSessao;
        this.horario = horario;
        this.sala = sala;
        this.assentosDisponiveis = assentosDisponiveis;
    }

    public boolean possuiAssentos(int qtd){
        return assentosDisponiveis >= qtd;
    }

    public void diminuirAssentos(int qtd){
        assentosDisponiveis -= qtd;
    }

    public void devolverAssentos(int qtd){
        assentosDisponiveis += qtd;
    }

    public int getAssentosDisponiveis() {
        return assentosDisponiveis;
    }

    public Filme getFilme() {
        return filme;
    }

    public int getId() {
        return id;
    }

    public LocalDate getData() {
        return dataSessao;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public String getSala() {
        return sala;
    }
}
