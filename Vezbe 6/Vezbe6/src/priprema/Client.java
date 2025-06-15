package priprema;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Client {

    Client() throws IOException{

        Socket socket = new Socket("localhost", 2025);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));

        String message = in.readLine();
        System.out.println(message);
        message = in.readLine();
        System.out.println(message);
        if(!message.contains("broj"))
            socket.close();

        boolean siguran = true;
        while(siguran) {
            boolean tacan = true;

                int random = Integer.parseInt(kin.readLine());
                switch (random) {
                    case 3:
                        out.println(random);
                        tacan = false;
                        break;
                    case 5:
                        out.println(random);
                        tacan = false;
                        break;
                    case 6:
                        out.println(random);
                        tacan = false;
                        break;
                    default:
                        System.out.println("Neispravan broj, biraj 3, 5 ili 6");
                }

            while(true) {
                message = in.readLine();
                System.out.println(message);
                if(message.contains("Niste pogodili rec.")){
                    siguran = false;
                    break;
                }
                if(message.contains("Pogodili ste rec.")){
                    message = kin.readLine();
                    out.println(message);
                    if(!message.equals("da")){
                        siguran = false;
                        break;
                    }
                    continue;
                }
                message = kin.readLine();
                out.println(message);
            }
        }
        System.out.println("Podravvvvvvvv");
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }
}
