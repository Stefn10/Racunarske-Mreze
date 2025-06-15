package Cezar;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int clientNumber = 0;

    public Server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(2025);
        System.out.println("Slusam na portu 2025");

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
