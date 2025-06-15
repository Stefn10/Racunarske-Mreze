package Zd5_;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Server {

    static boolean winner_exists = false;
    static int random = (int) (Math.random() * 20) + 1;
    static int winner;
    static List<WinnerRecord> topPlayers = new ArrayList<>();

    public synchronized static void addToTopPlayers(int clientNumber, long duration){
        topPlayers.add(new WinnerRecord(clientNumber, duration));
        topPlayers.sort(Comparator.comparingLong(p->p.duration));
        if(topPlayers.size() > 3)
            topPlayers = topPlayers.subList(0, 3);
    }

    public static List<WinnerRecord> getTopPlayers(){
        return topPlayers;
    }

    public Server() throws IOException {
        int clientNumber = 0;
        ServerSocket serverSocket = new ServerSocket(2025);
        System.out.println("Imam svoj zamisjleni broj: " + random);
        while(true){
            Socket socket = serverSocket.accept();
            ServerThread serverThread = new ServerThread(socket, ++clientNumber);
            Thread thread = new Thread(serverThread);
            thread.start();
        }
    }

    public static boolean doesWinnerExist(){
        return winner_exists;
    }
    public static void setWinnerExist(){
        winner_exists = true;
    }
    public static void setWinner(int client){
        winner = client;
    }
    public static int getWinner(){
        return winner;
    }
    public static int getRandom(){
        return random;
    }


    public static void main(String[] args) throws IOException {
        new Server();
    }
}