package Zd2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    //Zadatak2: Komunikacija između servera i klijenata. Klijent šalje običnu poruku, a server je vraća velikim slovima
    //Kada neki klijent unese exit - on se gasi, ali server ostaje da radi za druge
    public Server() throws IOException{
        ServerSocket serverSocket = new ServerSocket(2025);
        System.out.println("Slušam na portu 2025...");

        while(true){
            Socket socket = serverSocket.accept();
            ServerThread serverThread = new ServerThread(socket);

            Thread thread = new Thread(serverThread);
            thread.start();
        }
    }
    public static void main(String[] args) throws IOException {
        new Server();
    }
}