package server;

import java.io.*;
import java.net.*;

import model.Baralho;
import model.Carta;
import model.Dupla;
import model.Jogador;
import rules.Regra;

public class Jogo implements Runnable {

    private Socket jogador1;
    private Socket jogador2;

    private BufferedReader in1;
    private BufferedReader in2;

    private PrintWriter out1;
    private PrintWriter out2;

    private Jogador j1;
    private Jogador j2;

    private Dupla dupla1;
    private Dupla dupla2;

    public Jogo(Socket jogador1, Socket jogador2) {
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
    }

    @Override
    public void run() {
        try {
            inicializarJogadores();

            while (!jogoTerminou()) {

                Baralho baralho = new Baralho();

                distribuirCartas(baralho);
                mostrarCartas();

                jogarRodada();

                mostrarPlacar();
            }

            finalizaJogo();

          //proteção caso estourar erro
        } catch (Exception e) {
            System.out.println("Erro na comunicação com jogadores " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    // organizei em metodos para ficar mais organizado
    private void inicializarJogadores() throws IOException {
    	
    	//le o que o jogador escreveu 
        in1 = new BufferedReader(new InputStreamReader(jogador1.getInputStream()));
        //manda mensagem do servidor para o jogador 
        out1 = new PrintWriter(jogador1.getOutputStream(), true);

        in2 = new BufferedReader(new InputStreamReader(jogador2.getInputStream()));
        out2 = new PrintWriter(jogador2.getOutputStream(), true);

        j1 = new Jogador();
        j2 = new Jogador();

      //ainda ta implementado para dois jogadores só para teste
        dupla1 = new Dupla();
        dupla1.addDupla(j1);

        dupla2 = new Dupla();
        dupla2.addDupla(j2);

        out1.println("Você é o jogador 1");
        out2.println("Você é o jogador 2");
    }

  // por enquanto deixei assim para testar no console ai vc ve certinho o que é cada regra e tals
    private void distribuirCartas(Baralho baralho) {
        j1.setMao(baralho.entregarCartas());
        j2.setMao(baralho.entregarCartas());
    }

    private void mostrarCartas() {
        mostrarMao(out1, j1);
        mostrarMao(out2, j2);
    }

    private void mostrarMao(PrintWriter out, Jogador j) {
        out.println("Suas cartas:");
        for (int i = 0; i < j.getMao().size(); i++) {
            out.println(i + " - " + j.getMao().get(i));
        }
    }

    private void jogarRodada() throws IOException {

        int vitoriasJ1 = 0;
        int vitoriasJ2 = 0;

        for (int mao = 1; mao <= 3; mao++) {

            int resultado = Tempo (mao);

            if (resultado == 1) vitoriasJ1++;
            if (resultado == 2) vitoriasJ2++;

            if (vitoriasJ1 == 2 || vitoriasJ2 == 2) break;
        }

        if (vitoriasJ1 > vitoriasJ2) {
            dupla1.addPontuacao(1);
            out1.println("Você venceu a rodada!");
            out2.println("Você perdeu a rodada!");

        } else if (vitoriasJ2 > vitoriasJ1) {
            dupla2.addPontuacao(1);
            out2.println("Você venceu a rodada!");
            out1.println("Você perdeu a rodada!");

        } else {
            out1.println("empatou!");
            out2.println("empatou! ");
        }
    }

     private int Tempo(int numeroMao) throws IOException {

        out1.println("Mão " + numeroMao);
        out2.println("Mão " + numeroMao);

        int indice1 = leJogada(in1, out1, j1);
        var carta1 = j1.jogarCarta(indice1);

        int indice2 = leJogada(in2, out2, j2);
        var carta2 = j2.jogarCarta(indice2);

        enviarJogadas(carta1, carta2);

        return resolveTempo(carta1, carta2);
    }

    private int leJogada(BufferedReader in, PrintWriter out, Jogador j) throws IOException {
        int indice;

        while (true) {
            try {
                out.println("Sua vez, digite o número da carta: ");
                indice = Integer.parseInt(in.readLine());

                if (indice >= 0 && indice < j.getMao().size()) {
                    return indice;
                } else {
                    out.println("Escolha inválida!");
                }

            } catch (Exception e) {
                out.println("Digite um número válido!");
            }
        }
    }

    private void enviarJogadas(Carta carta1, Carta carta2) {
        out1.println("Você jogou: " + carta1);
        out1.println("Jogador 2 jogou: " + carta2);

        out2.println("Você jogou: " + carta2);
        out2.println("Jogador 1 jogou: " + carta1);
    }

    private int resolveTempo(Carta carta1, Carta carta2) {

        int resultado = Regra.compararCartas(carta1, carta2); // vai vir da classe regras

        if (resultado == 1) {
            out1.println("Você venceu a mão!");
            out2.println("Você perdeu a mão!");
        } else if (resultado == 2) {
            out2.println("Você venceu a mão!");
            out1.println("Você perdeu a mão!");
        } else {
            out1.println("Empate!");
            out2.println("Empate!"); // por enquanto se empatar nao muda nada ksks vou arrumar caso empate para adicionar mais uma mão 
        }

        return resultado;
    }
    
    private void mostrarPlacar() {
        out1.println("Placar: " + dupla1.getPontuacao() + " x " + dupla2.getPontuacao());
        out2.println("Placar: " + dupla1.getPontuacao() + " x " + dupla2.getPontuacao());
    }

    private boolean jogoTerminou() {
        return dupla1.getPontuacao() >= 12 || dupla2.getPontuacao() >= 12;
    }

    private void finalizaJogo() {
        if (dupla1.getPontuacao() >= 12) {
            out1.println("Você venceu o jogo!");
            out2.println("Você perdeu o jogo!");
        } else {
            out2.println("Você venceu o jogo!");
            out1.println("Você perdeu o jogo!");
        }
    }
}