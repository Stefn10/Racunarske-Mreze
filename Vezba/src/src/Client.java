import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {


    public Client() throws IOException {

        Socket socket = new Socket("localhost", 2025);
//        String adresa = InetAddress.getLocalHost().getHostAddress();
//        Socket socket1 = new Socket(adresa, 2025);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);

        String message = in.readLine();
        System.out.println(message);
        out.println("Cao cao");
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }
}
