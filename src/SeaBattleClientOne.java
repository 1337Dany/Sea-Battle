import java.io.*;
import java.net.*;

public class SeaBattleClientOne implements NetworkControl {

    private static String ip;

    private static final int port = 9999;

    private static final String password = "UTP_12345";
    GameLogs gameLogs;
    InGameChat inGameChat;

    private static boolean ipCorrect = false;
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;

    SeaBattleClientOne(String ip) {
        SeaBattleClientOne.ip = ip;
    }

    @Override
    public void connect(GameLogs gameLogs, InGameChat inGameChat) {
        this.gameLogs = gameLogs;
        this.inGameChat = inGameChat;

        new Thread(() -> {
            out.println("I am connecting");
            gameLogs.updateLinkedList("----Connection successful----");
            try {
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    if (serverMessage.equals("I am disconnecting")) {
                        gameLogs.updateLinkedList("----Server disconected----");
                    } else if (serverMessage.contains("Chat: ")) {
                        inGameChat.addMessage("(Opponent): " + serverMessage.substring(6));
                    } else if (serverMessage.contains("Game: ")) {
                        if (serverMessage.contains("ready")) {
                            GameManager.setOpponentState(true);
                            gameLogs.updateLinkedList("Opponent is ready");
                        }
                    } else if (serverMessage.contains("Shoot to: ")) {
                        GameManager.hit(
                                Integer.parseInt(serverMessage.substring(10, 11)),
                                Integer.parseInt(serverMessage.substring(11, 12))
                        );
                    } else if (serverMessage.contains("miss")) {
                        GameManager.amIHitOpponent(false,
                                Integer.parseInt(serverMessage.substring(5, 6)),
                                Integer.parseInt(serverMessage.substring(6, 7)));
                    } else if (serverMessage.contains("hit")) {
                        GameManager.amIHitOpponent(true,
                                Integer.parseInt(serverMessage.substring(4, 5)),
                                Integer.parseInt(serverMessage.substring(5, 6)));
                    }else if (serverMessage.contains("I loose")){
                        gameLogs.updateLinkedList("You are winner");
                        System.out.println("i win");

                    }

                }
            } catch (IOException e) {
                gameLogs.updateLinkedList("----Server disconected----");
            }
        }).start();

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
            } else {
                ipCorrect = false;
            }

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
}