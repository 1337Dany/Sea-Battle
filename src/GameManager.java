import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameManager {
    JNILogicManager jniLogicManager;
    private final JFrame window;
    private final JPanel menuPanel;
    private JPanel buttonPanel;
    private GameField gameField;
    private EnemyField enemyField;
    private PlaceShips placeShips;
    private HistoryLogs historyLogs;
    private InGameChat inGameChat;
    private GameLogs gameLogs;

    private final JButton rotateShip = new JButton("Rotate");
    private final JButton showEnemyDesk = new JButton("Show enemy desk");
    private final JButton exit = new JButton("Surrender looser");
    private final NetworkControl networkControl;

    GameManager(SeaBattleServer networkControl, JFrame window, JPanel menuPanel) {
        this.networkControl = networkControl;
        this.window = window;
        this.menuPanel = menuPanel;
        jniLogicManager = new JNILogicManager();

        startGame();
        networkControl.setGameManager(this);
        networkControl.connect();
        jniLogicManager.setMyTurn(true);
    }

    GameManager(SeaBattleClient networkControl, JFrame window, JPanel menuPanel) {
        this.networkControl = networkControl;
        this.window = window;
        this.menuPanel = menuPanel;
        jniLogicManager = new JNILogicManager();

        startGame();
        networkControl.setGameManager(this);
        networkControl.connect();
        jniLogicManager.setMyTurn(false);
    }

    private void startGame() {
        drawAll();
        manageCore();
        SettingsSetter.setParametersToObjects(window);
    }

    private void manageCore() {
        new Thread(() -> {
            ActionListener actionListener = e -> jniLogicManager.setRotated(!jniLogicManager.isRotated());
            rotateShip.addActionListener(actionListener);
            showEnemyDesk.addActionListener(event -> {
                if (enemyField.isVisible()) {
                    enemyField.setVisible(false);
                    gameField.setVisible(true);

                } else {
                    enemyField.setVisible(true);
                    gameField.setVisible(false);
                }
                enemyField.revalidate();
                enemyField.repaint();
            });
            exit.addActionListener(event -> closeAll());

            boolean isShipsPlaced = false;
            while (true) {
                if (placeShips.countShips() == 0 && jniLogicManager.isGameStarted()) {
                    jniLogicManager.setGameStart(false);
                    buttonPanel.add(showEnemyDesk);
                    buttonPanel.repaint();
                    gameLogs.addMessage("Game is starting!");
                }

                if (placeShips.countShips() == 0 && !isShipsPlaced) {
                    window.remove(placeShips);
                    rotateShip.removeActionListener(actionListener);
                    buttonPanel.remove(rotateShip);
                    window.repaint();
                    window.revalidate();

                    buttonPanel.revalidate();
                    buttonPanel.repaint();

                    gameLogs.addMessage("I am ready to start");
                    networkControl.sendMessage("ready");

                    isShipsPlaced = true;
                }
            }
        }).start();
    }

    public void addMessageToGameChat(String message) {
        inGameChat.sendMessage(message);
    }

    public void addMessageToGameLogs(String message) {
        gameLogs.addMessage(message);
    }

    public void shootTo(int x, int y) {
        networkControl.sendMessage("Shoot to: " + x + y);
        historyLogs.addHistoryNote("Shooting is conducted to " + ((char) (y + 65)) + x);
        jniLogicManager.setMyTurn(false);
    }

    public void hit(int x, int y) {
        if (jniLogicManager.contains(x, y)) {
            networkControl.sendMessage("hit " + x + y);
            historyLogs.addHistoryNote("Opponent is hitted your ship!");
            jniLogicManager.kill(x, y);
            if (jniLogicManager.isEveryoneDead()) {
                networkControl.sendMessage("I loose");
                addMessageToGameLogs("You loose");
            }
            gameField.repaint();
        } else {
            networkControl.sendMessage("miss " + x + y);
            historyLogs.addHistoryNote("Opponent is missed!");
        }
        jniLogicManager.setMyTurn(true);
    }

    public void amIHitOpponent(boolean bool, int x, int y) {
        if (bool) {
            jniLogicManager.addKilledShip(x, y);
            historyLogs.addHistoryNote("Field " + ((char) (y + 65)) + x + " is hitted");
        } else {
            jniLogicManager.addOpenLocation(x, y);
            historyLogs.addHistoryNote("Field " + ((char) (y + 65)) + x + " is empty");
        }
    }

    private void drawAll() {
        placeShips = new PlaceShips(this);
        gameField = new GameField(placeShips, this);
        enemyField = new EnemyField(this);
        historyLogs = new HistoryLogs(this);
        inGameChat = new InGameChat(this);
        inGameChat.messageCheckingThread(networkControl);
        gameLogs = new GameLogs(this);

        placeShips.setBounds(
                gameField.getX() + gameField.getWidth() + 200,
                0,
                window.getWidth() - gameField.getWidth() - 200,
                500
        );
        drawButtonPanel();

        placeShips.drawPanel();
        gameField.placeShips();


        historyLogs.setBounds(
                gameField.getX() + gameField.getWidth() + 200,
                0,
                window.getWidth() - gameField.getWidth() - 200,
                500
        );
        historyLogs.drawHistory();

        inGameChat.setBounds(
                gameField.getX() + gameField.getWidth(),
                500,
                window.getWidth() - gameField.getWidth(),
                window.getHeight() - 500
        );
        inGameChat.drawInGameChat();

        gameLogs.setBounds(
                0,
                gameField.getHeight(),
                window.getWidth() - inGameChat.getWidth(),
                window.getHeight() - gameField.getHeight()
        );
        gameLogs.drawGameLogs();

        SettingsSetter.setParametersToObjects(window);
    }

    private void drawButtonPanel() {
        buttonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                Graphics2D graphics2D = (Graphics2D) graphics;
                int strokeWidth = 10;
                int arc = 100;

                graphics2D.setColor(Color.DARK_GRAY);
                graphics2D.fillRoundRect(strokeWidth, 10,
                        this.getWidth() - strokeWidth * 2, this.getHeight() - strokeWidth * 2,
                        arc, arc);

                graphics2D.setStroke(new BasicStroke(strokeWidth));

                graphics2D.setColor(Color.CYAN);
                graphics2D.drawRoundRect(
                        strokeWidth, 10,
                        this.getWidth() - strokeWidth * 2, this.getHeight() - strokeWidth * 2,
                        arc, arc);
            }
        };

        buttonPanel.setLayout(null);
        buttonPanel.setBackground(Color.DARK_GRAY);


        buttonPanel.setBounds(
                gameField.getX() + gameField.getWidth(),
                0,
                200,
                500
        );

        exit.setBounds(
                15,
                buttonPanel.getHeight() - 130,
                buttonPanel.getWidth() - 30,
                70
        );

        showEnemyDesk.setBounds(
                exit.getX(),
                buttonPanel.getHeight() / 2,
                exit.getWidth(),
                exit.getHeight()
        );

        rotateShip.setBounds(
                exit.getX(),
                60,
                exit.getWidth(),
                exit.getHeight()
        );

        buttonPanel.add(rotateShip);
        buttonPanel.add(exit);
        window.add(buttonPanel);
    }

    private void closeAll() {
        window.remove(gameField);
        window.remove(placeShips);
        window.remove(buttonPanel);
        window.remove(historyLogs);
        window.remove(inGameChat);
        window.remove(gameLogs);
        window.remove(enemyField);
        window.revalidate();
        window.repaint();
        networkControl.closeConnection();

        window.add(menuPanel);
    }

    public JFrame getWindow() {
        return window;
    }

    public JNILogicManager getJniLogicManager() {
        return jniLogicManager;
    }

    public void setOpponentState(boolean opponentState) {
        jniLogicManager.setGameStart(opponentState);
    }

    public boolean isMyTurn() {
        return jniLogicManager.isMyTurn();
    }
}
