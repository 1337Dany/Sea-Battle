import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private final JButton host = new JButton("Host");
    private final JButton connect = new JButton("Connect");
    private final JButton exit = new JButton("Exit");

    private boolean isConnectionPanelShown = false;

    MainMenu() {
        SwingUtilities.invokeLater(() -> {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setExtendedState(MAXIMIZED_BOTH);
            setUndecorated(true);
            setLayout(null);
            new SettingsSetter(this);

            mainMenu();
        });
    }

    private void mainMenu() {

        JPanel menuPanel = new JPanel();
        drawMenu(menuPanel);
        ConnectionMenu connectionMenu = new ConnectionMenu(menuPanel);
        SettingsSetter.setParametersToObjects(menuPanel);

        host.addActionListener(e -> {
            remove(menuPanel);
            new GameManager(new SeaBattleServer(), this, menuPanel);
        });

        connect.addActionListener(e -> {
            if (!isConnectionPanelShown) {
                menuPanel.add(connectionMenu);
                repaint();
                isConnectionPanelShown = true;
            }

            if (connectionMenu.isCorrect()) {
                remove(menuPanel);
                revalidate();
                new GameManager(new SeaBattleClient(), this, menuPanel);
            }
        });

        exit.addActionListener(e -> System.exit(0));
    }

    private void drawMenu(JPanel menuPanel) {
        menuPanel.setLayout(null);
        menuPanel.setBackground(Color.DARK_GRAY);

        menuPanel.setBounds(0,
                0,
                this.getWidth(),
                this.getHeight()
        );


        JLabel logo = new JLabel("SEA BATTLE");
        logo.setFont(new Font(logo.getFont().getFontName(), Font.PLAIN, 100));


        logo.setBounds(
                0,
                (int) (0.1 * this.getHeight()),
                this.getWidth(),
                (int) (0.1 * this.getHeight())
        );
        logo.setForeground(Color.ORANGE);
        logo.setVerticalAlignment(SwingConstants.CENTER);
        logo.setHorizontalAlignment(SwingConstants.CENTER);


        host.setFont(new Font(logo.getFont().getFontName(), Font.PLAIN, 40));
        host.setBounds(
                this.getWidth() / 2 - (int) (0.3 * this.getWidth()) / 2,
                this.getHeight() / 2 - (int) (0.1 * this.getHeight()),
                (int) (0.3 * this.getWidth()), (int) (0.1 * this.getHeight()));

        connect.setFont(new Font(logo.getFont().getFontName(), Font.PLAIN, 40));
        connect.setBounds(
                host.getX(),
                host.getY() + host.getHeight() + (int) (0.05 * this.getHeight()),
                host.getWidth(), host.getHeight());

        exit.setFont(new Font(logo.getFont().getFontName(), Font.PLAIN, 40));
        exit.setBounds(
                connect.getX(),
                connect.getY() + connect.getHeight() + (int) (0.05 * this.getHeight()),
                connect.getWidth(), connect.getHeight());

        menuPanel.add(logo);
        menuPanel.add(host);
        menuPanel.add(connect);
        menuPanel.add(exit);

        this.add(menuPanel);
    }
}
