package br.com.cinema.dao;

import br.com.cinema.model.Filme;
import br.com.cinema.model.Sessao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.Time;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SessaoDAO {

    public void cadastrarSessao(Sessao sessao) {
        String sql = "INSERT INTO sessao (id, filme_id, data_sessao, horario, sala, assentos_disponiveis) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, sessao.getId());
            stmt.setInt(2, sessao.getFilme().getId());
            stmt.setDate(3, Date.valueOf(sessao.getData()));
            stmt.setTime(4, Time.valueOf(sessao.getHorario()));
            stmt.setString(5, sessao.getSala());
            stmt.setInt(6, sessao.getAssentosDisponiveis());

            stmt.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void atualizarAssentos(Sessao sessao) {
        String sql = "UPDATE sessao SET assentos_disponiveis = ? WHERE id = ?";
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, sessao.getAssentosDisponiveis());
            stmt.setInt(2, sessao.getId());

            stmt.execute();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deletarSessao(int id) {
        String sql = "DELETE FROM sessao WHERE id = ?";
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
            System.out.println("Sessão deletada com sucesso!");
        } catch (Exception ex) {
            System.out.println("Erro ao deletar sessão (verifique se há vendas vinculadas): " + ex.getMessage());
        }
    }

    public int obterProximoId() {
        int id = 1;
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 AS proximo FROM sessao";
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

    public List<Sessao> consultarSessoes() {
        List<Sessao> sessoes = new ArrayList<>();
        String sql = "SELECT * FROM sessao";
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            FilmeDAO filmeDao = new FilmeDAO();
            List<Filme> filmes = filmeDao.consultarFilmes();

            while (rs.next()) {
                int id = rs.getInt("id");
                int filmeId = rs.getInt("filme_id");

                Filme filmeSessao = null;
                for (Filme f : filmes) {
                    if (f.getId() == filmeId) {
                        filmeSessao = f;
                        break;
                    }
                }

                Sessao sessao = new Sessao(
                        id,
                        filmeSessao,
                        rs.getDate("data_sessao").toLocalDate(),
                        rs.getTime("horario").toLocalTime(),
                        rs.getString("sala"),
                        rs.getInt("assentos_disponiveis")
                );
                sessoes.add(sessao);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return sessoes;
    }
}