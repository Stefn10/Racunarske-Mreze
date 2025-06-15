package priprema;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable {

    private Socket socket;
    ;
    private int clientNumber;
    BufferedReader in;
    PrintWriter out;

    ServerThread(Socket socket, int clientNumber) {
        this.clientNumber = clientNumber;
        this.socket = socket;
    }


    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            out.println("Dobrodosao klijentu# " + clientNumber);
            boolean tacan = true;
            while (tacan) {
                out.println("Pogadjaj broj 3, 5 ili 6");
                String message = in.readLine();
                int random = Integer.parseInt(message);
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for (int i = 0; i < random; i++) {
                    sb.append("_");
                }
                sb.append("]");
                out.println(sb + " Pocnite sa pogadjanjem.");
                if (pogodioRec(random, sb)) {
                    message = in.readLine();
                    if(!message.equals("da")){
                        System.out.println("Pa paaa");
                        tacan = false;
                    }
                }
            }
            Server.checkClients();
            System.out.println("Cao caoo");
            socket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean pogodioRec(int pogadjanje, StringBuilder sb) throws IOException {
        String rec =  Server.getWord(pogadjanje);
        for(int i = 0; i < pogadjanje*2; i++){
            String message = in.readLine();
            char ch = message.charAt(0);
            if(rec.contains(message)){
                for(int j = 0; j < rec.length(); j++){
                    if(ch == rec.charAt(j)){
                        sb.setCharAt(j + 1, ch);
                        if(rec.equals(sb.substring(1, pogadjanje+1))) {
                            out.println("Pogodili ste rec. Da li hocete ponovo da igrate?");
                            return true;
                        }
                    }
                }
                out.println("Pogodili ste slovo: " + ch + " --> " + sb);
            }
            else
                out.println("Niste pogodili slovo.");
        }
        out.println("Niste pogodili rec.");
        return false;
    }

}
