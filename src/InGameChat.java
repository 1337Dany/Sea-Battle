import javax.swing.*;
import java.awt.*;

public class InGameChat extends JPanel {
    GameManager gameManager;
    InGameChat(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void drawInGameChat(){

        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);

        JLabel inGameChatLogo = new JLabel("Chat");
        inGameChatLogo.setFont(inGameChatLogo.getFont().deriveFont(30.0f));
        inGameChatLogo.setForeground(Color.ORANGE);
        inGameChatLogo.setBackground(Color.DARK_GRAY);
        inGameChatLogo.setVerticalAlignment(SwingConstants.CENTER);
        inGameChatLogo.setHorizontalAlignment(SwingConstants.CENTER);

        inGameChatLogo.setBounds(
                0,
                10,
                this.getWidth(),
                50
        );
        this.add(inGameChatLogo);

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
