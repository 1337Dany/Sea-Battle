import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private final JButton host = new JButton("Host");
    private final JButton connect = new JButton("Connect");
    private final JButton exit = new JButton("Exit");

    MainMenu() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
        this.setBackground(Color.BLACK);
        new SettingsSetter(this);

        this.setLayout(new BorderLayout());

        mainMenu();
    }

    private void mainMenu() {
        JPanel menuPanel = new JPanel();
        drawMenu(menuPanel);

        host.addActionListener(e -> {
            Game game = new Game(new SeaBattleServer(), this);
            this.remove(menuPanel);
            revalidate();
        });

        connect.addActionListener(e -> {
            ConnectionMenu connectionMenu = new ConnectionMenu(menuPanel);
            menuPanel.add(connectionMenu, BorderLayout.EAST);

            if(ConnectionMenu.isCorrect){
                Game game = new Game(new SeaBattleClientOne(connectionMenu.getIp()),this);
                this.remove(menuPanel);
                revalidate();
            }

            repaint();
        });

        exit.addActionListener(e -> System.exit(0));
    }

    private void drawMenu(JPanel menuPanel) {
        menuPanel.setLayout(null);
        menuPanel.setBackground(Color.DARK_GRAY);


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

        menuPanel.setVisible(true);

        this.add(menuPanel, BorderLayout.CENTER);
    }

}
