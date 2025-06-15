package Zd2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable{
    private Socket socket;

    public ServerThread(Socket socket){
        this.socket = socket;
    }

    public ServerThread(Socket socket, int i, int random) {
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            String message = "Dobrodošao na server!";
            out.println(message);

            while(true){
                message = in.readLine();

                if(message.equalsIgnoreCase("exit")){
                    System.out.println("Klijent se diskonektovao");
                    break;
                }

                System.out.println("Klijent je napisao: " + message);
                out.println(message.toUpperCase());
            }
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}