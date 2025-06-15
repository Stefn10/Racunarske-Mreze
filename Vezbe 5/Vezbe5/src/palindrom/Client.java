package palindrom;

import java.io.*;
import java.net.Socket;

public class Client {

    public Client() throws IOException {

        Socket socket = new Socket("localhost", 2013);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));

        while (true){
            String message = kin.readLine();
            String encr =  enkriptovana(message);

            if(message.contains("exit"))
                socket.close();

            out.println(encr);
            message = in.readLine();
            System.out.println("Server: " + message);
        }
    }

    private String enkriptovana(String message){
        int broj = message.length();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < broj; i++){
            char ch = message.charAt(i);
            sb.append((char) (ch + broj));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }
}
