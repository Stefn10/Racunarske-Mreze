package Racunanje;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable {
    Socket socket;
    char ch;
    char operator = '+';
    int result = 0;
    String number = "";

    public ServerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            out.println("Dobrodošli, unesite svoje ime: ");
            String name = in.readLine();

            while(true){
                out.println(name + ", unesite izraz za rešavanje: ");
                String expression = in.readLine();

                //5+4-2
                if(expression.matches("[0-9+-]+") && !expression.contains("++") && !expression.contains("+-") &&
                        !expression.contains("-+") && !expression.contains("--")){
                    for(int i = 0; i < expression.length(); i++){
                        ch = expression.charAt(i);
                        if(ch == '+' || ch == '-'){
                            if(operator == '+'){
                                result = result + Integer.parseInt(number);
                            } else{
                                result = result - Integer.parseInt(number);
                            }
                            operator = ch;
                            number = "";
                        }
                        else
                            number += ch;

                    }
                    if(operator == '+')
                        result = result + Integer.parseInt(number);
                    else
                        result = result - Integer.parseInt(number);

                    out.println("Rezultat je: " + result);
                    break;
                }else{
                    out.println("Neispravan unos.");
                }
            }
            socket.close();
            System.out.println("Test: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}