import java.io.*;
import java.net.Socket;

public class Client {


    public Client() throws IOException{


        Socket socket = new Socket("192.168.13.44", 2013);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));

        boolean tacan = true;

        String message = "";
        while(tacan) {
            message = in.readLine(); //dobrodosao
            System.out.println(message);
            message = kin.readLine(); //todo start
            out.println(message);

            while (!message.contains("start")) { //provera starta
                message = in.readLine();
                System.out.println(message);
                message = kin.readLine();
            }

            message = in.readLine(); //igra pocela
            System.out.println(message);

            while (true) {
                message = kin.readLine();
                if (message.contains("odustajem")) { // TODO IMPLEMENTIRATI U SERVERU
                    tacan = false;
                    break;
                }
                out.println(message);
                message = in.readLine();
                System.out.println(message); //greska||rezultat||cestitam
                if(message.contains("Da li")){
                    message = kin.readLine();
                    out.println(message);
                    if(message.contains("ne")){
                        tacan = false;
                        break;
                    }
                }
            }
        }

        message = in.readLine();
        out.println(message);
        socket.close();

    }

    public static void main(String[] args) throws IOException {
        new Client();
    }
}
