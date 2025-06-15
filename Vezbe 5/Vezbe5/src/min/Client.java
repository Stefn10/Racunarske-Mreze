package min;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Client {

    public Client() throws IOException {

        Socket socket = new Socket("localhost", 2013);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));
        int a = 3;
        while(a-- > 0){
            int n = new Random().nextInt(101);
            int m = new Random().nextInt(101);

            out.println(n);
            out.println(m);

            String message = in.readLine();
            System.out.println("Server: " + message);
            if(message.contains("cao"))
                break;
        }
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }
}
