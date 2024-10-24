import javax.swing.*;
import java.awt.*;

public class ConnectionMenu extends JPanel {
    JTextArea ip = new JTextArea();
    JButton connectButton = new JButton("Connect");
    JPanel menuPanel;

    boolean ipCorrect = false;

    ConnectionMenu(JPanel menuPanel) {
        this.menuPanel = menuPanel;

        drawPanel();


        connectButton.addActionListener(e -> {

        });


    }

    private void connectToServer() {

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
                this.getWidth() / 2 - (int) (text.getWidth()*0.8) / 2,
                text.getY() + text.getHeight(),
                (int) (text.getWidth()*0.8),
                text.getHeight()
        );

        connectButton.setBounds(
                this.getWidth() / 2 - (int) (0.3 * this.getWidth()) / 2,
                this.getHeight() - (int) (0.2 * this.getHeight()),
                (int) (0.3 * this.getWidth()),
                (int) (0.1 * this.getHeight())
        );

        this.add(text);
        this.add(ip);
        this.add(connectButton);

        FrameScalability.updateComponents(this);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        int arc = 100;

        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), arc, arc);

        graphics2D.setStroke(new BasicStroke(10));

        graphics2D.setColor(Color.ORANGE);
        graphics2D.drawRoundRect(0, 0, this.getWidth(), this.getHeight(), arc, arc);

        graphics2D.setColor(Color.ORANGE);
        graphics2D.drawRoundRect((int) (ip.getX() * 0.55), (int) (ip.getY() * 0.93), (int) (ip.getWidth() / 0.9), (int) (ip.getHeight() / 0.8),30,30);
    }
}
