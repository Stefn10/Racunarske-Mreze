import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

    static int clientNumber = 0;
    static Socket socket;
    static ServerSocket serverSocket;


    public Server() throws IOException{

        serverSocket = new ServerSocket(2013);
        while(!serverSocket.isClosed()){
            try {
                socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket, ++clientNumber);
                Thread thread = new Thread(serverThread);
                thread.start();
            } catch (SocketException se){
                System.out.println("Gasii");
            }
        }

    }
    static void checkClients() throws IOException {
        if(--clientNumber == 0){
            BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Ugasi?");
            String message = kin.readLine();
            if(message.contains("da"))
            //if yes
                serverSocket.close();
        }
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}
