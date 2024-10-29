import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class EnemyField extends JPanel implements Runnable {
    JFrame window;
    private final ArrayList<Point> shipLocations = new ArrayList<>();
    private Point projection;
    private final int borderSize = 40;
    private final int cellSize;

    EnemyField(JFrame window) {
        this.window = window;
        this.setBounds(0, 0, 650, 650);
        cellSize = ((this.getWidth() - (borderSize * 2)) / 10);
        this.setBackground(Color.DARK_GRAY);
        window.add(this);
        this.setVisible(false);
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int adjX = e.getX() - borderSize;
                int adjY = e.getY() - borderSize;

                int col = adjX / cellSize;
                int row = adjY / cellSize;

                if (col >= 0 && col < 10 && row >= 0 && row < 10) {
                    projection = new Point(col,row);
                    repaint();
                }else{
                    projection = null;
                    repaint();
                }
            }
        });
    }

    private boolean placable(){
        return true;
    }


    @Override
    public void run() {
        while (true) {

        }
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

                for (int i = 0; i < shipLocations.size(); i++) {
                    if (shipLocations.get(i) != null && shipLocations.get(i).x == col && shipLocations.get(i).y == row) {
                        graphics2D.setColor(Color.BLUE); // Цвет подсветки
                        graphics2D.fillRect(x, y, cellSize, cellSize);
                    }
                }

                if (projection != null && projection.x == col && projection.y == row) {
                    if (true) {
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
}