package Zd2;

import java.io.*;
import java.net.Socket;

public class Client {

    public Client() throws IOException {

        Socket socket = new Socket("localhost", 2025);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));

        String message = in.readLine();
        System.out.println("Server: " + message);

        while(true){
            System.out.println("Unesite poruku za server:");
            message = kin.readLine();
            out.println(message);

            if(message.equalsIgnoreCase("exit"))
                break;

            message = in.readLine();
            System.out.println("Server: " + message);
        }
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }
}
