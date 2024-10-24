import java.io.*;
import java.net.*;

public class SeaBattleClientOne {
    public static void main(String[] args) {
        String serverAddress = "192.168.0.12";
        int port = 9999;

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Connection is stable.");

            // Data transmission streams
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
    }
}