import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class EnemyField extends JPanel {
    private final GameManager gameManager;

    private final ArrayList<Point> shipLocations = new ArrayList<>();

    private final ArrayList<Point> openedLocations = new ArrayList<>();

    private Point projection;

    private final int borderSize = 40;
    private final int cellSize;

    EnemyField(GameManager gameManager) {
        this.gameManager = gameManager;
        this.setBounds(0, 0, 650, 650);
        cellSize = ((this.getWidth() - (borderSize * 2)) / 10);
        SettingsSetter.ignoreSettingParametersToObjects(this);
        this.setBackground(Color.DARK_GRAY);
        gameManager.getWindow().add(this);
        this.setVisible(false);
        drawField();

    }

    private void drawField() {
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int adjX = e.getX() - borderSize;
                int adjY = e.getY() - borderSize;

                int col = adjX / cellSize;
                int row = adjY / cellSize;

                if (col >= 0 && col < 10 && row >= 0 && row < 10) {
                    projection = new Point(col, row);
                    repaint();
                } else {
                    projection = null;
                    repaint();
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int adjX = e.getX() - borderSize;
                int adjY = e.getY() - borderSize;

                int col = adjX / cellSize;
                int row = adjY / cellSize;

                if (col >= 0 && col < 10 && row >= 0 && row < 10) {
                    if (gameManager.isMyTurn()) {
                        gameManager.shootTo(col, row);
                    }
                }
            }
        });
    }

    private boolean placable(Point projection) {
        for (Point shipLocation : shipLocations) {
            if (shipLocation != null) {
                if (projection.x == shipLocation.x && projection.y == shipLocation.y) {
                    return false;
                }
            }
        }
        for (Point checkPoint : openedLocations) {
            if (checkPoint != null) {
                if (projection.x == checkPoint.x && projection.y == checkPoint.y) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;


        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(
                borderSize,
                borderSize,
                this.getWidth() - borderSize * 2,
                this.getHeight() - borderSize * 2);

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(getFont().deriveFont(20.0f));
        for (int i = 1; i <= 10; i++) {
            graphics2D.drawString(String.valueOf((i - 1)),
                    ((this.getWidth() - borderSize) / 11) * i + graphics2D.getFontMetrics().stringWidth(String.valueOf(i)),
                    18);
            char ch = (char) (i + 64);
            graphics2D.drawString(String.valueOf(ch),
                    4,
                    ((this.getHeight() - borderSize / 2) / 11) * i + borderSize / 2);
        }

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                int x = borderSize + col * cellSize;
                int y = borderSize + row * cellSize;

                for (Point shipLocation : shipLocations) {
                    if (shipLocation != null && shipLocation.x == col && shipLocation.y == row) {
                        graphics2D.setColor(Color.BLUE);
                        graphics2D.fillRect(x, y, cellSize, cellSize);
                    }
                }

                for (Point emptyLocation : openedLocations) {
                    if (emptyLocation != null && emptyLocation.x == col && emptyLocation.y == row) {
                        graphics2D.setColor(Color.RED);
                        graphics2D.fillRect(x, y, cellSize, cellSize);
                    }
                }

                if (projection != null && projection.x == col && projection.y == row) {
                    if (placable(projection)) {
                        graphics2D.setColor(new Color(0, 255, 0, 128));
                    } else {
                        graphics2D.setColor(new Color(255, 0, 0, 128));
                    }
                    graphics2D.fillRect(x, y, cellSize, cellSize);
                }

            }
        }

        graphics2D.setColor(Color.ORANGE);
        for (int i = 1; i <= 9; i++) {
            graphics2D.fillRect(
                    ((this.getWidth() - borderSize * 2) / 10 * i) + borderSize,
                    borderSize,
                    5,
                    this.getHeight() - borderSize * 2);
            graphics.fillRect(
                    borderSize,
                    ((this.getHeight() - (borderSize * 2)) / 10 * i) + borderSize,
                    this.getWidth() - borderSize * 2,
                    5);
        }

    }

    public ArrayList<Point> getShipLocations() {
        return shipLocations;
    }

    public ArrayList<Point> getOpenedLocations() {
        return openedLocations;
    }
}
