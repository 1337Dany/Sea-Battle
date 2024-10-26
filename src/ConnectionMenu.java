import javax.swing.*;
import java.awt.*;

public class ConnectionMenu extends JPanel {
    JTextArea ip = new JTextArea("test");

    JButton checkIPButton = new JButton("Check IP");
    JPanel menuPanel;
    static boolean isCorrect = false;

    ConnectionMenu(JPanel menuPanel) {
        this.menuPanel = menuPanel;
        drawPanel();


        checkIPButton.addActionListener(e -> {
            CheckClientConnection checkClientConnection = new CheckClientConnection(ip, this);
            //if(checkClientConnection.checkConnection(ip.getText())){
            if (ip.getText().equals("test")) {
                isCorrect = true;
                repaint();
            }
        });


    }

    private void drawPanel() {
        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);

        this.setBounds(
                menuPanel.getWidth() / 2 + (int) (0.2 * menuPanel.getWidth()),
                menuPanel.getHeight() / 2 - (int) (0.1 * menuPanel.getHeight()),
                (int) (0.25 * menuPanel.getWidth()),
                (int) (0.4 * menuPanel.getHeight())
        );

        JLabel text = new JLabel("Write IP addres of a server:");
        text.setFont(new Font(text.getFont().getFontName(), Font.PLAIN, 20));

        text.setBounds(
                0,
                this.getHeight() / 2 - (int) (0.2 * this.getHeight()),
                this.getWidth(),
                (int) (0.2 * this.getHeight())
        );

        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setVerticalAlignment(SwingConstants.CENTER);

        ip.setFont(new Font(ip.getFont().getFontName(), Font.PLAIN, 40));
        ip.setOpaque(false);
        ip.setBounds(
                this.getWidth() / 2 - (int) (text.getWidth() * 0.8) / 2,
                text.getY() + text.getHeight(),
                (int) (text.getWidth() * 0.8),
                text.getHeight()
        );

        checkIPButton.setBounds(
                this.getWidth() / 2 - (int) (0.3 * this.getWidth()) / 2,
                this.getHeight() - (int) (0.2 * this.getHeight()),
                (int) (0.3 * this.getWidth()),
                (int) (0.1 * this.getHeight())
        );

        this.add(text);
        this.add(ip);
        this.add(checkIPButton);
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

        if (isCorrect) {
            graphics2D.setColor(Color.GREEN);
        } else {
            graphics2D.setColor(Color.RED);
        }

        graphics2D.drawRoundRect((int) (ip.getX() * 0.55), (int) (ip.getY() * 0.93), (int) (ip.getWidth() / 0.9), (int) (ip.getHeight() / 0.8), 30, 30);
    }

    public String getIp() {
        return ip.getText();
    }
}
