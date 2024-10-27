import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GameField extends JPanel {
    JFrame window;
    private final int borderSize = 40;
    private int cellSize;
    private Point cell = null;
    private Point projection = null;

    GameField(JFrame window) {
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
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Проверяем, что клик попал в границы сетки
                int adjX = e.getX() - borderSize;
                int adjY = e.getY() - borderSize;

                int col = adjX / cellSize;
                int row = adjY / cellSize;

                cell = new Point(col, row); // Сохраняем выбранную клетку
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int adjX = e.getX() - borderSize;
                int adjY = e.getY() - borderSize;

                int col = adjX / cellSize;
                int row = adjY / cellSize;

                if (col >= 0 && col < 10 && row >= 0 && row < 10) {
                    projection = new Point(col, row);
                    repaint(); // Перерисовываем проекцию под мышью
                } else {
                    projection = null; // Если вне сетки, убираем проекцию
                    repaint();
                }
                System.out.println(e.getX());
            }
        });

        new Thread(() -> {
            while (countShips() != 0) {
                try {
                    // Если есть выбранная клетка, вызываем перерисовку в EDT
                    if (cell != null) {

                        SwingUtilities.invokeLater(this::repaint);
                    }
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

                // Подсвечиваем выбранную клетку
                if (cell != null && cell.x == col && cell.y == row) {
                    graphics2D.setColor(Color.BLUE); // Цвет подсветки
                    graphics2D.fillRect(x, y, cellSize, cellSize);
                } else if (projection != null && projection.x == col && projection.y == row) {
                    graphics2D.setColor(new Color(0, 0, 255, 128)); // Прозрачный цвет для проекции
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
}
