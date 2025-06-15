package pogadjanje;

import java.io.*;
import java.net.Socket;

public class Client {

    public Client() throws IOException {

        Socket socket = new Socket("localhost", 2026);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));
        boolean alive= true;
        while(alive){
            boolean pogadjam = true;
            int n = (int) (Math.random() * 5) + 1;
            String message = "";
            StringBuilder sb = new StringBuilder();

            switch (n) {
                case 1, 2:
                    message = "Zelim da igram";
                    for (int i = 0; i < message.length(); i++) {
                        char ch = message.charAt(i);
                        sb.append((char) (ch + n + 2));
                    }
                    break;
                case 3:
                    message = "Igrao bih";
                    for (int i = 0; i < message.length(); i++) {
                        char ch = message.charAt(i);
                        sb.append((char) (ch + n - 2));
                    }
                    break;
                case 4, 5:
                    message = "Daj mi rec";
                    for (int i = 0; i < message.length(); i++) {
                        char ch = message.charAt(i);
                        sb.append((char) (ch + n + n));
                    }
                    break;
                default:
                    System.out.println("Nije uspelo ha ha");
            }
            System.out.println("Poruka: " + message);
            System.out.println(sb);
            out.println(sb); // TODO 1
            message = in.readLine(); //TODO 2
            System.out.println(message);
            while (pogadjam) {
                message = kin.readLine();
                out.println(message); // TODO 3

                message = in.readLine(); // TODO 4
                System.out.println("[S]: " + message);
                if(message.contains("Cestitam")) {
                    String upit = in. readLine(); // TODO 5 ponovo?
                    System.out.println("[S]: " + upit);
                    String odogovor = kin.readLine();
                    out.println(odogovor); // TODO 6 da/ne
                    if(odogovor.contains("ne")){
                        alive = false;
                        pogadjam = false;
                        break;
                    } else if(odogovor.contains("da"))
                        break;
                }
                else if(message.contains("Nazalost")){
                    alive = false;
                    pogadjam = false;
                    break;
                }
            }
        }
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }
}