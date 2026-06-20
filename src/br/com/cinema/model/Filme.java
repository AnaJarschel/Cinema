package br.com.cinema.model;

public class Filme {

    private int id;
    private String titulo;
    private String genero;
    private String classificacao;
    private int duracao;

    public Filme(int id, String titulo,
                 String genero,
                 String classificacao,
                 int duracao) {

        if(titulo == null || titulo.isEmpty())
            throw new IllegalArgumentException("Título obrigatório");

        if(duracao <= 0)
            throw new IllegalArgumentException("Duração inválida");

        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.classificacao = classificacao;
        this.duracao = duracao;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public int getDuracao() {
        return duracao;
    }
}