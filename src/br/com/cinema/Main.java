package br.com.cinema;

import br.com.cinema.dao.FilmeDAO;
import br.com.cinema.dao.SessaoDAO;
import br.com.cinema.dao.VendaDAO;
import br.com.cinema.model.Filme;
import br.com.cinema.model.Sessao;
import br.com.cinema.model.Venda;
import br.com.cinema.service.GerenciarFilmes;
import br.com.cinema.service.GerenciarSessoes;
import br.com.cinema.service.VendaIngressos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcaoPrincipal = -1;

        GerenciarFilmes gerenciarFilmes = new GerenciarFilmes();
        GerenciarSessoes gerenciarSessoes = new GerenciarSessoes();
        VendaIngressos vendaIngressos = new VendaIngressos();

        FilmeDAO filmeDao = new FilmeDAO();
        SessaoDAO sessaoDao = new SessaoDAO();
        VendaDAO vendaDao = new VendaDAO();

        while (opcaoPrincipal != 0) {
            System.out.println("\n=== CINE UNIPAR - MENU PRINCIPAL ===");
            System.out.println("1 - Menu de Filmes");
            System.out.println("2 - Menu de Sessões");
            System.out.println("3 - Menu de Vendas");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcaoPrincipal = scanner.nextInt();
            scanner.nextLine();

            switch (opcaoPrincipal) {
                case 1:
                    int opFilme = -1;
                    while (opFilme != 0) {
                        System.out.println("\n--- MENU DE FILMES ---");
                        System.out.println("1 - Cadastrar Filme");
                        System.out.println("2 - Listar Filmes");
                        System.out.println("3 - Deletar Filme");
                        System.out.println("0 - Voltar");
                        System.out.print("Opção: ");
                        opFilme = scanner.nextInt();
                        scanner.nextLine();

                        if (opFilme == 1) {
                            System.out.println("\n--- NOVO FILME ---");
                            System.out.print("Título (ou 0 para cancelar): ");
                            String titulo = scanner.nextLine();

                            if (titulo.equals("0")) {
                                System.out.println("Operação cancelada.");
                                continue;
                            }

                            System.out.print("Gênero: ");
                            String genero = scanner.nextLine();
                            System.out.print("Classificação: ");
                            String classif = scanner.nextLine();
                            System.out.print("Duração (minutos): ");
                            int duracao = scanner.nextInt();

                            int idFilme = filmeDao.obterProximoId();
                            Filme filme = new Filme(idFilme, titulo, genero, classif, duracao);
                            gerenciarFilmes.cadastrarFilme(filme);

                        } else if (opFilme == 2) {
                            System.out.println("\n--- FILMES EM CARTAZ ---");
                            for (Filme f : filmeDao.consultarFilmes()) {
                                System.out.println(f.getId() + " - " + f.getTitulo() + " (" + f.getGenero() + ")");
                            }

                        } else if (opFilme == 3) {
                            System.out.println("\n--- DELETAR FILME ---");
                            for (Filme f : filmeDao.consultarFilmes()) {
                                System.out.println(f.getId() + " - " + f.getTitulo());
                            }
                            System.out.print("Digite o ID do Filme para deletar (ou 0 para cancelar): ");
                            int idDel = scanner.nextInt();
                            scanner.nextLine();

                            if (idDel == 0) {
                                System.out.println("Operação cancelada.");
                                continue;
                            }

                            filmeDao.deletarFilme(idDel);
                        }
                    }
                    break;

                case 2:
                    int opSessao = -1;
                    while (opSessao != 0) {
                        System.out.println("\n--- MENU DE SESSÕES ---");
                        System.out.println("1 - Cadastrar Sessão");
                        System.out.println("2 - Listar Sessões");
                        System.out.println("3 - Deletar Sessão");
                        System.out.println("0 - Voltar");
                        System.out.print("Opção: ");
                        opSessao = scanner.nextInt();
                        scanner.nextLine();

                        if (opSessao == 1) {
                            System.out.println("\n--- NOVA SESSÃO ---");
                            List<Filme> filmes = filmeDao.consultarFilmes();
                            if (filmes.isEmpty()) {
                                System.out.println("Cadastre um filme antes de criar a sessão.");
                            } else {
                                for (Filme f : filmes) {
                                    System.out.println(f.getId() + " - " + f.getTitulo());
                                }
                                System.out.print("Digite o ID do Filme (ou 0 para cancelar): ");
                                int idFilmeSel = scanner.nextInt();
                                scanner.nextLine();

                                if (idFilmeSel == 0) {
                                    System.out.println("Operação cancelada.");
                                    continue;
                                }

                                Filme filmeSel = null;
                                for (Filme f : filmes) {
                                    if (f.getId() == idFilmeSel) {
                                        filmeSel = f;
                                        break;
                                    }
                                }

                                if (filmeSel == null) {
                                    System.out.println("Filme não encontrado!");
                                } else {
                                    try {
                                        System.out.print("Data (AAAA-MM-DD): ");
                                        LocalDate data = LocalDate.parse(scanner.nextLine());
                                        System.out.print("Horário (HH:MM): ");
                                        LocalTime hora = LocalTime.parse(scanner.nextLine());
                                        System.out.print("Sala: ");
                                        String sala = scanner.nextLine();
                                        System.out.print("Assentos disponíveis: ");
                                        int assentos = scanner.nextInt();

                                        int idSessao = sessaoDao.obterProximoId();
                                        Sessao s = new Sessao(idSessao, filmeSel, data, hora, sala, assentos);
                                        gerenciarSessoes.cadastrarSessao(s);
                                    } catch (Exception e) {
                                        System.out.println("Erro ao criar sessão. Verifique o formato da data/hora.");
                                    }
                                }
                            }
                        } else if (opSessao == 2) {
                            System.out.println("\n--- SESSÕES DISPONÍVEIS ---");
                            for (Sessao s : sessaoDao.consultarSessoes()) {
                                System.out.println(s.getId() + " - " + s.getFilme().getTitulo() + " | Sala: " + s.getSala() + " | Assentos: " + s.getAssentosDisponiveis());
                            }
                        } else if (opSessao == 3) {
                            System.out.println("\n--- DELETAR SESSÃO ---");
                            for (Sessao s : sessaoDao.consultarSessoes()) {
                                System.out.println(s.getId() + " - " + s.getFilme().getTitulo() + " | Sala: " + s.getSala());
                            }
                            System.out.print("Digite o ID da Sessão para deletar (ou 0 para cancelar): ");
                            int idDel = scanner.nextInt();
                            scanner.nextLine();

                            if (idDel == 0) {
                                System.out.println("Operação cancelada.");
                                continue;
                            }

                            sessaoDao.deletarSessao(idDel);
                        }
                    }
                    break;

                case 3:
                    int opVenda = -1;
                    while (opVenda != 0) {
                        System.out.println("\n--- MENU DE VENDAS ---");
                        System.out.println("1 - Efetuar Venda");
                        System.out.println("2 - Cancelar Venda");
                        System.out.println("3 - Listar Vendas");
                        System.out.println("0 - Voltar");
                        System.out.print("Opção: ");
                        opVenda = scanner.nextInt();
                        scanner.nextLine();

                        if (opVenda == 1) {
                            System.out.println("\n--- EFETUAR VENDA ---");
                            List<Sessao> sessoes = sessaoDao.consultarSessoes();
                            if (sessoes.isEmpty()) {
                                System.out.println("Nenhuma sessão cadastrada no banco de dados.");
                            } else {
                                for (Sessao s : sessoes) {
                                    System.out.println(s.getId() + " - " + s.getFilme().getTitulo() + " | Sala: " + s.getSala() + " | Assentos: " + s.getAssentosDisponiveis());
                                }
                                System.out.print("Digite o ID da Sessão (ou 0 para cancelar): ");
                                int idSes = scanner.nextInt();
                                scanner.nextLine();

                                if (idSes == 0) {
                                    System.out.println("Operação cancelada.");
                                    continue;
                                }

                                Sessao sessaoEsc = null;
                                for (Sessao s : sessoes) {
                                    if (s.getId() == idSes) {
                                        sessaoEsc = s;
                                        break;
                                    }
                                }

                                if (sessaoEsc == null) {
                                    System.out.println("Sessão não encontrada.");
                                } else {
                                    System.out.print("Quantidade de ingressos desejada: ");
                                    int qtd = scanner.nextInt();
                                    System.out.print("Preço unitário: R$ ");
                                    double preco = scanner.nextDouble();

                                    try {
                                        int idVenda = vendaDao.obterProximoId();
                                        Venda v = vendaIngressos.comprarIngresso(idVenda, sessaoEsc, qtd, preco);
                                        System.out.println("\n>>> Venda concluída com sucesso! <<<");
                                        System.out.println("Total pago: R$ " + v.getValorTotal());
                                    } catch (Exception e) {
                                        System.out.println("\nErro na venda: " + e.getMessage());
                                    }
                                }
                            }
                        } else if (opVenda == 2) {
                            System.out.println("\n--- CANCELAR VENDA ---");
                            List<Venda> vendas = vendaDao.consultarVendas();
                            if (vendas.isEmpty()) {
                                System.out.println("Nenhuma venda registrada.");
                            } else {
                                for (Venda v : vendas) {
                                    System.out.println(v.getId() + " - Sessão: " + v.getSessao().getFilme().getTitulo() + " | Qtd: " + v.getQuantidade() + " | Total: R$ " + v.getValorTotal());
                                }
                                System.out.print("Digite o ID da Venda que deseja cancelar (ou 0 para cancelar): ");
                                int idVen = scanner.nextInt();
                                scanner.nextLine();

                                if (idVen == 0) {
                                    System.out.println("Operação cancelada.");
                                    continue;
                                }

                                Venda vendaCan = null;
                                for (Venda v : vendas) {
                                    if (v.getId() == idVen) {
                                        vendaCan = v;
                                        break;
                                    }
                                }

                                if (vendaCan == null) {
                                    System.out.println("Venda não encontrada.");
                                } else {
                                    vendaIngressos.cancelarCompra(vendaCan);
                                    System.out.println("Venda cancelada e assentos devolvidos à sessão!");
                                }
                            }
                        } else if (opVenda == 3) {
                            System.out.println("\n--- VENDAS REALIZADAS ---");
                            for (Venda v : vendaDao.consultarVendas()) {
                                System.out.println(v.getId() + " - Sessão: " + v.getSessao().getFilme().getTitulo() + " | Qtd: " + v.getQuantidade() + " | Total: R$ " + v.getValorTotal());
                            }
                        }
                    }
                    break;

                case 0:
                    System.out.println("\nEncerrando o sistema... Até logo!");
                    break;

                default:
                    System.out.println("\nOpção inválida! Tente novamente.");
                    break;
            }
        }
        scanner.close();
    }
}