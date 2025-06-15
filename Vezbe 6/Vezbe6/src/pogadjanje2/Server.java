package pogadjanje2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;

public class Server {

    static String [] reci3 = {
            "vuk",
            "pas",
            "rak"
    };
    static String [] reci5 = {
            "tigar",
            "macka",
            "ptica"
    };
    static String [] reci6 = {
            "zirafa",
            "hijena",
            "kamila"
    };

    static int clientNumber = 0;
    static BufferedReader kin;
    static ServerSocket serverSocket;

    public Server() throws IOException {

        kin = new BufferedReader(new InputStreamReader(System.in));
        serverSocket = new ServerSocket(2025);

        while(!serverSocket.isClosed()){
            try {
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket, ++clientNumber);
                Thread thread = new Thread(serverThread);
                thread.start();
            } catch (SocketException se){
                System.out.println("Ciao Serveru...");
            }
        }
    }

    static void checkClients() throws IOException {
        if(--clientNumber == 0){
            System.out.println("Da li zelis da se ugasis?");
            String message = kin.readLine();
            if(message.contains("da"))
                serverSocket.close();
        }
    }

    static String dajRec(int brojSlova){
        int random = new Random().nextInt(3);
        String rec = switch (brojSlova) {
            case 3 -> reci3[random];
            case 5 -> reci5[random];
            case 6 -> reci6[random];
            default -> "";
        };
        return rec;
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}
