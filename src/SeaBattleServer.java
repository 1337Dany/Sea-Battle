import java.io.*;
import java.net.*;

public class SeaBattleServer implements NetworkControl {
    private static final int port = 9999;
    public static boolean userConnected = false;

    GameLogs gameLogs;
    InGameChat inGameChat;

    private static final String password = "UTP_12345";
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    BufferedReader in = null;
    PrintWriter out = null;

    @Override
    public void connect(GameLogs gameLogs, InGameChat inGameChat) {
        this.gameLogs = gameLogs;
        this.inGameChat = inGameChat;

        new Thread(() -> {

            try {
                serverSocket = new ServerSocket(port);
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
                            String input = in.readLine();
                            if (input != null) {
                                if (input.equals("I am connecting")) {
                                    userConnected = true;
                                    gameLogs.updateLinkedList("\t----Client \"" + clientSocket.getInetAddress() + "\" connected.----");
                                    break;
                                } else {
                                    userConnected = false;
                                    gameLogs.updateLinkedList("Client \"" + clientSocket.getInetAddress() + "\" disconnected.");
                                    break;
                                }
                            }
                        }
                    } else if (!in.readLine().equals(password) && !userConnected) {
                        out.println("denied");
                        gameLogs.updateLinkedList("Client \"" + clientSocket.getInetAddress() + "\" has declined.");
                    }
                }


                try {
                    String clientMessage;
                    while ((clientMessage = in.readLine()) != null) {
                        if (clientMessage.equals("I am disconnecting")) {
                            gameLogs.updateLinkedList("----Client disconected----");
                        } else if (clientMessage.contains("Chat: ")) {
                            inGameChat.addMessage("(Opponent): " + clientMessage.substring(6));
                        } else if (clientMessage.contains("Game: ")) {
                            if (clientMessage.contains("ready")) {
                                GameManager.setOpponentState(true);
                                gameLogs.updateLinkedList("Opponent is ready");
                            }
                        } else if (clientMessage.contains("Shoot to: ")) {
                            GameManager.hit(
                                    Integer.parseInt(clientMessage.substring(10, 11)),
                                    Integer.parseInt(clientMessage.substring(11, 12))
                            );
                        } else if (clientMessage.contains("no hit")) {
                            GameManager.amIHitOpponent(false,
                                    Integer.parseInt(clientMessage.substring(7, 8)),
                                    Integer.parseInt(clientMessage.substring(8, 9)));
                        } else if (clientMessage.contains("hit")) {
                            GameManager.amIHitOpponent(true,
                                    Integer.parseInt(clientMessage.substring(4, 5)),
                                    Integer.parseInt(clientMessage.substring(5, 6)));
                        }

                    }
                } catch (IOException e) {
                    gameLogs.updateLinkedList("----Client disconected----");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    @Override
    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void closeConnection() {
        try {
            out.println("I am disconnecting");
            serverSocket.close();
            clientSocket.close();
            in.close();
            out.close();
        } catch (IOException | NullPointerException ignored) {
        }
    }


}