import javax.swing.*;
import java.awt.*;

public class GameField extends JPanel {
    JFrame window;

    GameField(JFrame window) {
        this.window = window;
        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);
        drawField();
    }

    public void drawField() {
        window.add(this);
        this.setBounds(0, 0,650,650);
        revalidate();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        int strokeWidth = 10;
        int arc = 100;

        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRoundRect(strokeWidth, strokeWidth,
                this.getWidth()-strokeWidth*2, this.getHeight()-strokeWidth*2,
                arc, arc);

        graphics2D.setStroke(new BasicStroke(strokeWidth));

        graphics2D.setColor(Color.ORANGE);
        graphics2D.drawRoundRect(
                strokeWidth, strokeWidth,
                this.getWidth()-strokeWidth*2, this.getHeight()-strokeWidth*2,
                arc, arc);

        for (int i = 1; i <= 9; i++) {
            graphics2D.setColor(Color.ORANGE);
            graphics2D.fillRect((this.getWidth() / 10 * i), strokeWidth, 5, this.getHeight()-strokeWidth*2);
            graphics.fillRect(strokeWidth, this.getHeight() / 10 * i, this.getWidth()-strokeWidth*2, 5);
        }
    }
}
