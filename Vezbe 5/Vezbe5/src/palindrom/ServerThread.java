package palindrom;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable{

    Socket socket;

    public ServerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);

            while(true) {
                String message = in.readLine();
                String decr = dekriptovana(message);
                if(decr.contains("exit"))
                    break;
                StringBuilder sb = new StringBuilder();
                if (palindrom(decr)) {
                    for(int i = 0; i < decr.length(); i++){
                        if(i % 2 != 0)
                            sb.append(decr.charAt(i));
                    }
                    out.println(sb);
                } else {
                    for(int i = 0; i < decr.length(); i++){
                        if(i % 2 == 0)
                            sb.append(decr.charAt(i));
                    }
                    out.println(sb);
                }
            }
        socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean palindrom(String poruka){
        int broj = poruka.length();
        for(int i = 0; i < broj/2; i++)
            if(poruka.charAt(i) != poruka.charAt(broj - 1 - i))
                return false;
        System.out.println("Yes bro");
        return true;
    }

    private String dekriptovana(String message){
        int broj = message.length();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < broj; i++){
            char ch = message.charAt(i);
            sb.append((char) (ch - broj));
        }
        return sb.toString();
    }
}
