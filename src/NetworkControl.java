public interface NetworkControl {
    void connect(GameLogs gameLogs, InGameChat inGameChat);

    void sendMessage(String message);

    void closeConnection();
}
