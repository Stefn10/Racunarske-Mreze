package xox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public Client() throws UnknownHostException, IOException {

        Socket socket = new Socket("localhost", 2025);
        String message;

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        // Pozdrav poruka sa brojem igraca
        message = in.readLine();
        System.out.println(message);

        int playerNumber = Integer.parseInt(message.split("-")[1]);
        System.out.println(playerNumber);
        boolean gameStarted = false;
        int potez = playerNumber % 2;

        // Priprema za igru
        while (true) {
            System.out.println("Unesite poruku: ");
            message = keyboard.readLine();
            out.println(message);

            message = in.readLine();
            System.out.println(message);

            if (message.contains("Igra pocinje")) {
                gameStarted = true;
                break;
            }
        }

        // Igra
        while (gameStarted) {
            // Neparni potezi kada si na potezu, parni kada je protivnik na potezu
            if (potez % 2 == 0) { // Ja sam na potezu
                System.out.println("Unesite potez: ");
                message = keyboard.readLine();
                while (!message.matches("\\d,\\d")) { // Sve dok potez nije u dobrom formatu
                    System.out.println("Unesite potez u formatu x,y:  ");
                    message = keyboard.readLine();
                }
                out.println(message);

            } else { // Sacekaj, protivnik je na potezu
                message = in.readLine();
                if (message.contains("pobedio")) { // Proveri da li je protivnik pobedio
                    System.out.println(message);
                    break;
                }
                printTable(message);
            }

            message = in.readLine(); // Odgovor servera na potez

            if (message.contains("TABLA:")) {
                String table = message.split(":")[1];
                printTable(table);
            } else {
                System.out.println("Server: " + message);
            }

            if (message.contains("pobedili")) { // Kada pobedis
                System.out.println("Kraj igre!");
                break;
            } else if (!message.contains("Neispravan")) { // Predji na sledeci potez samo ako je odigran ispravan potez
                potez++;
            }
        }

        socket.close();
        keyboard.close();

    }

    public static void main(String[] args) throws IOException {
        new Client();

    }

    private void printTable(String table) {
        System.out.println("Tabla: ");
        String[] tableRows = table.split(",");
        for (String row : tableRows) {
            System.out.println(row);
        }

    }

}