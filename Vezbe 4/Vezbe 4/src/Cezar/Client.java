package Cezar;

import java.io.*;
import java.net.Socket;

public class Client {

    private int key;

    public Client() throws IOException {

        Socket socket = new Socket("localhost", 2025);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));

        String message = in.readLine();
        System.out.println("Server: " + message);
        message = kin.readLine();
        out.println(message);
        key = Integer.parseInt(message);

        while(true){
            message = decryptMessage(in.readLine());
            System.out.println("Server: " + message);

            message = encryptMessage(kin.readLine());
            out.println(message);
            String dalExit = decryptMessage(message);
            if(dalExit.contains("exit"))
                break;

            message = decryptMessage(in.readLine());
            System.out.println("Server: " + message);
        }
        socket.close();
    }

    private String encryptMessage(String message){
        StringBuilder sb = new StringBuilder();
        for(char ch : message.toCharArray()){
            sb.append((char) (ch + key));
        }
        return sb.toString();
    }

    private String decryptMessage(String message){
        StringBuilder sb = new StringBuilder();
        for(char ch : message.toCharArray()){
            sb.append((char) (ch - key));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }
}
