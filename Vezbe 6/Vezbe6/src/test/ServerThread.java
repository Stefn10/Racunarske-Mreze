package test;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable{

    Socket socket;
    int clientNumber;

    public ServerThread(Socket socket, int clientNumber){
        this.socket = socket;
        this.clientNumber = clientNumber;
    }


    @Override
    public void run() {
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            String message;
            out.println("Dobrodosli na Server klijente-" + clientNumber);

            while(true){
                message = in.readLine();
                if(!message.contains("start")){
                    out.println("Neispravan unos poruke.");
                } else {
                    if(Server.hasOpponent(clientNumber))
                        break;
                    else
                        out.println("Ceka se protivnik.");
                }
            }
            out.println("Igra pocinje.");
            while(true) {
                message = in.readLine();
                if(message == null)
                    break;

                if(!message.matches("\\d,\\d")){
                    out.println("Neipsravan unos poruke. Sada igra protivnik.");
                    continue;
                }
                int row = Integer.parseInt(message.split(",")[0]);
                int column = Integer.parseInt(message.split(",")[1]);
                if(Server.makeMove(row, column, clientNumber)){
                    if(checkWinner()){
                        out.println("Vi ste pobedili.");
                        Server.messageOpponents("Protivnik je pobedio", clientNumber);
                        break;
                    }
                    else if(isBoardFull()){
                        out.println("Nereseno.");
                        Server.messageOpponents("Nereseno.", clientNumber);
                        break;
                    }
                    else {
                        System.out.println("Potez: " + message);
                        out.println("TABLA:" + Server.getBoardString());
                        Server.messageOpponents(Server.getBoardString(),clientNumber);
                        Server.messageOpponents("Vi ste na potezu.", clientNumber);
                    }
                }
                else
                    out.println("Neispravan potez.");
            }
            Server.checkClients();
            socket.close();

        } catch (IOException io){
            //
        }

    }

    private boolean checkWinner() {
        char[][] board = Server.getBoard();
        char symbol = (clientNumber % 2 == 0) ? 'X' : 'O';

        for(int i = 0; i < 3; i++){
            if((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol))
                return true;
        }

        return (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
                (board[0][2] == symbol &&  board[1][1] == symbol && board[2][0] == symbol);
    }

    private boolean isBoardFull() {
        char board[][] = Server.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[i][j] == '-')
                    return false;
            }
        }
        return true;
    }
}
