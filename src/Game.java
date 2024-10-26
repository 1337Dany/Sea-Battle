import javax.swing.*;
import java.awt.*;

public class Game{
    GameField gameField;
    JFrame window;
    Game(SeaBattleClientOne seaBattleClientOne, JFrame window) {
        this.window = window;
        window.add(gameField, BorderLayout.CENTER);
        gameField.setBounds(0,0,window.getWidth(),window.getHeight());

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
