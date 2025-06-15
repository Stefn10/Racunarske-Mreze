package gasenje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

    static boolean alive = true;
    static int clientNumber = 0;
    static BufferedReader kin;
    static ServerSocket serverSocket;

    public Server() throws IOException {

        serverSocket = new ServerSocket(2025);
        kin = new BufferedReader(new InputStreamReader(System.in));

        while(alive){
            try {
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket, ++clientNumber);
                Thread thread = new Thread(serverThread);
                thread.start();
            } catch (SocketException se){
                System.out.println("Socket vise ne radi.");
                break;
            }
        }
    }

    static void closeServer() throws IOException {
        alive = false;
        serverSocket.close();
    }

    static void checkClients() throws IOException {
        if(--clientNumber == 0) {
            System.out.println("Da li zelis da ostanes ukljucen?");
            String message = kin.readLine();
            if(message.contains("ne"))
                closeServer();
        }
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}
