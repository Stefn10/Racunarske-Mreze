package piq;

import java.io.*;
import java.net.Socket;

public class Client {

    public Client() throws IOException {

        Socket socket = new Socket("localhost", 2013);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));


        String message = kin.readLine();
        out.println(message);

        message = in.readLine();
        if(message.contains("QUIT"))
            socket.close();
        System.out.println("[S] : " + message);

        message = message.replaceAll("[()]", "");
        String [] niz = message.split(",");
        int p = Integer.parseInt(niz[0]);
        int q = Integer.parseInt(niz[1]);
        int A = Integer.parseInt(in.readLine());
        System.out.println("A = " + A);
        int random_b = (int) Math.floor(Math.random() * p) + 1;
        int B = (int) Math.pow(q, random_b) % p;
        out.println(B);
        System.out.println("B = " + B);
        int s = (int) Math.pow(A, random_b) % p;
        System.out.println("Tajni broj je: " + s);
        socket.close();

    }

    public static void main(String[] args) throws IOException {
        new Client();
    }
}
