package Zd3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ServerThread implements Runnable{
    private Socket socket;
    private int clientNumber;

    public ServerThread(Socket socket, int clientNumber){
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            int random = new Random().nextInt(20);
            System.out.println("Zamislio sam broj: " + random);

            String message = "Dobrodošao na server klijentu: " + clientNumber;

            while(true){
                out.println(message);
//                if(message.contains("exit"))
//                    break;
                message = in.readLine();
                System.out.println("Klijent#" + clientNumber + ": " + message);

                if(Integer.parseInt(message) < random){
                    message = "Tvoj broj treba da bude veci.";
                } else if(Integer.parseInt(message) > random)
                    message = "Tvoj broj treba da bude manji.";
                else {
                    out.println("Pogodili ste zamisljeni broj.");
                    break;
                }
            }
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}