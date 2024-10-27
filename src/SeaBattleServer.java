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
                Socket clientSocket = null;
                BufferedReader in = null;
                PrintWriter out = null;
                while (!userConnected) {
                    gameLogs.updateLinkedList("Waiting for client connection...");

                    clientSocket = serverSocket.accept();
                    //Data transmission streams
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    if (in.readLine().equals(password) && !userConnected) {
                        out.println("accepted");
                        gameLogs.updateLinkedList("Client \"" + clientSocket.getInetAddress() + "\" tried to connect.");
                        while (true) {
                            if (in.readLine().equals("I am connecting")) {
                                userConnected = true;
                                gameLogs.updateLinkedList("Client \"" + clientSocket.getInetAddress() + "\" connected.");
                                break;
                            }
                        }
                    } else if (!in.readLine().equals(password) && !userConnected) {
                        out.println("denied");
                        gameLogs.updateLinkedList("Client \"" + clientSocket.getInetAddress() + "\" has declined.");
                        clientSocket.close();
                        in.close();
                        out.close();
                    }
                }


                try {
                    String clientMessage;
                    while ((clientMessage = in.readLine()) != null) {
                        gameLogs.updateLinkedList("Client data: " + clientMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }


}