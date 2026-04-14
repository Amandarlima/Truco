package server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args) throws Exception {

    	//mudar para o ip que ta rodando o Server 
        Socket socket = new Socket("localhost", 12345);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Scanner scanner = new Scanner(System.in);

        while (true) {

            String msg = in.readLine();
            System.out.println(msg);

            if (msg.contains("Sua vez")) {
                String jogada = scanner.nextLine();
                out.println(jogada);
            }
        }
    }
}