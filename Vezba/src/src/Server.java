import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    int clientNumber = 0;

    public Server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(2025);
        Socket socket = serverSocket.accept();
        while(!serverSocket.isClosed()) {
            ServerThread serverThread = new ServerThread(socket, clientNumber);
            Thread thread = new Thread(serverThread);
            thread.start();
        }
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}
