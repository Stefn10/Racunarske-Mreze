import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;

public class ServerThread implements Runnable{

    int clientNumber = 0;
    Socket socket;
    BufferedReader in;
    PrintWriter out;

    public ServerThread(Socket socket, int clientNumber){
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            while(true) {
                out.println("Dobrodosao u igru VokalMaster! Za pocetak igre unesite start");
                String message = in.readLine(); //todo start

                while (!message.contains("start")) { //provera starta
                    out.println("Niste uneli start");
                    message = in.readLine();
                }
                StringBuilder sb = new StringBuilder();
                String generisanNiz = generisiNiz(sb);
                out.println("Igra je pocela imate 6 pokusaja. Dostupni samoglasnici su a,e,i,o,u");

                if (pogodioNiz(generisanNiz)) { // da li je pogodio
                    message = in.readLine(); // cekam odgovor
                    if (message.contains("ne"))
                        break;
                }
                else {
                    out.println("Nemate vise pokusaja, tacan niz je bio "+ sb + " . Da li hocete ponovo da igrate? (da/ne)");
                    if(in.readLine().contains("ne"))
                        break;
                }
            }
            out.println("Hvala na igranju, dovidjenja.");
            Server.checkClients();
            socket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    boolean pogodioNiz(String generisan) throws IOException {
        StringBuilder sb = new StringBuilder();

        for(int pokusaj = 0; pokusaj < 6; pokusaj++) { // 6 puta pokusa
            String poruka = in.readLine();
//            if(!poruka.matches("[aeiou \\s]+")) {
//                out.println("Greska, preostalo pokusaja: " + (6 - 1 -pokusaj) );
//                continue;
//            }
            char prvo = poruka.charAt(0);
            char drugo = poruka.charAt(2);
            char trece = poruka.charAt(4);
            char cetvrto = poruka.charAt(6);
            boolean sadrzi = false;
            for (int i = 0; i < 7; i++) {  // da li uopste sadrzi slovo
                if(i % 2 == 0) {
                    if (generisan.charAt(i)==prvo || generisan.charAt(i)==drugo || generisan.charAt(i)==trece || generisan.charAt(i)==cetvrto) {
                        sadrzi = true;
                        break;
                    }
                }
            }
            if(!sadrzi){
                out.println("Rezultat - - - -");
                continue;
            }

            for (int i = 0; i < 7; i++) { // prolazi kroz poruku
                if (generisan.equals(poruka)) { //pogodio ceo string
                    out.println("Cestitam pogodili ste tajni niz slova " + generisan + " . Da li zelite novu igru? (da/ne)");
                    return true;
                } // a e i o u  ,  a i a o

                if (i % 2 == 0) { // Proveravamo samo parne indekse
                    String slovo = String.valueOf(poruka.charAt(i)); // uzme jedno slovo
                    if (generisan.contains(slovo)) { // Da li uopste sadrzi to slovo
                        for (int k = 0; k < 7; k++) {
                            if (sb.charAt(k) != 'X' && k % 2 == 0)
                                sb.setCharAt(k, 'O');
                        }

                        for (char ch : generisan.toCharArray()) {
                            if (ch == slovo.charAt(0)) {
                                sb.setCharAt(i, 'X'); // Pogodio slovo
                                break;
                            }
                        }
                    }
                }
            }
            out.println("Rezultat " + sb);
        }

        return false;
    }

    String generisiNizNERADI(StringBuilder sb){
        String [] samoglasnici = {"a", "e", "i", "o", "u"};
        System.out.println("usaooo");
        for(int i = 0; i < 7; i++){
            int random = new Random().nextInt(5);
            System.out.println("usaooo");
            String slovo = samoglasnici[random];
            System.out.println(slovo + " !!!!!!!!!!!");
            System.out.println("usaooo");
            sb.setCharAt(i, slovo.charAt(0)); //TODO PROVERITI !!!!!!!!!!!!
            System.out.println("usaooo");
            sb.append(' ');
            System.out.println("usaooo");
        }
        return sb.toString();
    }

    String generisiNiz(StringBuilder sb){
        String [] samoglasnici = {"a", "e", "i", "o", "u"};
        for(int i = 0; i < 7; i++) {
            StringBuilder string = new StringBuilder(Arrays.toString(samoglasnici));
            int random = new Random().nextInt(5);
            sb.append(string.charAt(random));
        }
        return sb.toString();
    }
}
