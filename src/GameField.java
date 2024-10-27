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
        this.setBounds(0, 0, 650, 650);
        revalidate();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        int strokeWidth = 20;

        graphics2D.setColor(Color.ORANGE);
        graphics2D.fillRect(strokeWidth,
                strokeWidth,
                this.getWidth()-strokeWidth*2,
                this.getHeight()-strokeWidth*2);
        graphics2D.setColor(Color.DARK_GRAY);
        graphics2D.fillRect(
                strokeWidth*2,
                strokeWidth*2,
                this.getWidth() - strokeWidth * 4,
                this.getHeight() - strokeWidth * 4);

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(getFont().deriveFont(20.0f));
        for (int i = 1; i <= 10; i++) {
            graphics2D.drawString(String.valueOf((i-1)),
                    ((this.getWidth() - (strokeWidth*2))/11)*i + graphics2D.getFontMetrics().stringWidth(String.valueOf(i)),
                    18);
            char ch = (char) (i+64);
            graphics2D.drawString(String.valueOf(ch),
                    4,
                    ((this.getHeight() - (strokeWidth))/11)*i +20);
        }

        graphics2D.setColor(Color.ORANGE);
        for (int i = 1; i <= 9; i++) {
            graphics2D.fillRect(
                    ((this.getWidth() - (strokeWidth * 4)) / 10 * i) + strokeWidth*2,
                    strokeWidth*2,
                    5,
                    this.getHeight() - strokeWidth * 4);
            graphics.fillRect(
                    strokeWidth*2,
                    ((this.getHeight() - (strokeWidth * 4)) / 10 * i) + strokeWidth*2,
                    this.getWidth() - strokeWidth * 4,
                    5);
        }
    }
}
