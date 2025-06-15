package pogadjanje;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable{

    Socket socket;
    int clientNumber;
    BufferedReader in;
    PrintWriter out;

    public ServerThread(Socket socket, int clientNumber){
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            boolean alive = true;
            while(alive) {
                String message = in.readLine(); // sifrovana poruka  TODO 1
                System.out.println("Sifrovana: " + message);

                String desifrovana = desifrujRec(message);
                int randomLista = -1;
                switch (desifrovana){
                    case "Zelim da igram" : randomLista = 0; break;
                    case "Igrao bih" : randomLista = 1; break;
                    case "Daj mi rec" : randomLista = 2; break;
                    default:
                        System.out.println("Nije se nista naslo");
                }
                String recIzListe = Server.dajRec(randomLista);
                StringBuilder sb = new StringBuilder();
                sb.append("_".repeat(recIzListe.length()));
                    out.println("Ovo je vasa rec: " + sb + ". Pocnite."); // TODO 2
                System.out.println("Zamislio sam rec: " + recIzListe);
                if(pogodioRec(recIzListe, sb)){
                    out.println("Da li zelite jos da igrate?"); // TODO 5
                    String odgovor = in.readLine();             //TODO 6
                    if(odgovor.toLowerCase().contains("ne")){
                        alive = false;
                        break;
                    }
                }
                else {
                    out.println("Nazalost nemate vise pokusaja."); // TODO 5
                    alive = false;
                    break;
                }
            }

            Server.checkClients();
            socket.close();

        } catch (IOException e){
            System.out.println("Greskaaa...");
        }
    }

    boolean pogodioRec(String rec, StringBuilder crtice) throws IOException {
        for(int i = 0; i < rec.length() * 2; i++){
            boolean pogodio = false;
            String odgovor = in.readLine(); // TODO 3
            char ch = odgovor.charAt(0);
            for(int j = 0; j < rec.length(); j++){
                if(rec.charAt(j) == ch){
                    pogodio = true;
                    crtice.setCharAt(j, ch);
                }
            }
            if(rec.contentEquals(crtice)) {
                out.println("Cestitam pogodio si rec."); // TODO 4
                return true;
            }
            if(pogodio)
                out.println("Bravo, pogodio si slovo: " + ch + " , " + crtice + " [Probaj ponovo]"); // TODO 4
            else
                out.println("Promasio si slovo. Probaj ponovo.");      // TODO 4
        }

        return false;
    }

    String desifrujRec(String poruka){
        String uporediMe;
        for(int i = 1; i < 6; i++){
            if(i < 3){
                uporediMe = "Zelim da igram";
            } else if(i == 3) {
                uporediMe = "Igrao bih";
            }
            else {
                uporediMe = "Daj mi rec";
            }
            if(i <= 2) {
                StringBuilder sb = new StringBuilder(poruka);
                for (int k = 0; k < poruka.length(); k++) {
                    char ch = poruka.charAt(k);
                    sb.setCharAt(k, (char) (ch - i - 2));
                }
                if (sb.toString().equals(uporediMe))
                    return sb.toString();
            } else if(i == 3) {
                StringBuilder sb = new StringBuilder(poruka);
                for (int k = 0; k < poruka.length(); k++) {
                    char ch = poruka.charAt(k);
                    sb.setCharAt(k, (char) (ch - i + 2));
                }
                if (sb.toString().equals(uporediMe))
                    return sb.toString();
            } else {
                StringBuilder sb = new StringBuilder(poruka);
                for (int k = 0; k < poruka.length(); k++) {
                    char ch = poruka.charAt(k);
                    sb.setCharAt(k, (char) (ch - i - i));
                }
                if (sb.toString().equals(uporediMe))
                    return sb.toString();
                }
            }
        return null;
    }
}