package Zd4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {

    static boolean winner_exists = false;
    static int random = (int) (Math.random() * 20) + 1;
    static int winner;

    public Server() throws IOException {
        int clientNumber = 0;
        ServerSocket serverSocket = new ServerSocket(2025);
        System.out.println("Imam svoj zamisjleni broj: " + random);
        while(true){
            Socket socket = serverSocket.accept();
            ServerThread serverThread = new ServerThread(socket, ++clientNumber, random);
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