import javax.swing.*;
import java.awt.*;

public class Game {
    GameField gameField;
    JFrame window;

    Game(SeaBattleClientOne seaBattleClientOne, JFrame window) {
        this.window = window;
        gameField = new GameField(window);
        window.add(gameField, BorderLayout.CENTER);
        gameField.setBounds(0, 0, window.getWidth(), window.getHeight());
    }

    Game(SeaBattleServer seaBattleServer, JFrame window) {
        this.window = window;
        gameField = new GameField(window);
        window.add(gameField, BorderLayout.CENTER);
        gameField.setBounds(0, 0, window.getWidth(), window.getHeight());
    }

    private void history() {
    }

    private void connection() {
    }

    private void dataExchange() {
    }

    private void fieldPainting() {
    }
}
