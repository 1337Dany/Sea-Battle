import java.io.*;
import java.net.*;

public class SeaBattleServer {
    private static final int port = 9999;
    public static boolean userConnected = false;

    GameLogs gameLogs;
    InGameChat inGameChat;

    private static final String password = "UTP_12345";
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    BufferedReader in = null;
    PrintWriter out = null;

    public void startServer(GameLogs gameLogs, InGameChat inGameChat) {
        this.gameLogs = gameLogs;
        this.inGameChat = inGameChat;

        new Thread(() -> {

            try  {
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
                            if(input != null) {
                                if (input.equals("I am connecting")) {
                                    userConnected = true;
                                    gameLogs.updateLinkedList("\t----Client \"" + clientSocket.getInetAddress() + "\" connected.----");
                                    break;
                                }else{
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
                        if(clientMessage.equals("I am disconnecting")){
                            gameLogs.updateLinkedList("----Client disconected----");
                        }
                        gameLogs.updateLinkedList("Client data: " + clientMessage);
                    }
                } catch (IOException e) {
                    gameLogs.updateLinkedList("----Client disconected----");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void closeServer() {
        try {
            out.println("I am disconnecting");
            serverSocket.close();
            clientSocket.close();
            in.close();
            out.close();
        }catch (IOException | NullPointerException ignored){}
    }


}