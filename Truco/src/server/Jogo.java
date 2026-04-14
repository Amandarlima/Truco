package server;

import java.io.*;
import java.net.*;
import java.util.*;

import model.Baralho;
import model.Carta;
import model.Dupla;
import model.Jogador;
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
			Jogador j2 = new Jogador();

			Dupla d1 = new Dupla();
			d1.addDupla(j1);

			Dupla d2 = new Dupla();
			d2.addDupla(j2);

			Regra regra = new Regra();

			while (d1.getPontuacao() < 12 && d2.getPontuacao() < 12) {

				Baralho baralho = new Baralho();

				j1.setMao(baralho.entregarCartas());
				j2.setMao(baralho.entregarCartas());

				int vencedorRodada = Tempo(in1, out1, in2, out2, j1, j2, regra);

				if (vencedorRodada == 1) {
					d1.addPontuacao(1);
				} else if (vencedorRodada == 2) {
					d2.addPontuacao(1);
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

			// proteção caso estourar erro
		} catch (Exception e) {
			System.out.println("Erro na comunicação com jogadores " + e.getMessage());
			e.printStackTrace();
		}
	}

	private int Tempo(BufferedReader in1, PrintWriter out1, BufferedReader in2, PrintWriter out2, Jogador j1,
			Jogador j2, Regra regra) throws IOException {

		int jogador1 = 0;
		int jogador2 = 0;

		for (int mao = 1; mao <= 3; mao++) {

			out1.println("Mão " + mao);
			out2.println("Mão " + mao);

			// jogador 1
			out1.println("Sua vez:");
			int i1 = Integer.parseInt(in1.readLine());
			Carta c1 = j1.jogarCarta(i1);
			c1.setId(1);

			// jogador 2
			out2.println("Sua vez:");
			int i2 = Integer.parseInt(in2.readLine());
			Carta c2 = j2.jogarCarta(i2);
			c2.setId(2);

			out1.println("Você jogou: " + c1);
			out1.println("Jogador 2 jogou: " + c2);

			out2.println("Você jogou: " + c2);
			out2.println("Jogador 1 jogou: " + c1);

			List<Carta> mesa = Arrays.asList(c1, c2);
			List<Integer> vencedores = regra.verificar_vitoria(mesa, null);

			if (vencedores.size() == 1) {

				int vencedor = vencedores.get(0);

				if (vencedor == 1) {
					jogador1++;
					out1.println("Você venceu a mão!");
					out2.println("Você perdeu a mão!");
				} else {
					jogador2++;
					out2.println("Você venceu a mão!");
					out1.println("Você perdeu a mão!");
				}

			} else {
				out1.println("Empate!");
				out2.println("Empate!");
			}

			// alguém fez 2 primeiro
			if (jogador1 == 2 || jogador2 == 2)
				break;
		}

		// vencedor da rodada
		if (jogador1 > jogador2)
			return 1;
		if (jogador2 > jogador1)
			return 2;

		return 0;
	}
}