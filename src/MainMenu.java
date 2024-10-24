import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private final JButton start = new JButton("Start new game");

    private final JButton exit = new JButton("Exit");

    MainMenu() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        this.setVisible(true);

        this.setLayout(new BorderLayout());

        Resources resources = Resources.GAME_ICON;
        this.setIconImage(resources.getImage());

        mainMenu();
    }

    private void mainMenu() {
        new FrameScalability(this);
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBackground(Color.DARK_GRAY);


        JLabel logo = new JLabel("SEA BATTLE");
        logo.setFont(new Font(logo.getFont().getFontName(), Font.PLAIN, 60));
        System.out.println(logo.getFont().getSize());


        logo.setBounds(
                this.getWidth() / 2 - (int) (0.3 * this.getWidth()) / 2,
                (int) (0.1 * this.getHeight()),
                (int) (0.3 * this.getWidth()),
                (int) (0.1 * this.getHeight())
        );
        logo.setForeground(Color.ORANGE);
        logo.setVerticalAlignment(SwingConstants.CENTER);
        logo.setHorizontalAlignment(SwingConstants.CENTER);


        start.setBounds(
                this.getWidth() / 2 - (int) (0.3 * this.getWidth()) / 2,
                this.getHeight() / 2 - (int) (0.1 * this.getHeight()),
                (int) (0.3 * this.getWidth()), (int) (0.1 * this.getHeight()));

        exit.setBounds(
                this.getWidth() / 2 - (int) (0.3 * this.getWidth()) / 2,
                this.getHeight() / 2 + (int) (0.1 * this.getHeight()),
                (int) (0.3 * this.getWidth()), (int) (0.1 * this.getHeight()));

        menuPanel.add(logo);
        menuPanel.add(start);
        menuPanel.add(exit);


        this.add(menuPanel, BorderLayout.CENTER);

        menuPanel.setVisible(true);

        FrameScalability.updateComponents(menuPanel);

        start.addActionListener(e -> {
            this.remove(menuPanel);
            this.add(new Game(),BorderLayout.CENTER);
        });

        exit.addActionListener(e -> System.exit(0));
    }

}
