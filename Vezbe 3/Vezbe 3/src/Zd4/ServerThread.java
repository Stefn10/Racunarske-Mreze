package Zd4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ServerThread implements Runnable{

    private Socket socket;
    private int random;
    private int clientNumber;

    public ServerThread(Socket socket, int clientNumber, int random){
        this.socket = socket;
        this.clientNumber = clientNumber;
        this.random = random;
    }

    @Override
    public void run() {
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);

            out.println("Dobrodosao klijentu: " + clientNumber + "! Možeš početi sa pogađanjem!");

            while(true){
                String message = in.readLine();
                if(Server.doesWinnerExist()){
                    out.printf("Klijent %d je pobedio, igra je zavrsena.", Server.getWinner());
                    break;
                }
                else {
                    System.out.printf("Klijent# %d : %s\n", clientNumber, message);
                    if (Integer.parseInt(message) < random) {
                        out.println("Tvoj broj mora da bude veci.");
                    } else if (Integer.parseInt(message) > random) {
                        out.println("Tvoj broj mora da bude manji.");
                    } else {
                        Server.setWinnerExist();
                        Server.setWinner(clientNumber);
                        System.out.println("Klijent sa rednim brojem " + clientNumber + " je pobedio.");
                        out.println("Pogodio si broj, cestitam.");
                        break;
                    }
                }
            }
            socket.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
