import javax.swing.*;
import java.awt.*;

public class HistoryLogs extends JPanel {
    GameManager gameManager;
    HistoryLogs(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void drawHistory(){

        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);

        JLabel historyLogo = new JLabel("History");
        historyLogo.setFont(historyLogo.getFont().deriveFont(30.0f));
        historyLogo.setForeground(Color.ORANGE);
        historyLogo.setBackground(Color.DARK_GRAY);
        historyLogo.setVerticalAlignment(SwingConstants.CENTER);
        historyLogo.setHorizontalAlignment(SwingConstants.CENTER);

        historyLogo.setBounds(
                0,
                10,
                this.getWidth(),
                50
        );
        this.add(historyLogo);
        gameManager.getWindow().add(this);
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
    }
}
