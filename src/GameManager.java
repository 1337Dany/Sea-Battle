import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameManager {
    JFrame window;

    JPanel buttonPanel;
    static GameField gameField;
    static EnemyField enemyField;
    PlaceShips placeShips;
    static HistoryLogs historyLogs;
    InGameChat inGameChat;
    GameLogs gameLogs;

    JButton rotateShip = new JButton("Rotate");

    private static boolean rotated = false;

    JButton showEnemyDesk = new JButton("Show enemy desk");
    JButton exit = new JButton("Surrender looser");
    static NetworkControl networkControl;

    private static boolean gameStarted = false;

    GameManager(SeaBattleClientOne networkControl, JFrame window) {
        this.networkControl = networkControl;
        this.window = window;
        startGame();
        networkControl.connect(gameLogs, inGameChat);
    }

    GameManager(SeaBattleServer networkControl, JFrame window) {
        this.window = window;
        this.networkControl = networkControl;
        startGame();
        window.revalidate();
        window.repaint();
        networkControl.connect(gameLogs, inGameChat);
    }

    private void startGame() {
        drawAll();
        manageCore();
        SettingsSetter.setParametersToObjects(window);
    }

    private void manageCore() {
        new Thread(() -> {
            ActionListener actionListener = e -> rotated = !rotated;
            rotateShip.addActionListener(actionListener);
            showEnemyDesk.addActionListener(event -> {
                if(enemyField.isVisible()){
                    enemyField.setVisible(false);
                    gameField.setVisible(true);

                }else{
                    enemyField.setVisible(true);
                    gameField.setVisible(false);
                }
                enemyField.revalidate();
                enemyField.repaint();
            });
            exit.addActionListener(event -> {
                closeAll();
            });

            boolean isShipsPlaced = false;
            while (true) {
                if (placeShips.countShips() == 0 && !isShipsPlaced) {
                    window.remove(placeShips);
                    rotateShip.removeActionListener(actionListener);
                    buttonPanel.remove(rotateShip);
                    window.repaint();
                    window.revalidate();

                    gameLogs.updateLinkedList("I am ready to start");
                    networkControl.sendMessage("Game: ready");

                    isShipsPlaced = true;
                }

                if(placeShips.countShips() == 0 && gameStarted){
                    gameStarted = false;
                    gameLogs.updateLinkedList("Game is starting!");
                }

            }
        }).start();
    }

    public static void shootTo(int x, int y){
        networkControl.sendMessage("Shoot to: " + x + y);
        historyLogs.addHistoryNote("Shooting is conducted to " + (char) (x + 64) + y);
    }
    public static void hit(int x, int y){
        Point attack = new Point(x,y);
        if(gameField.getShipLocations().contains(attack)){
            networkControl.sendMessage("hit " + x + y);
            historyLogs.addHistoryNote((char) (x + 64) + y + " is hitted");
            gameField.getShipLocations().remove(attack);
            gameField.repaint();
        }else{
            networkControl.sendMessage("no hit " + x + y);
            historyLogs.addHistoryNote((char) (x + 64) + y + " is empty");
        }
    }
    public static void amIHitOpponent(boolean bool, int x, int y){
        if(bool){
            enemyField.getShipLocations().add(new Point(x,y));
        }else {
            enemyField.getOpenedLocations().add(new Point(x,y));
        }
    }

    private void drawAll() {


        placeShips = new PlaceShips(this);

        gameField = new GameField(placeShips, window);
        enemyField = new EnemyField(this, window);

        placeShips.setBounds(
                gameField.getX() + gameField.getWidth() + 200,
                0,
                window.getWidth() - gameField.getWidth() - 200,
                500
        );
        drawButtonPanel();

        placeShips.drawPanel();
        gameField.placeShips();



        historyLogs = new HistoryLogs(this);
        historyLogs.setBounds(
                gameField.getX() + gameField.getWidth() + 200,
                0,
                window.getWidth() - gameField.getWidth() - 200,
                500
        );
        historyLogs.drawHistory();

        inGameChat = new InGameChat(this);
        inGameChat.setBounds(
                gameField.getX() + gameField.getWidth(),
                500,
                window.getWidth() - gameField.getWidth(),
                window.getHeight() - 500
        );
        inGameChat.drawInGameChat();
        inGameChat.messageCheckingThread(networkControl);

        gameLogs = new GameLogs(this);
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
                graphics2D.fillRoundRect(strokeWidth, strokeWidth,
                        this.getWidth() - strokeWidth * 2, this.getHeight() - strokeWidth * 2,
                        arc, arc);

                graphics2D.setStroke(new BasicStroke(strokeWidth));

                graphics2D.setColor(Color.CYAN);
                graphics2D.drawRoundRect(
                        strokeWidth, strokeWidth,
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
        buttonPanel.add(showEnemyDesk);
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
        window.revalidate();
        window.repaint();
        networkControl.closeConnection();

    }

    public JFrame getWindow() {
        return window;
    }

    public static boolean isRotated() {
        return rotated;
    }

    public static void setOpponentState(boolean opponentState) {
        gameStarted = opponentState;
    }
}
