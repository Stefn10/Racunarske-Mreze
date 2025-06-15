package xox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static int clientCounter;
    private static int connectedClients;
    private char[][] board;
    private List<Socket> sockets;

    public Server() throws IOException {
        ServerSocket server_socket = new ServerSocket(2025);
        server_socket.setSoTimeout(1000);
        System.out.println("Slusam na portu 2025...");
        clientCounter = 0;
        connectedClients = 0;
        int asked_to_close = 0;
        board = new char[3][3];
        initializeBoard();
        sockets = new ArrayList<>();
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));

        while (true) {

            if (clientCounter != asked_to_close && connectedClients == 0) {
                System.out.println("Da li zelite da zatvorite server? (da/ne)");
                boolean close_server = kin.readLine().equals("da");
                asked_to_close = clientCounter;
                if (close_server) {
                    break;
                }
            }

            try {

                Socket socket = server_socket.accept();
                sockets.add(socket);
                ServerThread server_thread = new ServerThread(this, socket, clientCounter);
                Thread thread = new Thread(server_thread);
                thread.start();

                System.out.println("Konektovao se Client " + clientCounter++);
                incrementConnectedClients();

            } catch (SocketTimeoutException e) {
                // Ignorisi
            }

        }
        server_socket.close();
        kin.close();

    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public void incrementConnectedClients() {
        connectedClients++;
    }

    public void decrementConnectedClients() {
        connectedClients--;
    }

    public String getBoardString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != 'X' && board[i][j] != 'O') {
                    sb.append("-");
                } else {
                    sb.append(board[i][j]);
                }
            }
            sb.append(",");
        }
        return sb.toString();
    }

    public List<Socket> getSockets() {
        return sockets;
    }

    public void messageOpponent(String message, int clientNumber) {
        int opponent = (clientNumber % 2 == 0) ? clientNumber + 1 : clientNumber - 1;
        try {
            Socket opponentSocket = sockets.get(opponent);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(opponentSocket.getOutputStream()), true);
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasOpponent(int clientNumber) {
        int opponent = (clientNumber % 2 == 0) ? clientNumber + 1 : clientNumber - 1;
        return sockets.size() > opponent;
    }

    public boolean makeMove(int player, int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != ' ') {
            return false;
        }
        board[row][col] = (player % 2 == 0) ? 'X' : 'O';
        return true;
    }

    public char[][] getBoard() {
        return board;
    }

    public static void main(String[] args) throws IOException {
        new Server();

    }

}