package xox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable {

    private Server server;
    private Socket socket;
    private int clientNumber;

    public ServerThread(Server server, Socket socket, int clientNumber) {
        this.server = server;
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            String message;
            out.println("Dobrodosli na Server klijente-" + clientNumber);

            // Cekaj start poruku pa proveri da li se konektovao protivnik
            while (true) {
                message = in.readLine();
                if (!"start".equals(message)) {
                    out.println("Neispravan unos poruke.");
                } else {
                    if (server.hasOpponent(clientNumber)) {
                        break;
                    } else {
                        out.println("Ceka se protivnik...");
                    }
                }
            }

            out.println("Igra pocinje!");
            while (true) {
                message = in.readLine();

                if (message == null) {
                    break;
                }

                if (!message.matches("\\d,\\d")) {
                    out.println("Neispravan unos poruke.");
                    continue;
                }

                int row = Integer.parseInt(message.split(",")[0]);
                int col = Integer.parseInt(message.split(",")[1]);
                if (server.makeMove(clientNumber, row, col)) {
                    if (checkWinner()) {
                        out.println("Vi ste pobedili!");
                        server.messageOpponent("Protivnik je pobedio", clientNumber);
                        break;
                    } else if (isBoardFull()) {
                        out.println("Nereseno!");
                        server.messageOpponent("Nereseno!", clientNumber);
                        break;
                    } else {

                        System.out.println("POTEZ: " + message);
                        out.println("TABLA:" + server.getBoardString());
                        server.messageOpponent(server.getBoardString(), clientNumber);
                        server.messageOpponent("Vi ste na potezu", clientNumber);
                    }

                } else {
                    out.println("Neispravan potez.");
                    continue;
                }

            }
            server.decrementConnectedClients();
            socket.close();

        } catch (IOException e) {
            server.decrementConnectedClients();
            e.printStackTrace();
        }

    }

    private boolean checkWinner() {
        char[][] board = server.getBoard();
        char symbol = (clientNumber %2 == 0) ? 'X' : 'O';

        //Proveri redove, kolone i dijagonale
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                    (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)) {
                return true;
            }
        }
        return (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
                (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol);
    }

    private boolean isBoardFull() {
        char[][] board = server.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ')
                    return false;
            }
        }
        return true;
    }

}