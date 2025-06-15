package test;

import java.io.*;
import java.net.Socket;

public class Client {

    public Client() throws IOException {

        Socket socket = new Socket("localhost", 2027);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));

    }

}
