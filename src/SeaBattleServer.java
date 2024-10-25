import java.io.*;
import java.net.*;

public class SeaBattleServer {
    private static final int port = 9999;
    public static boolean userConnected = false;

    private static final String password = "UTP_12345";

    public static void kjhgkjhg(String args) {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Waiting for client connection...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client \"" + clientSocket.getInetAddress() + "\" connected succesfully.");

            //Data transmission streams
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            if (in.readLine().equals(password)) {
                out.println("accepted");
            } else {
                out.println("denied");
                clientSocket.close();
            }
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            new Thread(() -> {
                try {
                    String clientMessage;
                    while ((clientMessage = in.readLine()) != null) {
                        System.out.println("Client data: " + clientMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Sending message to user
            String message;
            while ((message = userInput.readLine()) != null) {
                out.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        checkConnection();
    }

    public static void checkConnection() {
        new Thread(() -> {
            Socket clientSocket = new Socket();
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                String clientMessage = "";
                while (true) {
                    if (!clientSocket.isClosed()) {
                        System.out.println("Waiting for a connect......");
                    }
                    clientSocket = serverSocket.accept();
                    System.out.println("Client " + clientSocket.getInetAddress() + " trying to connect.");

                    //Data transmission streams
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    clientMessage = in.readLine();

                    System.out.println(clientMessage);
                    if (clientMessage.equals(password)) {
                        out.println("accepted");
                        userConnected = true;
                    } else {
                        out.println("denied");
                        userConnected = false;
                    }
                    if (userConnected) {
                        System.out.println("Client " + clientSocket.getInetAddress() + " connected succesfully.");
                    } else {
                        System.out.println("Client " + clientSocket.getInetAddress() + " access denied.");
                    }
                    Thread.sleep(1000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (NullPointerException ignored) {
            }
        }).start();

    }

    private static void waitingForConnect() {

    }

}