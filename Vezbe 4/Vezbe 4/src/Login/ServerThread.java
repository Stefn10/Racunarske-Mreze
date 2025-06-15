package Login;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable{

    private Socket socket;
    private int clientNumber;
    private String logIn = Server.getLogIn();
    BufferedReader in;
    PrintWriter out;

    public ServerThread(Socket socket, int clientNumber){
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    private boolean isLoggedIn() throws IOException {
        int brojac = 3;
        boolean tacan = false;

        while(brojac > 0){
            String message = in.readLine();
            if(message.equals(logIn)){
                out.println("Cestitam!!! Koja je tvoja poruka za mene?");
                tacan = true;
                break;
            }
            out.println("Pokusaj jos jednom.");
            brojac--;
        }

        return tacan;
    }

    @Override
    public void run() {

        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);

            out.println("Dobrodosao, uloguj se.");
            out.println("Imas 3 pokusaja.");
            String message;

            if (isLoggedIn()) {
                while(true) {
                    if (clientNumber % 2 == 0) {
                        message = in.readLine();
                        System.out.println(message);
                        if(message.contains("exit"))
                            break;
                        out.println(message.toUpperCase());
                    }
                    else {
                        message = in.readLine();
                        System.out.println(message);
                        if(message.contains("exit"))
                            break;
                        StringBuilder sb = new StringBuilder(message);
                        sb.reverse();
                        out.println(sb);
                    }
                }
            }

            socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
