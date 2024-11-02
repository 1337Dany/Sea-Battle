import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class GameField extends JPanel {
    private final GameManager gameManager;
    private final PlaceShips placeShips;

    private final ArrayList<Point> shipLocations = new ArrayList<>();
    private final ArrayList<Point> projections = new ArrayList<>();

    GameField(PlaceShips placeShips, GameManager gameManager) {
        this.gameManager = gameManager;
        this.placeShips = placeShips;
        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);
        drawField();
    }

    public void drawField() {
        gameManager.getWindow().add(this);
        this.setBounds(0, 0, 650, 650);
        revalidate();
    }

    public void placeShips() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Проверяем, что клик попал в границы сетки
                int adjX = e.getX() - gameManager.jniLogicManager.borderSize();
                int adjY = e.getY() - gameManager.jniLogicManager.borderSize();

                int col = adjX / gameManager.jniLogicManager.cellSize();
                int row = adjY / gameManager.jniLogicManager.cellSize();

                ArrayList<Point> ship = new ArrayList<>();

                if (gameManager.jniLogicManager.getNavy(gameManager.jniLogicManager.getShipToLand()) > 0) {
                    if (gameManager.jniLogicManager.isRotated()) {
                        switch (gameManager.jniLogicManager.getShipToLand()) {
                            case 0 -> {
                                ship.add(new Point(col, row));
                                ship.add(new Point(col, row - 1));
                                ship.add(new Point(col, row - 2));
                                ship.add(new Point(col, row - 3));
                            }
                            case 1 -> {
                                ship.add(new Point(col, row));
                                ship.add(new Point(col, row - 1));
                                ship.add(new Point(col, row - 2));
                            }
                            case 2 -> {
                                ship.add(new Point(col, row));
                                ship.add(new Point(col, row - 1));
                            }
                            case 3 -> ship.add(new Point(col, row));
                        }
                    } else {
                        switch (gameManager.jniLogicManager.getShipToLand()) {
                            case 0 -> {
                                ship.add(new Point(col, row));
                                ship.add(new Point(col - 1, row));
                                ship.add(new Point(col - 2, row));
                                ship.add(new Point(col - 3, row));
                            }
                            case 1 -> {
                                ship.add(new Point(col, row));
                                ship.add(new Point(col - 1, row));
                                ship.add(new Point(col - 2, row));
                            }
                            case 2 -> {
                                ship.add(new Point(col, row));
                                ship.add(new Point(col - 1, row));
                            }
                            case 3 -> ship.add(new Point(col, row));
                        }
                    }

                    if (positionValid(ship)) {
                        shipLocations.addAll(ship);
                        gameManager.jniLogicManager.setNavy(gameManager.jniLogicManager.getShipToLand(), gameManager.jniLogicManager.getNavy(gameManager.jniLogicManager.getShipToLand()) - 1);
                        if (gameManager.jniLogicManager.getNavy(gameManager.jniLogicManager.getShipToLand()) == 0) {
                            gameManager.jniLogicManager.setShipToLand(gameManager.jniLogicManager.getShipToLand() + 1);
                        }
                    }
                }
                placeShips.revalidateLables();
            }
        };
        addMouseListener(mouseAdapter);

        MouseMotionAdapter mouseMotionAdapter = new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int adjX = e.getX() - gameManager.jniLogicManager.borderSize();
                int adjY = e.getY() - gameManager.jniLogicManager.borderSize();

                int col = adjX / gameManager.jniLogicManager.cellSize();
                int row = adjY / gameManager.jniLogicManager.cellSize();

                if (col >= 0 && col < 10 && row >= 0 && row < 10) {
                    projections.clear();
                    if (gameManager.jniLogicManager.isRotated()) {
                        switch (gameManager.jniLogicManager.getShipToLand()) {
                            case 0 -> {
                                projections.add(new Point(col, row));
                                projections.add(new Point(col, row - 1));
                                projections.add(new Point(col, row - 2));
                                projections.add(new Point(col, row - 3));
                            }
                            case 1 -> {
                                projections.add(new Point(col, row));
                                projections.add(new Point(col, row - 1));
                                projections.add(new Point(col, row - 2));
                            }
                            case 2 -> {
                                projections.add(new Point(col, row));
                                projections.add(new Point(col, row - 1));
                            }
                            case 3 -> projections.add(new Point(col, row));
                        }
                    } else {
                        switch (gameManager.jniLogicManager.getShipToLand()) {
                            case 0 -> {
                                projections.add(new Point(col, row));
                                projections.add(new Point(col - 1, row));
                                projections.add(new Point(col - 2, row));
                                projections.add(new Point(col - 3, row));
                            }
                            case 1 -> {
                                projections.add(new Point(col, row));
                                projections.add(new Point(col - 1, row));
                                projections.add(new Point(col - 2, row));
                            }
                            case 2 -> {
                                projections.add(new Point(col, row));
                                projections.add(new Point(col - 1, row));
                            }
                            case 3 -> projections.add(new Point(col, row));
                        }
                    }
                    gameManager.jniLogicManager.setPlacable(positionValid(projections));

                    repaint();
                } else {
                    projections.clear();
                    repaint();
                }
            }
        };
        addMouseMotionListener(mouseMotionAdapter);

        new Thread(() -> {
            while (true) {
                if (placeShips.countShips() == 0) {
                    removeMouseListener(mouseAdapter);
                    removeMouseMotionListener(mouseMotionAdapter);
                    this.invalidate();
                    break;
                }
                try {

                    SwingUtilities.invokeLater(this::repaint);

                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

        }).start();
    }

    private boolean positionValid(ArrayList<Point> newShip) {
        for (Point point : newShip) {
            if (point.x < 0 || point.x >= 10 || point.y < 0 || point.y >= 10) {
                return false;
            }
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int checkCol = point.x + i;
                    int checkRow = point.y + j;

                    // Check if selected cell in a field
                    if (checkCol >= 0 && checkCol < 10 && checkRow >= 0 && checkRow < 10) {
                        // if in this field exist a ship, we return false
                        if (shipLocations.contains(new Point(checkCol, checkRow))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;


        graphics2D.setColor(Color.DARK_GRAY);
        graphics2D.fillRect(
                gameManager.jniLogicManager.borderSize(),
                gameManager.jniLogicManager.borderSize(),
                this.getWidth() - gameManager.jniLogicManager.borderSize() * 2,
                this.getHeight() - gameManager.jniLogicManager.borderSize() * 2);

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(getFont().deriveFont(20.0f));
        for (int i = 1; i <= 10; i++) {
            graphics2D.drawString(String.valueOf((i - 1)),
                    ((this.getWidth() - gameManager.jniLogicManager.borderSize()) / 11) * i + graphics2D.getFontMetrics().stringWidth(String.valueOf(i)),
                    18);
            char ch = (char) (i + 64);
            graphics2D.drawString(String.valueOf(ch),
                    4,
                    ((this.getHeight() - gameManager.jniLogicManager.borderSize() / 2) / 11) * i + gameManager.jniLogicManager.borderSize() / 2);
        }


        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                int x = gameManager.jniLogicManager.borderSize() + col * gameManager.jniLogicManager.cellSize();
                int y = gameManager.jniLogicManager.borderSize() + row * gameManager.jniLogicManager.cellSize();

                for (Point shipLocation : shipLocations) {
                    if (shipLocation != null && shipLocation.x == col && shipLocation.y == row) {
                        graphics2D.setColor(Color.BLUE); // Цвет подсветки
                        graphics2D.fillRect(x, y, gameManager.jniLogicManager.cellSize(), gameManager.jniLogicManager.cellSize());
                    }
                }
                for (Point projection : projections) {
                    if (projection != null && projection.x == col && projection.y == row) {
                        if (gameManager.jniLogicManager.isPlacable()) {
                            graphics2D.setColor(new Color(0, 0, 255, 128));
                        } else {
                            graphics2D.setColor(new Color(255, 0, 0, 128));
                        }
                        graphics2D.fillRect(x, y, gameManager.jniLogicManager.cellSize(), gameManager.jniLogicManager.cellSize());
                    }
                }
            }
        }


        graphics2D.setColor(Color.ORANGE);
        for (int i = 1; i <= 9; i++) {
            graphics2D.fillRect(
                    ((this.getWidth() - gameManager.jniLogicManager.borderSize() * 2) / 10 * i) + gameManager.jniLogicManager.borderSize(),
                    gameManager.jniLogicManager.borderSize(),
                    5,
                    this.getHeight() - gameManager.jniLogicManager.borderSize() * 2);
            graphics.fillRect(
                    gameManager.jniLogicManager.borderSize(),
                    ((this.getHeight() - (gameManager.jniLogicManager.borderSize() * 2)) / 10 * i) + gameManager.jniLogicManager.borderSize(),
                    this.getWidth() - gameManager.jniLogicManager.borderSize() * 2,
                    5);
        }

    }

    public ArrayList<Point> getShipLocations() {
        return shipLocations;
    }
}