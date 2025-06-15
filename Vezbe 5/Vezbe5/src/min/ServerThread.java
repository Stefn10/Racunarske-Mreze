package min;

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

            int a = 3;
            while(a-- > 0){
                int n = Integer.parseInt(in.readLine());
                int m = Integer.parseInt(in.readLine());

                if(n > m) {
                    int zbir = n + m;
                    if ((zbir >= 65 && zbir <= 90) || (zbir >= 97 && zbir <= 122)) {
                        out.println((char) zbir);
                    } else {
                        out.println("cao cao");
                        Server.gasiServer();
                        break;
                    }
                } else if(n < m){
                    int razlika = m - n;
                    if((razlika >=65 && razlika <=90) || (razlika >=97 && razlika <=122)){
                        out.println((char) razlika);
                    } else
                        out.println("Razlika brojeva n-m je: " + razlika);
                } else {
                    out.println("JEdNaki su.");
                }
            }
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
