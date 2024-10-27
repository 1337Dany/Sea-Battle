import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class GameLogs extends JPanel {
    GameManager gameManager;
    LinkedList<JLabel> labelList = new LinkedList<>();
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

        addLinkedList();
        this.add(gameLogsLogo);
        gameManager.getWindow().add(this);
    }

    private void addLinkedList(){
        for (int i = 1; i <= 5; i++) {
            JLabel label = new JLabel("Label" + i);
            label.setFont(label.getFont().deriveFont(15.0f));
            label.setBounds(
                    55,
                    15  + (30 * i),
                    this.getWidth(),
                    30
            );
            labelList.add(label);
            this.add(label);
        }
    }

    public void updateLinkedList(String newMessage){
        for (int i =  labelList.size()-1; i > 0; i--) {
            labelList.get(i).setText(labelList.get(i-1).getText());
        }
        labelList.get(0).setText(newMessage);
        repaint();
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
    }

}
