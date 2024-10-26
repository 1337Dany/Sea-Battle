import javax.swing.*;
import java.awt.*;

public class GameField extends JPanel {
    Game game;

    GameField(Game game) {
        this.game = game;
        this.setLayout(null);
        this.setBackground(Color.YELLOW);
        drawField();
    }

    public void drawField() {
        this.setBounds(0, 0, (int) (game.getWidth() / 1.2), (int) (game.getHeight() / 1.2));
        this.setVisible(true);

        revalidate();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        for (int i = 1; i <= 9; i++) {
            graphics2D.setColor(Color.ORANGE);
            graphics2D.fillRect(this.getWidth() / 10 * i, 0, 5, this.getHeight());
            graphics.fillRect(0, this.getHeight() / 10 * i, this.getWidth(), 5);
        }
    }
}
