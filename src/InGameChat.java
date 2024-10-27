import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class InGameChat extends JPanel {
    GameManager gameManager;
    LinkedList<JLabel> labelList = new LinkedList<>();
    JTextArea message = new JTextArea("");

    InGameChat(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void drawInGameChat() {

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

        message.setFont(message.getFont().deriveFont(15.0f));
        message.setBounds(
                55,
                60,
                this.getWidth() - 55*2,
                30
        );

        this.add(message);
        addLinkedList();
        this.add(inGameChatLogo);
        gameManager.getWindow().add(this);
    }

    private void addLinkedList() {
        for (int i = 1; i <= 8; i++) {
            JLabel label = new JLabel("Label" + i);
            label.setFont(label.getFont().deriveFont(15.0f));
            label.setBounds(
                    55,
                    60 + (30 * i),
                    this.getWidth(),
                    30
            );
            labelList.add(label);
            this.add(label);
        }
    }
    private void messageCheckingThread(SeaBattleServer seaBattleServer){
        new Thread(() -> {
            while (true){
                String checkMessage = message.getText();
                if(!checkMessage.equals("") && checkMessage.contains("\n")){
                    addMessage(checkMessage);
                    seaBattleServer.sendMessage("Chat: " + checkMessage);
                }
            }
        }).start();
    }private void messageCheckingThread(SeaBattleClientOne seaBattleClientOne){
        new Thread(() -> {
            while (true){
                String checkMessage = message.getText();
                if(!checkMessage.equals("") && checkMessage.contains("\n")){
                    addMessage(checkMessage);
                    seaBattleClientOne.sendMessage("Chat: " + checkMessage);
                }
            }
        }).start();
    }

    private void addMessage(String newMessage){
        for (int i =  labelList.size()-1; i > 0; i--) {
            labelList.get(i).setText(labelList.get(i-1).getText());
        }
        labelList.get(0).setText(newMessage);
        repaint();
        revalidate();
    }

    public void receiveMessage(){

    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        int strokeWidth = 10;
        int arc = 100;

        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRoundRect(strokeWidth, strokeWidth,
                this.getWidth() - strokeWidth * 2, this.getHeight() - strokeWidth * 2,
                arc, arc);

        graphics2D.setStroke(new BasicStroke(strokeWidth));

        graphics2D.setColor(Color.ORANGE);
        graphics2D.drawRoundRect(
                strokeWidth, strokeWidth,
                this.getWidth() - strokeWidth * 2, this.getHeight() - strokeWidth * 2,
                arc, arc);
    }

}
