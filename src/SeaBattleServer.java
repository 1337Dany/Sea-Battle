import java.io.*;
import java.net.*;

public class SeaBattleServer implements NetworkControl {
    private final int port = 9999;
    public boolean userConnected = false;

    private GameManager gameManager;

    private final String password = "UTP_12345";
    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    @Override
    public void connect() {

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                while (!userConnected) {
                    gameManager.addMessageToGameLogs("Waiting for client connection...");
                    clientSocket = serverSocket.accept();

                    //Data transmission streams
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new PrintWriter(clientSocket.getOutputStream(), true);

                    if (in.readLine().equals(password) && !userConnected) {
                        out.println("accepted");
                        gameManager.addMessageToGameLogs("Client \"" + clientSocket.getInetAddress() + "\" tried to connect.");

                        while (true) {
                            String input = in.readLine();
                            if (input != null) {
                                if (input.equals("I am connecting")) {
                                    userConnected = true;
                                    gameManager.addMessageToGameLogs("\t----Client \"" + clientSocket.getInetAddress() + "\" connected.----");
                                    break;
                                } else {
                                    userConnected = false;
                                    gameManager.addMessageToGameLogs("Client \"" + clientSocket.getInetAddress() + "\" disconnected.");
                                    break;
                                }
                            }
                        }
                    } else if (!in.readLine().equals(password) && !userConnected) {
                        out.println("denied");
                        gameManager.addMessageToGameLogs("Client \"" + clientSocket.getInetAddress() + "\" has declined.");
                    }
                }


                try {
                    String clientMessage;
                    while ((clientMessage = in.readLine()) != null) {
                        receivedMessage(clientMessage);

                    }
                } catch (IOException e) {
                    gameManager.addMessageToGameLogs("----Client disconected----");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void receivedMessage(String clientMessage){
        if (clientMessage.equals("I am disconnecting")) {
            gameManager.addMessageToGameLogs("----Client disconected----");

        } else if (clientMessage.contains("Chat: ")) {
            gameManager.addMessageToGameChat("(Opponent): " + clientMessage.substring(6));

        } if (clientMessage.contains("ready")) {
                gameManager.setOpponentState(true);
                gameManager.addMessageToGameLogs("Opponent is ready");

        } else if (clientMessage.contains("Shoot to: ")) {
            gameManager.hit(
                    Integer.parseInt(clientMessage.substring(10, 11)),
                    Integer.parseInt(clientMessage.substring(11, 12))
            );

        } else if (clientMessage.contains("miss")) {
            gameManager.amIHitOpponent(false,
                    Integer.parseInt(clientMessage.substring(5, 6)),
                    Integer.parseInt(clientMessage.substring(6, 7)));

        } else if (clientMessage.contains("hit")) {
            gameManager.amIHitOpponent(true,
                    Integer.parseInt(clientMessage.substring(4, 5)),
                    Integer.parseInt(clientMessage.substring(5, 6)));

        } else if (clientMessage.contains("I loose")) {
            gameManager.addMessageToGameLogs("You are winner");
        }
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

    @Override
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }


}