package Zd3;

import java.io.*;
import java.net.Socket;

public class Client {

    public Client() throws IOException {

        Socket socket = new Socket("localhost", 2025);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            String message = in.readLine();
            if (message.contains("Pogodili")){
                System.out.println(message);
                break;
            }
            System.out.println("Server: " + message);

            message = kin.readLine();
            out.println(message);
        }
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }
}
