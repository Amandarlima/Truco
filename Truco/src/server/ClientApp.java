package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 12345);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Scanner scanner = new Scanner(System.in);

        String msg;

        while ((msg = in.readLine()) != null) {

            System.out.println(msg);

           
            if (msg.contains("Sua vez")) {

                System.out.print("> ");
                String jogada = scanner.nextLine();

                out.println(jogada);
            }
        }

        System.out.println("Conexão encerrada pelo servidor.");

        scanner.close();
        socket.close();
    }
}