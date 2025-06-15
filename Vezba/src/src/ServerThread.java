import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable{

    Socket socket;
    int clientNumber;

    ServerThread(Socket socket, int clientNumber){
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            out.println("Cao cao");
            String message = in.readLine();
            System.out.println(message);

            socket.close();

        } catch(Exception e){
            System.out.println("Gresskaa");
        }
    }
}
