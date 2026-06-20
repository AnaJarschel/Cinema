package br.com.cinema.dao;

import br.com.cinema.model.Filme;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FilmeDAO {

    public void cadastrarFilme(Filme filme) {
        String sql = "INSERT INTO filme (id, titulo, genero, classificacao, duracao) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, filme.getId());
            stmt.setString(2, filme.getTitulo());
            stmt.setString(3, filme.getGenero());
            stmt.setString(4, filme.getClassificacao());
            stmt.setInt(5, filme.getDuracao());

            stmt.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Filme> consultarFilmes() {
        List<Filme> filmes = new ArrayList<>();
        String sql = "SELECT * FROM filme";
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Filme filme = new Filme(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("genero"),
                        rs.getString("classificacao"),
                        rs.getInt("duracao")
                );
                filmes.add(filme);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return filmes;
    }

    public void deletarFilme(int id) {
        String sql = "DELETE FROM filme WHERE id = ?";
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            System.out.println("Filme deletado com sucesso!");
        } catch (Exception ex) {
            System.out.println("Erro ao deletar filme (verifique se há sessões vinculadas): " + ex.getMessage());
        }
    }

    public int obterProximoId() {
        int id = 1;
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 AS proximo FROM filme";
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("proximo");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
}