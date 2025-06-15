package pogadjanje2;

import java.io.*;
import java.net.Socket;

public class Client {

    public Client() throws IOException {

        Socket socket = new Socket("localhost", 2025);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));

        boolean alive = true;
        while (alive) {
            //Server: Dobrodošli na server! Unesite koju duzinu reči...
            String message = in.readLine(); //1
            System.out.println(message);

            String pokusaji = kin.readLine();  // Unos duzine reci
            out.println(pokusaji); //2

            message = in.readLine(); // Vasa rec: [___]  3
            System.out.println(message);
            message = in.readLine(); // Pocnite sa nagadjanjem slova. 4
            System.out.println(message);

            while (true) {
                String slovo = kin.readLine();  // unos slova
                out.println(slovo); // 5

                message = in.readLine(); // Uspešno / Niste pogodili / Čestitam  6
                System.out.println(message);
                if(message.contains("srece")){
                    alive = false;
                    break;
                }

                if (message.contains("Cestitam")) {
                    // Čekamo poruku "Da li zelite jos da igrate?"
                    message = in.readLine(); // 7
                    System.out.println(message);

                    String odgovor = kin.readLine(); // da/ne
                    out.println(odgovor); // 8

                    if (odgovor.toLowerCase().contains("ne"))
                        alive = false;

                    break;
                }
            }
        }

        System.out.println("Caoo...");
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }
}
