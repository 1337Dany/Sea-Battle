import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class GameField extends JPanel {
    JFrame window;
    PlaceShips placeShips;
    private final int borderSize = 40;
    private int cellSize;
    private ArrayList<Point> shipLocations = new ArrayList<>();
    private int shipToLand = 0;
    private ArrayList<Point> projections = new ArrayList<>();

    GameField(PlaceShips placeShips, JFrame window) {
        this.placeShips = placeShips;
        this.window = window;
        this.setLayout(null);
        this.setBackground(Color.DARK_GRAY);
        drawField();
    }

    public void drawField() {
        window.add(this);
        this.setBounds(0, 0, 650, 650);
        cellSize = ((this.getWidth() - (borderSize * 2)) / 10);
        revalidate();
    }

    public void placeShips() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Проверяем, что клик попал в границы сетки
                int adjX = e.getX() - borderSize;
                int adjY = e.getY() - borderSize;

                int col = adjX / cellSize;
                int row = adjY / cellSize;

                if (PlaceShips.getNavy().get(shipToLand) > 0) {
                    switch (shipToLand) {
                        case 0 -> {
                            shipLocations.add(new Point(col, row));
                            shipLocations.add(new Point(col, row - 1));
                            shipLocations.add(new Point(col, row - 2));
                            shipLocations.add(new Point(col, row - 3));
                        }
                        case 1 -> {
                            shipLocations.add(new Point(col, row));
                            shipLocations.add(new Point(col, row - 1));
                            shipLocations.add(new Point(col, row - 2));
                        }
                        case 2 -> {
                            shipLocations.add(new Point(col, row));
                            shipLocations.add(new Point(col, row - 1));
                        }
                        case 3 -> shipLocations.add(new Point(col, row));
                    }
                    int temp = PlaceShips.getNavy().get(shipToLand);
                    PlaceShips.getNavy().set(shipToLand, temp - 1);

                    if (PlaceShips.getNavy().get(shipToLand) == 0) {
                        shipToLand++;
                    }
                }
                placeShips.revalidateLables();
            }
        };
        addMouseListener(mouseAdapter);

        MouseMotionAdapter mouseMotionAdapter = new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int adjX = e.getX() - borderSize;
                int adjY = e.getY() - borderSize;

                int col = adjX / cellSize;
                int row = adjY / cellSize;

                if (col >= 0 && col < 10 && row >= 0 && row < 10) {
                    projections.clear();
                    switch (shipToLand) {
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
                        case 3 -> {
                            projections.add(new Point(col, row));
                        }
                    }


                    repaint(); // Перерисовываем проекцию под мышью
                } else {
                    projections.clear(); // Если вне сетки, убираем проекцию
                    repaint();
                }
            }
        };
        addMouseMotionListener(mouseMotionAdapter);

        new Thread(() -> {
            while (true) {
                if(placeShips.countShips() == 0){
                    removeMouseListener(mouseAdapter);
                    removeMouseMotionListener(mouseMotionAdapter);
                    this.invalidate();
                    break;
                }
                try {
                    // Если есть выбранная клетка, вызываем перерисовку в EDT

                    SwingUtilities.invokeLater(this::repaint);

                    // Пауза, чтобы предотвратить перегрузку потока
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

        }).start();
    }

    private int countShips() {
        int result = 0;
        for (int i = 0; i < PlaceShips.getNavy().size(); i++) {
            result += PlaceShips.getNavy().get(i);
        }
        return result;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;


        graphics2D.setColor(Color.DARK_GRAY);
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

                for (int i = 0; i < shipLocations.size(); i++) {
                    if (shipLocations.get(i) != null && shipLocations.get(i).x == col && shipLocations.get(i).y == row) {
                        graphics2D.setColor(Color.BLUE); // Цвет подсветки
                        graphics2D.fillRect(x, y, cellSize, cellSize);
                    }
                }
                for (int i = 0; i < projections.size(); i++) {
                    if (projections.get(i) != null && projections.get(i).x == col && projections.get(i).y == row) {
                        graphics2D.setColor(new Color(0, 0, 255, 128)); // Прозрачный цвет для проекции
                        graphics2D.fillRect(x, y, cellSize, cellSize);
                    }
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
}
