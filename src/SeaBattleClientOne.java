import java.io.*;
import java.net.*;

public class SeaBattleClientOne {

    String ip;

    private static int port = 9999;

    private static String password = "UTP_12345";

    private static boolean ipCorrect = false;

    SeaBattleClientOne(String ip) {
        this.ip = ip;
    }

    public void connect() {

        try (Socket socket = new Socket(ip, port)) {
            System.out.println("Connection is stable.");

            // Data transmission streams
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                out.println("I am connecting");
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("Server message: " + serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkConnection(String serverAddress) {
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