import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlaceShips extends JPanel {
    private final GameManager gameManager;

    private final ArrayList<Integer> navy = new ArrayList<>();
    private final JLabel linkors = new JLabel();
    private final JLabel cruisers = new JLabel();
    private final JLabel destroyer = new JLabel();
    private final JLabel submarines = new JLabel();
    private final ArrayList<JLabel> labels = new ArrayList<>();

    PlaceShips(GameManager gameManager) {
        this.gameManager = gameManager;
        createNavy();
    }

    private void createNavy() {
        navy.add(1);  // linkors
        navy.add(2);  // cruiser
        navy.add(3);  // destroyer
        navy.add(4);  // submarines
    }

    public int countShips() {
        int result = 0;
        for (Integer integer : navy) {
            result += integer;
        }
        return result;
    }


    public void drawPanel() {

        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);

        JLabel placeShipsLogo = new JLabel("Tap to game field to place ship");
        placeShipsLogo.setFont(placeShipsLogo.getFont().deriveFont(30.0f));
        placeShipsLogo.setForeground(Color.ORANGE);
        placeShipsLogo.setBackground(Color.DARK_GRAY);
        placeShipsLogo.setVerticalAlignment(SwingConstants.CENTER);
        placeShipsLogo.setHorizontalAlignment(SwingConstants.CENTER);

        placeShipsLogo.setBounds(
                0,
                10,
                this.getWidth(),
                50
        );
        labels.add(linkors);
        labels.add(cruisers);
        labels.add(destroyer);
        labels.add(submarines);
        drawShips();

        this.add(placeShipsLogo);
        gameManager.getWindow().add(this);
    }

    private void drawShips() {
        for (int i = 0; i < labels.size(); i++) {
            labels.get(i).setFont(labels.get(i).getFont().deriveFont(30.0f));
            labels.get(i).setForeground(Color.ORANGE);
            labels.get(i).setBackground(Color.WHITE);
            labels.get(i).setVerticalAlignment(SwingConstants.CENTER);
            labels.get(i).setHorizontalAlignment(SwingConstants.CENTER);

            labels.get(i).setBounds(
                    60,
                    (this.getHeight() / 5) * (i + 1),
                    (int) (this.getWidth() / 1.2),
                    (int) (this.getHeight() * 0.05)
            );

            this.add(labels.get(i));

        }
        revalidateLables();
    }

    public void revalidateLables() {
        linkors.setText("Linkors: " + navy.get(0));
        cruisers.setText("Cruisers: " + navy.get(1));
        destroyer.setText("Destroyer: " + navy.get(2));
        submarines.setText("Submarines: " + navy.get(3));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setColor(Color.WHITE);
        int strokeWidth = 10;
        int arc = 100;
        graphics2D.fillRoundRect(strokeWidth, 10,
                this.getWidth() - strokeWidth * 2, this.getHeight() - strokeWidth * 2,
                arc, arc);

        graphics2D.setStroke(new BasicStroke(strokeWidth));

        graphics2D.setColor(Color.ORANGE);
        graphics2D.drawRoundRect(
                strokeWidth, 10,
                this.getWidth() - strokeWidth * 2, this.getHeight() - strokeWidth * 2,
                arc, arc);
    }

    public ArrayList<Integer> getNavy() {
        return navy;
    }
}
