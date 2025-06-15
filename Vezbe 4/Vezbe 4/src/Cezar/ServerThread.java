package Cezar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable{

    private Socket socket;
    private int clientNumber;
    private int key;

    public ServerThread(Socket socket, int clientNumber){
        this.socket = socket;
        this.clientNumber = clientNumber;
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

    @Override
    public void run() {

        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            out.println("Dobrodosao klijentu# " + clientNumber + " . Koji je tvoj key?");
            key = Integer.parseInt(in.readLine());
            while(true){
                String poruka = "Koja je tvoja poruka?";
                out.println(encryptMessage(poruka));
                StringBuilder message = new StringBuilder();
                message.append(decryptMessage(in.readLine()));
                System.out.println("Klijent: " + message.toString());

                if(message.toString().contains("exit"))
                    break;

                String reversedAndEncr = encryptMessage(String.valueOf(message.reverse()));
                out.println(reversedAndEncr);
            }
            socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
