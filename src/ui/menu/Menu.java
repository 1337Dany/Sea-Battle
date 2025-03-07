package ui.menu;

import domain.SettingsSetter;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {
    private final MenuContract contract;

    private final JButton host = new JButton("Host");
    private final JButton connect = new JButton("Connect");
    private final JButton exit = new JButton("Exit");

    ConnectionMenu connectionMenu = new ConnectionMenu();
    private boolean isConnectionPanelShown = false;

    public Menu(MenuContract contract) {
        this.contract = contract;

        configure();
        configureAdditionalPanels();
        open();
    }

    public void open() {

//        host.addActionListener(e -> {
//            remove(menuPanel);
//            new GameManager(new SeaBattleServer(), this, menuPanel);
//        });

        connect.addActionListener(e -> {
            if (!isConnectionPanelShown) {
                this.add(connectionMenu);
                repaint();
                isConnectionPanelShown = true;
            }

            if (connectionMenu.isCorrect()) {
                remove(this);
                revalidate();
                //new GameManager(new SeaBattleClient(), this, menuPanel);
            }
        });

        exit.addActionListener(e -> System.exit(0));
    }

    private void configure() {
        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);

        this.setBounds(0,
                0,
                contract.getFrameSize().width,
                contract.getFrameSize().height
        );

        JLabel logo = new JLabel("SEA BATTLE");
        logo.setFont(new Font(logo.getFont().getFontName(), Font.PLAIN, 100));

        logo.setBounds(
                0,
                (int) (0.1 * this.getHeight()),
                this.getWidth(),
                (int) (0.1 * this.getHeight())
        );
        System.out.println((int) (0.1 * this.getHeight()));
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

        this.add(logo);
        this.add(host);
        this.add(connect);
        this.add(exit);
    }

    private void configureAdditionalPanels() {
        connectionMenu.setBounds(
                this.getWidth() / 2 + (int) (0.2 * this.getWidth()),
                this.getHeight() / 2 - (int) (0.1 * this.getHeight()),
                (int) (0.25 * this.getWidth()),
                (int) (0.4 * this.getHeight())
        );

        connectionMenu.drawPanel();
    }
}
