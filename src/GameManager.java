import javax.swing.*;
import java.awt.*;

public class GameManager {
    GameField gameField;
    JFrame window;

    HistoryLogs historyLogs;
    InGameChat inGameChat;
    GameLogs gameLogs;

    JButton rotateShip = new JButton("Rotate");
    JButton showEnemyDesk = new JButton("Show enemy desk");
    JButton surrender = new JButton("Surrender looser");

    GameManager(SeaBattleClientOne seaBattleClientOne, JFrame window) {
        this.window = window;
        drawAll();
    }

    GameManager(SeaBattleServer seaBattleServer, JFrame window) {
        this.window = window;
        drawAll();
        SettingsSetter.setParametersToObjects(window);
    }

    private void drawAll() {
        gameField = new GameField(window);

        drawButtonPanel();

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

    private void history() {
    }

    private void connection() {
    }

    private void dataExchange() {
    }

    private void fieldPainting() {
    }

    private void drawButtonPanel() {
        JPanel buttonPanel = new JPanel() {
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

        window.add(buttonPanel);
    }

    public JFrame getWindow() {
        return window;
    }
}
