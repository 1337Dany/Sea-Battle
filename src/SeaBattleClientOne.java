import java.io.*;
import java.net.*;

public class SeaBattleClientOne {

    private static String ip;

    private static final int port = 9999;

    private static final String password = "UTP_12345";

    private static boolean ipCorrect = false;
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;

    SeaBattleClientOne(String ip) {
        SeaBattleClientOne.ip = ip;
    }

    public void connect() {

        try (Socket socket = new Socket(ip, port)) {

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
        ip = serverAddress;
        try {
            socket = new Socket(serverAddress, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(password);
            if (in.readLine().equals("accepted")) {
                ipCorrect = true;
            }else{
                ipCorrect = false;
            }

        } catch (IOException e) {
            System.out.println("Incorrect IP");
        }


        return ipCorrect;
    }
}