package Racunanje;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static int clientNumber = 0;
    private static String logIn = "RAF123";

    public Server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(2025);
        System.out.println("Slusam na portu 2025");

        while(true){
            Socket socket = serverSocket.accept();
            ServerThread serverThread = new ServerThread(socket);
            Thread thread = new Thread(serverThread);
            thread.start();
        }
    }

    public static String getLogIn(){
        return logIn;
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}
