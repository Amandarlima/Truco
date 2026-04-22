package server;

import java.io.*;
import java.net.*;
import java.util.*;

import model.Baralho;
import model.Carta;
import model.Dupla;
import model.Jogador;
import model.Modelo;
import model.Rodada;
import rules.Regra;

public class Jogo implements Runnable {

	private Socket jogador1;
	private Socket jogador2;

	public Jogo(Socket jogador1, Socket jogador2) {
		this.jogador1 = jogador1;
		this.jogador2 = jogador2;
	}

	@Override
	public void run() {
		try {

			// le o que o jogador escreveu
			BufferedReader in1 = new BufferedReader(new InputStreamReader(jogador1.getInputStream()));
			// manda mensagem do servidor para o jogador
			PrintWriter out1 = new PrintWriter(jogador1.getOutputStream(), true);

			BufferedReader in2 = new BufferedReader(new InputStreamReader(jogador2.getInputStream()));
			PrintWriter out2 = new PrintWriter(jogador2.getOutputStream(), true);

			out1.println("Você é o jogador 1");
			out2.println("Você é o jogador 2");

			Jogador j1 = new Jogador();
			j1.setId(1);

			Jogador j2 = new Jogador();
			j2.setId(2);

			Dupla d1 = new Dupla();
			d1.setId(1);
			d1.addDupla(j1);

			Dupla d2 = new Dupla();
			d2.setId(2);
			d2.addDupla(j2);

			j1.setDupla(d1);
			j2.setDupla(d2);

			Regra regra = new Regra();

			while (d1.getPontuacao() < 12 && d2.getPontuacao() < 12) {

				Baralho baralho = new Baralho();
				Rodada rodada = iniciarRodada(baralho, j1, j2);

				Carta vira = rodada.getVira();
				Modelo manilha = regra.calcularManilha(vira);

				out1.println("Vira: " + vira);
				out1.println("Manilha da rodada: " + manilha);

				out2.println("Vira: " + vira);
				out2.println("Manilha da rodada: " + manilha);

				int vencedorRodada = Tempo(in1, out1, in2, out2, j1, j2, regra, rodada);

				if (vencedorRodada == 1) {
					d1.addPontuacao(rodada.getValor());
				} else if (vencedorRodada == 2) {
					d2.addPontuacao(rodada.getValor());
				}

				out1.println("Placar: " + d1.getPontuacao() + " x " + d2.getPontuacao());
				out2.println("Placar: " + d1.getPontuacao() + " x " + d2.getPontuacao());
			}

			if (d1.getPontuacao() >= 12) {
				out1.println("Você venceu o jogo!");
				out2.println("Você perdeu o jogo!");
			} else {
				out2.println("Você venceu o jogo!");
				out1.println("Você perdeu o jogo!");
			}

		} catch (Exception e) {
			System.out.println("Erro na comunicação com jogadores " + e.getMessage());
			e.printStackTrace();
		}
	}

	private Rodada iniciarRodada(Baralho baralho, Jogador j1, Jogador j2) {

		Rodada rodada = new Rodada();
		rodada.setTrucoAtivo(false);

		// distribuir cartas
		j1.setMao(baralho.entregarCartas());
		j2.setMao(baralho.entregarCartas());

		Carta vira = baralho.getBaralho().remove(0);

		rodada.setVira(vira);

		return rodada;
	}

	private int Tempo(BufferedReader in1, PrintWriter out1, BufferedReader in2, PrintWriter out2, Jogador j1,
			Jogador j2, Regra regra, Rodada rodada) throws IOException {

		int dupla1 = 0;
		int dupla2 = 0;

		int jogadorDaVez = 1;

		for (int mao = 1; mao <= 3; mao++) {

			Modelo manilha = regra.calcularManilha(rodada.getVira());

			out1.println("Mão " + mao + " | Valor: " + rodada.getValor());
			out2.println("Mão " + mao + " | Valor: " + rodada.getValor());

			Carta c1 = null;
			Carta c2 = null;

			while (true) {

				if (jogadorDaVez == 1) {

					c1 = jogarTurnoComTruco(in1, out1, out2, j1, 1);

					if (c1 == null) {

						if (!regra.podePedirTruco(rodada)) {
							out1.println("Truco já está no máximo (12)");
							continue;
						}

						if (!rodada.isTrucoAtivo()) {
							regra.pedirTruco(rodada);
							rodada.setTrucoAtivo(true);
						}

						out1.println("TRUCO! Valor: " + rodada.getValor());
						out2.println("Adversário pediu TRUCO! Aceita? (sim/nao)");

						String resposta = in2.readLine();
						if (resposta == null) {
							out1.println("Jogador 2 desconectou.");
							return 1;
						}

						if (!regra.responderTruco(resposta)) {
							return 1;
						}

						continue;
					}

					c1.setId(j1.getDupla().getId());

					if (c1.getModelo() == manilha) {
						out1.println("MANILHA!");
					}

					c2 = jogarTurnoComTruco(in2, out2, out1, j2, 2);

					if (c2 == null) {

						if (!regra.podePedirTruco(rodada)) {
							out2.println("Truco já está no máximo (12)");
							continue;
						}

						if (!rodada.isTrucoAtivo()) {
							regra.pedirTruco(rodada);
							rodada.setTrucoAtivo(true);
						}

						out2.println("TRUCO! Valor: " + rodada.getValor());
						out1.println("Adversário pediu TRUCO! Aceita? (sim/nao)");

						String resposta = in1.readLine();
						if (resposta == null) {
							out2.println("Jogador 1 desconectou.");
							return 2;
						}

						if (!regra.responderTruco(resposta)) {
							return 2;
						}

						continue;
					}

					c2.setId(j2.getDupla().getId());

					if (c2.getModelo() == manilha) {
						out2.println("MANILHA!");
					}

				} else {

					c2 = jogarTurnoComTruco(in2, out2, out1, j2, 2);

					if (c2 == null) {

						if (!regra.podePedirTruco(rodada)) {
							out2.println("Truco já está no máximo (12)");
							continue;
						}

						if (!rodada.isTrucoAtivo()) {
							regra.pedirTruco(rodada);
							rodada.setTrucoAtivo(true);
						}

						out2.println("TRUCO! Valor: " + rodada.getValor());
						out1.println("Adversário pediu TRUCO! Aceita? (sim/nao)");

						String resposta = in1.readLine();
						if (resposta == null) {
							out2.println("Jogador 1 desconectou.");
							return 2;
						}

						if (!regra.responderTruco(resposta)) {
							return 2;
						}

						continue;
					}

					c2.setId(j2.getDupla().getId());

					if (c2.getModelo() == manilha) {
						out2.println("MANILHA!");
					}

					c1 = jogarTurnoComTruco(in1, out1, out2, j1, 1);

					if (c1 == null) {

						if (!regra.podePedirTruco(rodada)) {
							out1.println("Truco já está no máximo (12)");
							continue;
						}

						if (!rodada.isTrucoAtivo()) {
							regra.pedirTruco(rodada);
							rodada.setTrucoAtivo(true);
						}

						out1.println("TRUCO! Valor: " + rodada.getValor());
						out2.println("Adversário pediu TRUCO! Aceita? (sim/nao)");

						String resposta = in2.readLine();
						if (resposta == null) {
							out1.println("Jogador 2 desconectou.");
							return 1;
						}

						if (!regra.responderTruco(resposta)) {
							return 1;
						}

						continue;
					}

					c1.setId(j1.getDupla().getId());

					if (c1.getModelo() == manilha) {
						out1.println("MANILHA!");
					}
				}

				break;
			}

			List<Carta> mesa = Arrays.asList(c1, c2);
			List<Integer> vencedores = regra.verificar_vitoria(mesa, rodada.getVira());

			if (vencedores.size() == 1) {

				int vencedor = vencedores.get(0);

				if (vencedor == 1) {
					dupla1++;
					jogadorDaVez = 1;
				} else {
					dupla2++;
					jogadorDaVez = 2;
				}

			}

			if (dupla1 == 2 || dupla2 == 2)
				break;
		}

		if (dupla1 > dupla2)
			return 1;
		if (dupla2 > dupla1)
			return 2;

		return 0;
	}

	private Carta jogarTurnoComTruco(BufferedReader in, PrintWriter out, PrintWriter outOponente, Jogador jogador,
			int numeroJogador) throws IOException {

		out.println("Suas cartas:");

		for (int i = 0; i < jogador.getMao().size(); i++) {
			out.println(i + " - " + jogador.getMao().get(i));
		}

		out.println("Digite índice OU 'truco':");

		String entrada = in.readLine();

		if (entrada == null) {
			outOponente.println("Jogador " + numeroJogador + " desconectou.");
			throw new IOException("Jogador " + numeroJogador + " desconectou.");
		}

		if (entrada.equalsIgnoreCase("truco")) {
			return null;
		}

		try {
			int indice = Integer.parseInt(entrada);

			if (indice >= 0 && indice < jogador.getMao().size()) {
				return jogador.jogarCarta(indice);
			}

			out.println("Índice inválido!");
		} catch (NumberFormatException e) {
			out.println("Entrada inválida! Digite um número ou 'truco'.");
		}

		return jogarTurnoComTruco(in, out, outOponente, jogador, numeroJogador);
	}
}