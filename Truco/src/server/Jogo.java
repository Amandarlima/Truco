package server;

import java.io.*;
import java.net.*;

import model.Baralho;
import model.Dupla;
import model.Jogador;

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
            
            Jogador jogador_1 = new Jogador();
            Jogador jogador_2 = new Jogador();
            
            Dupla dupla_impar = new Dupla();
            dupla_impar.setId(1);
            dupla_impar.addDupla(jogador_1);
            
            Dupla dupla_par = new Dupla();
            dupla_par.setId(2);
            dupla_par.addDupla(jogador_2);
            
            while (dupla_impar.getPontuacao() < 12 || dupla_par.getPontuacao() < 12) {
            	
            	Baralho baralho = new Baralho();
            	
            	
            	
            	
            	
            	
            	
            }
            //ainda ta implementado para dois jogadores só para teste
            out1.println("Sua vez:");
            String jogada1 = in1.readLine();
            
            switch (jogada1) {
            	
            case "1":
            	
            	jogador_1.jogarCarta();
            	
            	break;
            
            case "2":
            	
            	jogador_1.;
            	
            	break;
            
            
            }

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