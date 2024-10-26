import javax.swing.*;
import java.awt.*;

public class GameLogs extends JPanel {
    GameManager gameManager;
    GameLogs(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void drawGameLogs(){

        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);

        JLabel gameLogsLogo = new JLabel("game logs");
        gameLogsLogo.setFont(gameLogsLogo.getFont().deriveFont(20.0f));
        gameLogsLogo.setForeground(Color.ORANGE);
        gameLogsLogo.setBackground(Color.DARK_GRAY);
        gameLogsLogo.setVerticalAlignment(SwingConstants.CENTER);
        gameLogsLogo.setHorizontalAlignment(SwingConstants.CENTER);

        gameLogsLogo.setBounds(
                0,
                5,
                this.getWidth(),
                50
        );
        this.add(gameLogsLogo);
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
