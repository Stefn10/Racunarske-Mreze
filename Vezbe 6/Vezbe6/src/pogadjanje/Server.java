package pogadjanje;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;


public class Server {

    static String [] lista1 = { "jabuka", "breskva", "mango", "limun", "kajsija" };
    static String [] lista2 = { "krastavac", "paradajz", "kupus", "rotkvica", "batat" };
    static String [] lista3 = { "toto", "milka", "plazma", "kinder", "snikers" };

    static int clientNumber = 0;
    static  ServerSocket serverSocket;
    static BufferedReader in;
    static PrintWriter out;
    static BufferedReader kin;


    public Server() throws IOException {

        serverSocket = new ServerSocket(2026);
        System.out.println("Povezan");

        while(!serverSocket.isClosed()){
            try {
                Socket socket = serverSocket.accept();
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                kin = new BufferedReader(new InputStreamReader(System.in));

                ServerThread serverThread = new ServerThread(socket, ++clientNumber);
                Thread thread = new Thread(serverThread);
                thread.start();
            } catch (SocketException se){
                System.out.println("Ciao...");
            }
        }
    }

    public static void checkClients() throws IOException {
        if(--clientNumber == 0){
            System.out.println("Da li zelis da se diskonektujes?");
            String message = kin.readLine();
            out.println(message);
            if(message.contains("da"))
                serverSocket.close();
        }
    }

    static String dajRec(int broj){
        int random = new Random().nextInt(5);
        try {
            String rec = switch (broj) {
                case 0 -> lista1[random];
                case 1 -> lista2[random];
                case 2 -> lista3[random];
                default -> "";
            };
            return rec;
        } catch (IndexOutOfBoundsException iob){
            System.out.println("Jbgg");
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}
