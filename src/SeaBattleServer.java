import java.io.*;
import java.net.*;

public class SeaBattleServer {
    private static final int port = 9999;
    public static boolean userConnected = false;

    GameLogs gameLogs;

    private static final String password = "UTP_12345";

    public void startServer(GameLogs gameLogs) {
        this.gameLogs = gameLogs;

        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                gameLogs.updateLinkedList("Waiting for client connection...");

                Socket clientSocket = serverSocket.accept();
                //Data transmission streams
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                if (in.readLine().equals(password)) {
                    out.println("accepted");
                    gameLogs.updateLinkedList("Client \"" + clientSocket.getInetAddress() + "\" tried to connect.");
                } else {
                    out.println("denied");
                    gameLogs.updateLinkedList("Client \"" + clientSocket.getInetAddress() + "\" has declined.");
                    clientSocket.close();
                }

                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));


                try {
                    String clientMessage;
                    while ((clientMessage = in.readLine()) != null) {
                        gameLogs.updateLinkedList("Client data: " + clientMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Sending message to user
                String message;
                while ((message = userInput.readLine()) != null) {
                    out.println(message);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    //public static void main(String[] args) {
//        checkConnection();
//    }

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