import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CheckClientConnection {
    private static int port = 9999;
    private static String password = "UTP_12345";

    private static boolean ipCorrect = false;
    JPanel connectMenu;

    public CheckClientConnection(JPanel connectMenu) {
        this.connectMenu = connectMenu;

    }

    public boolean checkConnection(String serverAddress) {
        try {
            Socket socket = new Socket(serverAddress, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(password);
            if (in.readLine().equals("accepted")) {
                ipCorrect = true;
            }

            socket.close();
            out.close();
            in.close();

        } catch (IOException e) {
            System.out.println("Incorrect IP");
        }


        return ipCorrect;
    }
}
