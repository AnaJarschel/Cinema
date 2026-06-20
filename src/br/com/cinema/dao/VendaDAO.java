package br.com.cinema.dao;

import br.com.cinema.model.Sessao;
import br.com.cinema.model.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    public void comprarIngresso(Venda venda) {
        String sql = "INSERT INTO venda (id, sessao_id, quantidade, valor_total, data_compra) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, venda.getId());
            stmt.setInt(2, venda.getSessao().getId());
            stmt.setInt(3, venda.getQuantidade());
            stmt.setDouble(4, venda.getValorTotal());
            stmt.setTimestamp(5, Timestamp.valueOf(java.time.LocalDateTime.now()));

            stmt.execute();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void cancelarIngresso(Venda venda) {
        String sql = "DELETE FROM venda WHERE id = ?";
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, venda.getId());

            stmt.execute();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int obterProximoId() {
        int id = 1;
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 AS proximo FROM venda";
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

    public List<Venda> consultarVendas() {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT * FROM venda";
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            SessaoDAO sessaoDao = new SessaoDAO();
            List<Sessao> sessoes = sessaoDao.consultarSessoes();

            while (rs.next()) {
                int id = rs.getInt("id");
                int sessaoId = rs.getInt("sessao_id");
                int qtd = rs.getInt("quantidade");
                double valorTotal = rs.getDouble("valor_total");

                Sessao sessaoVenda = null;
                for (Sessao s : sessoes) {
                    if (s.getId() == sessaoId) {
                        sessaoVenda = s;
                        break;
                    }
                }

                if (sessaoVenda != null) {
                    Venda venda = new Venda(id, sessaoVenda, qtd, (valorTotal / qtd));
                    sessaoVenda.devolverAssentos(qtd);
                    vendas.add(venda);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return vendas;
    }
}