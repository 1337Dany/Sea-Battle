import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlaceShips extends JPanel {
    GameManager gameManager;

    private static final ArrayList<Integer> navy = new ArrayList<>();

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

        this.add(placeShipsLogo);
        gameManager.getWindow().add(this);
    }

    public void drawShips(){
        for (int i = 0; i < navy.size(); i++) {
            JLabel ship = new JLabel();
            ship.setFont(ship.getFont().deriveFont(30.0f));
            ship.setForeground(Color.ORANGE);
            ship.setBackground(Color.DARK_GRAY);
            ship.setVerticalAlignment(SwingConstants.CENTER);
            ship.setHorizontalAlignment(SwingConstants.CENTER);

            switch (i) {
                case 0 -> ship.setText("Linkors: " + navy.get(i));
                case 1 -> ship.setText("Cruisers: " + navy.get(i));
                case 2 -> ship.setText("Destroyer: " + navy.get(i));
                case 3 -> ship.setText("Submarines: " + navy.get(i));
            }

            ship.setBounds(
                    60,
                    (int) (this.getHeight() / 10) * i,
                    (int) (this.getWidth()/1.2),
                    (int) (this.getHeight()/1.2)
            );
            this.add(ship);
        }
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

    public static ArrayList<Integer> getNavy() {
        return navy;
    }
}
