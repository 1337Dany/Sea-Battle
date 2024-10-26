import java.io.*;
import java.net.*;

public class SeaBattleClientOne {

    String ip;
    private static int port = 9999;

    private static String password = "UTP_12345";

    SeaBattleClientOne(String ip){
        this.ip = ip;
    }

    public static boolean tryToConnect(String serverAddress) {

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Connection is stable.");

            // Data transmission streams
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(password);
            if (!in.readLine().equals("accepted")) {
                return false;
            }
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("Server message: " + serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            String message;
            while ((message = userInput.readLine()) != null) {
                out.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean checkConnection(String serverAddress) {
        boolean check = false;

        try {
            Socket socket = new Socket(serverAddress, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(password);
            if(in.readLine().equals("accepted")){
                check = true;
            }

        } catch (IOException e) {
            System.out.println("Incorrect IP");
        }


        return check;
    }
}