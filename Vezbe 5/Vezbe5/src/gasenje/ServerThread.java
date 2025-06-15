package gasenje;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable{

    Socket socket;
    int clientNumber;

    public ServerThread(Socket socket, int clientNumber){
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);

            String message = "Dobrodosao klijentu #" + clientNumber;
            out.println(message);

            while(true){
                message = in.readLine();
                System.out.println(message);
                if(message.contains("exit"))
                    break;

                out.println(message.toUpperCase());
            }
            Server.checkClients();
            socket.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
