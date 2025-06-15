package Zd3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {
    public Server() throws IOException{
        ServerSocket serverSocket = new ServerSocket(2025);
        System.out.println("Slušam na portu 2025...");
        int clientNumber = 0;

        while(true){
            Socket socket = serverSocket.accept();
            ServerThread serverThread = new ServerThread(socket, ++clientNumber);

            Thread thread = new Thread(serverThread);
            thread.start();
        }
    }
    public static void main(String[] args) throws IOException {
        new Server();
    }
}