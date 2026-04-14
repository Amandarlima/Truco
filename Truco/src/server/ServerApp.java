package server;

import java.io.*;
import java.net.*;

public class ServerApp {

	public static void main(String[] args) throws IOException {

		ServerSocket server = new ServerSocket(12345);
		System.out.println("Servidor iniciado...");

		Socket jogador1 = server.accept();
		System.out.println("Jogador 1 conectado");

		Socket jogador2 = server.accept();
		System.out.println("Jogador 2 conectado");

		new Thread(new Jogo(jogador1, jogador2)).start();
	}
}