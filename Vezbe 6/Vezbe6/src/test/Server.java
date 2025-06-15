package test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    static int clientNumber = 0;
    static char[][] board = new char[3][3];
    static List<Socket> sockets = new ArrayList<>();
    static ServerSocket serverSocket;

    public Server() throws IOException {

        serverSocket = new ServerSocket(2027);
        initializeBoard();
        while(!serverSocket.isClosed()){
            try {
                Socket socket = serverSocket.accept();
                sockets.add(socket);
                ServerThread serverThread = new ServerThread(socket, ++clientNumber);
                Thread thread = new Thread(serverThread);
                thread.start();
            } catch (SocketException se){
                System.out.println("Caoo");
            }
        }
    }
    
    static void checkClients() throws IOException {
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));
        if(--clientNumber == 0){
            System.out.println("Da li zelis da se ugasi?");
            String message = kin.readLine();
            if(message.contains("da"))
                serverSocket.close();
        }
    }

    private void initializeBoard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    static String getBoardString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(board[i][j]);
            }
            sb.append(",");
        }
        return sb.toString();
    }
//todo NAJBITNIJE
    static void messageOpponents(String message, int client) throws IOException{
        int opponent = (client % 2 == 0) ? client + 1 : client - 1;
        Socket opponentSocket = sockets.get(opponent);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(opponentSocket.getOutputStream()), true);
        out.println(message);
    }

    static boolean hasOpponent(int client){
        int opponent = (client % 2== 0) ? client + 1: client - 1;
        return sockets.size() > opponent;
    }

    static boolean makeMove(int row, int column, int player){
        if(row < 0 || row > 2 || column < 0 || column > 2 || board[row][column] != '-')
            return false;
        board[row][column] = (player % 2 == 0) ? 'X' : 'O';
        return true;
    }

    static char[][] getBoard(){
        return board;
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}
