public interface NetworkControl {
    void connect();

    void sendMessage(String message);

    void closeConnection();

    void setGameManager(GameManager gameManager);
}
