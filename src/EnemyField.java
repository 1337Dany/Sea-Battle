import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class EnemyField extends JPanel {
    private final GameManager gameManager;

    private Point projection;


    EnemyField(GameManager gameManager) {
        this.gameManager = gameManager;
        this.setBounds(0, 0, 650, 650);
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
                int col = gameManager.jniLogicManager.calculateCol(e.getX());
                int row = gameManager.jniLogicManager.calculateCol(e.getY());

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
                int col = gameManager.jniLogicManager.calculateCol(e.getX());
                int row = gameManager.jniLogicManager.calculateCol(e.getY());

                if (col >= 0 && col < 10 && row >= 0 && row < 10) {
                    if (gameManager.isMyTurn()) {
                        if (placable(new Point(col, row))) {
                            gameManager.shootTo(col, row);
                        }
                    }
                }
            }
        });
    }

    private boolean placable(Point projection) {
        for (int i = 0; i < gameManager.jniLogicManager.getKilledShipsSize(); i++) {
                if (projection.x == gameManager.jniLogicManager.getKilledShipX(i) && projection.y == gameManager.jniLogicManager.getKilledShipY(i)) {
                    return false;
                }
        }
        for (int i = 0; i < gameManager.jniLogicManager.getOpenedLocationSize(); i++) {
                if (projection.x == gameManager.jniLogicManager.getOpenLocationX(i) && projection.y == gameManager.jniLogicManager.getOpenLocationY(i)) {
                    return false;
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

                for (int i = 0; i < gameManager.jniLogicManager.getKilledShipsSize(); i++) {
                    if (gameManager.jniLogicManager.getKilledShipX(i)== col && gameManager.jniLogicManager.getKilledShipY(i)== row) {
                        graphics2D.setColor(Color.BLUE);
                        graphics2D.fillRect(x, y, gameManager.jniLogicManager.cellSize(), gameManager.jniLogicManager.cellSize());
                    }
                }

                for (int i = 0; i < gameManager.jniLogicManager.getOpenedLocationSize(); i++) {
                    if (gameManager.jniLogicManager.getOpenLocationX(i) == col && gameManager.jniLogicManager.getOpenLocationY(i) == row) {
                        graphics2D.setColor(Color.RED);
                        graphics2D.fillRect(x, y, gameManager.jniLogicManager.cellSize(), gameManager.jniLogicManager.cellSize());
                    }
                }

                if (projection != null && projection.x == col && projection.y == row) {
                    if (placable(projection)) {
                        graphics2D.setColor(new Color(0, 255, 0, 128));
                    } else {
                        graphics2D.setColor(new Color(255, 0, 0, 128));
                    }
                    graphics2D.fillRect(x, y, gameManager.jniLogicManager.cellSize(), gameManager.jniLogicManager.cellSize());
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
}
