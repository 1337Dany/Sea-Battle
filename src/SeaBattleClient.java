import java.io.*;
import java.net.*;

public class SeaBattleClient implements NetworkControl {
    GameManager gameManager;

    private static boolean ipCorrect = false;/*Put in cpp*/
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;

    @Override
    public void connect() {

        new Thread(() -> {/*Put in cpp*/
            out.println("I am connecting");
            gameManager.addMessageToGameLogs("----Connection successful----");
            try {
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    receivedMessaged(serverMessage);

                }
            } catch (IOException e) {
                gameManager.addMessageToGameLogs("----Server disconected----");
            }
        }).start();

    }

    private void receivedMessaged(String serverMessage) {/*Put in cpp*/
        if (serverMessage.equals("I am disconnecting")) {
            gameManager.addMessageToGameLogs("----Server disconected----");

        } else if (serverMessage.contains("Chat: ")) {
            gameManager.addMessageToGameChat("(Opponent): " + serverMessage.substring(6));

        } else if (serverMessage.contains("ready")) {
                gameManager.setOpponentState(true);
                gameManager.addMessageToGameLogs("Opponent is ready");

        } else if (serverMessage.contains("Shoot to: ")) {
            gameManager.hit(
                    Integer.parseInt(serverMessage.substring(10, 11)),
                    Integer.parseInt(serverMessage.substring(11, 12))
            );

        } else if (serverMessage.contains("miss")) {
            gameManager.amIHitOpponent(false,
                    Integer.parseInt(serverMessage.substring(5, 6)),
                    Integer.parseInt(serverMessage.substring(6, 7)));

        } else if (serverMessage.contains("hit")) {
            gameManager.amIHitOpponent(true,
                    Integer.parseInt(serverMessage.substring(4, 5)),
                    Integer.parseInt(serverMessage.substring(5, 6)));

        } else if (serverMessage.contains("I loose")) {
            gameManager.addMessageToGameLogs("You are winner");

        }
    }

    public static boolean checkConnection(String serverAddress) {
        try {
            int port = 9999;
            socket = new Socket(serverAddress, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String password = "UTP_12345";
            out.println(password);
            ipCorrect = in.readLine().equals("accepted");

        } catch (IOException ignored) {

        }
        return ipCorrect;
    }

    @Override
    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void closeConnection() {
        out.println("I am disconnecting");
        try {
            socket.close();
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