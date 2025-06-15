package Zd1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public Server() throws IOException{
        ServerSocket serverSocket = new ServerSocket(2025);
        System.out.println("Slušam na portu 2025...");

        Socket socket = serverSocket.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

        String message = "Dobrodošao na server!";
        out.println(message);

        while(true){
            message = in.readLine();

            if(message.equalsIgnoreCase("exit")){
                System.out.println("Klijent se diskonektovao!");
                break;
            }

            System.out.println("Klijent je napisao: " + message);
            out.println(message.toUpperCase());
        }
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}
