package Zd5_;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable{

    private Socket socket;
    private int random = Server.getRandom();
    private int clientNumber;

    public ServerThread(Socket socket, int clientNumber){
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);

            out.println("Dobrodosao klijentu: " + clientNumber + "! Možeš početi sa pogađanjem!");

            long startTime = System.currentTimeMillis();

            while(true){
                String message = in.readLine();
                if(Server.doesWinnerExist()){
                    out.printf("Klijent %d je pobedio, igra je zavrsena.", Server.getWinner());
                    System.out.println("Top 3 igraca:");
                    for(WinnerRecord r : Server.getTopPlayers())
                        System.out.println(r);
                    break;
                }
                else {
                    System.out.printf("Klijent# %d : %s\n", clientNumber, message);
                    if (Integer.parseInt(message) < random) {
                        out.println("Tvoj broj mora da bude veci.");
                    } else if (Integer.parseInt(message) > random) {
                        out.println("Tvoj broj mora da bude manji.");
                    } else {
                        long duration = System.currentTimeMillis() - startTime;
                        Server.setWinnerExist();
                        Server.setWinner(clientNumber);
                        Server.addToTopPlayers(clientNumber, duration);
                        out.println("Pogodio si broj, cestitam.");
                        System.out.println("Klijent sa rednim brojem " + clientNumber + " je pobedio.");
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
