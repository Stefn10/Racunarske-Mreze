package piq;

import javax.print.attribute.standard.RequestingUserName;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ServerThread implements Runnable{

    Socket socket;

    public ServerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));

            String message = in.readLine();
            if(!message.contains("hvala")) {
                out.println("QUIT");
                socket.close();
                return;
            }
            int p = 11, q = 2;
            String brate = "(" + p + "," + q + ")";
            out.println(brate);
            int random_a = (int) Math.floor(Math.random() * p) + 1;
            int A = (int) Math.pow(q, random_a) % p;
            System.out.println("A= " + A);
            out.println(A);
            int B = Integer.parseInt(in.readLine());
            System.out.println("B = " + B);
            int s = ((int) Math.pow(B, random_a)) % p;

            System.out.println("tajni broj je: " + s);
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
