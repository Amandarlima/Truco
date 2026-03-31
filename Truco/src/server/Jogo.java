package server;

import java.io.*;
import java.net.*;

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

        	//le o que o jogador escreveu 
            BufferedReader in1 = new BufferedReader(new InputStreamReader(jogador1.getInputStream()));
            //manda mensagem do servidor para o jogador 
            PrintWriter out1 = new PrintWriter(jogador1.getOutputStream(), true);

            BufferedReader in2 = new BufferedReader(new InputStreamReader(jogador2.getInputStream()));
            PrintWriter out2 = new PrintWriter(jogador2.getOutputStream(), true);

            out1.println("Você é o jogador 1");
            out2.println("Você é o jogador 2");

            //ainda ta implementado para dois jogadores só para teste
            out1.println("Sua vez:");
            String jogada1 = in1.readLine();

            out2.println("Sua vez:");
            String jogada2 = in2.readLine();

            out1.println("Jogador 2 jogou: " + jogada2);
            out2.println("Jogador 1 jogou: " + jogada1);

            //proteção caso estourar erro
        } catch (Exception e) {
        	System.out.println("Erro na comunicação com jogadores " + e.getMessage());
            e.printStackTrace();
        }
    }
}