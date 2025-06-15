package min;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    static ServerSocket serverSocket;

    public Server() throws IOException {
        serverSocket = new ServerSocket(2013);
        Socket socket = serverSocket.accept();
        ServerThread serverThread = new ServerThread(socket);
        Thread thread = new Thread(serverThread);
        thread.start();
    }

    public static void gasiServer() throws IOException {
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }

}
