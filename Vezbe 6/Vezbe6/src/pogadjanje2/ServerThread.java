package pogadjanje2;

import java.io.*;
import java.net.Socket;

public class ServerThread implements  Runnable{

    Socket socket;
    int  clientNumber;
    boolean alive = true;

    public ServerThread(Socket socket, int clientNumber){
        this.clientNumber = clientNumber;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);

            while(alive){
                out.println("Dobrodošli na server! Unesite koju dužinu reči želite da pogađate"); //1
                int duzina = Integer.parseInt(in.readLine());//2
                int brojPogadjanja = duzina * 2;

                StringBuilder crtice = new StringBuilder();
                crtice.append('[');
                for(int i = 0; i< duzina; i++)
                    crtice.append('_');
                crtice.append(']');
                out.println("Vasa rec: " + crtice); //3
                out.println("Pocnite sa nagadjanjem slova."); //4
                String recZaPogadjanje = Server.dajRec(duzina);

                char ch = ' ';
                for (int i = 0; i < brojPogadjanja; i++) {
                    String slovo = in.readLine(); //5
                    if (recZaPogadjanje.contains(slovo)) {
                        for (int j = 0; j < duzina; j++) { // Pravi rec
                            if (recZaPogadjanje.charAt(j) == slovo.charAt(0))
                                crtice.setCharAt(j + 1, slovo.charAt(0));
                        }
                        if (recZaPogadjanje.equals(crtice.substring(1, duzina + 1))) { // Proverava rec
                            out.println("Cestitam pogodili ste rec."); //6
                            break;
                        }
                        out.println("Uspesno pogodjeno slovo: " + slovo.charAt(0) + " " + crtice); //6
                    } else
                        out.println("Niste pogodili slovo. Pokusajte ponovo.");  // 6
                }
                boolean pogodjeno = true;
                for (int i = 1; i < duzina+2; i++) {
                    if (crtice.charAt(i) == '_') {
                        pogodjeno = false;
                        break;
                    }
                }
                if(!pogodjeno) {
                    out.println("Vise srece drugi put...");
                    alive = false;
                    break;
                }
                else {
                    out.println("Da li zelite jos da igrate?"); //7
                    String message = in.readLine(); // 8
                    if (message.contains("ne")) {
                        alive = false;
                        break;
                    }
                }
            }
            System.out.println("Ciao ciao...");
            Server.checkClients();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
