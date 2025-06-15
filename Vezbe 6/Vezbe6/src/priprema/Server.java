package priprema;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Random;

public class Server {

    private static int clientNumber = 0;
    private static Socket socket;
    private static String [] lista1 = {"vuk", "pas", "rak"};
    private static String [] lista2 = {"tigar", "macka", "ptica"};
    private static String [] lista3 = {"hijena", "zirafa", "kamila"};
    private static PrintWriter out;
    private static BufferedReader in;

    public Server() throws IOException {

        ServerSocket serverSocket = new ServerSocket(2025);
        System.out.println("Server slusa...");
        while(!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket, ++clientNumber);
                Thread thread = new Thread(serverThread);
                thread.start();
            } catch (SocketException se){
                System.out.println("jebigaa");
            }
        }
    }

    static String getWord(int broj){
        int random = new Random().nextInt(3);
        return switch (broj) {
            case 3 -> lista1[random];
            case 5 -> lista2[random];
            case 6 -> lista3[random];
            default -> "null";
        };
    }

    static void checkClients() throws IOException {
        if(--clientNumber == 0)
            socket.close();
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}
