import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class GameField extends JPanel {
    private final GameManager gameManager;
    private final PlaceShips placeShips;

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

                System.out.println(gameManager.jniLogicManager.getShipSize());

                if (gameManager.jniLogicManager.getNavy(gameManager.jniLogicManager.getShipToLand()) > 0) {

                    System.out.println(gameManager.jniLogicManager.positionValid());

                    if (gameManager.jniLogicManager.positionValid()) {
                        gameManager.jniLogicManager.addAll();
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
                    gameManager.jniLogicManager.addShip(0,col,row);
                    gameManager.jniLogicManager.addShip(1,col,row);
                    gameManager.jniLogicManager.addShip(2,col,row);
                    gameManager.jniLogicManager.addShip(3,col,row);
                    gameManager.jniLogicManager.setShipSize(0);
                    if (gameManager.jniLogicManager.isRotated()) {
                        switch (gameManager.jniLogicManager.getShipToLand()) {
                            case 0 -> {
                                gameManager.jniLogicManager.addShip(0,col, row);
                                gameManager.jniLogicManager.addShip(1,col, row - 1);
                                gameManager.jniLogicManager.addShip(2,col, row - 2);
                                gameManager.jniLogicManager.addShip(3,col, row - 3);
                            }
                            case 1 -> {
                                gameManager.jniLogicManager.addShip(0,col, row);
                                gameManager.jniLogicManager.addShip(1,col, row - 1);
                                gameManager.jniLogicManager.addShip(2,col, row - 2);
                            }
                            case 2 -> {
                                gameManager.jniLogicManager.addShip(0,col, row);
                                gameManager.jniLogicManager.addShip(1,col, row - 1);
                            }
                            case 3 -> {
                                gameManager.jniLogicManager.addShip(0,col, row);
                            }
                        }
                    } else {
                        switch (gameManager.jniLogicManager.getShipToLand()) {
                            case 0 -> {
                                gameManager.jniLogicManager.addShip(0,col, row);
                                gameManager.jniLogicManager.addShip(1,col - 1, row);
                                gameManager.jniLogicManager.addShip(2,col - 2, row);
                                gameManager.jniLogicManager.addShip(3,col - 3, row);
                                gameManager.jniLogicManager.setShipSize(4);
                            }
                            case 1 -> {
                                gameManager.jniLogicManager.addShip(0,col, row);
                                gameManager.jniLogicManager.addShip(1,col - 1, row);
                                gameManager.jniLogicManager.addShip(2,col - 2, row);
                                gameManager.jniLogicManager.setShipSize(3);
                            }
                            case 2 -> {
                                gameManager.jniLogicManager.addShip(0,col, row);
                                gameManager.jniLogicManager.addShip(1,col - 1, row);
                                gameManager.jniLogicManager.setShipSize(2);
                            }
                            case 3 -> {
                                gameManager.jniLogicManager.addShip(0,col, row);
                                gameManager.jniLogicManager.setShipSize(1);
                            }
                        }
                    }

                    repaint();
                } else {
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
                    revalidate();
                    repaint();
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

                for (int i = 0; i < gameManager.jniLogicManager.getShipLocationsSize(); i++) {
                    if (gameManager.jniLogicManager.getShipLocationX(i) == col && gameManager.jniLogicManager.getShipLocationY(i) == row) {
                        graphics2D.setColor(Color.BLUE); // Цвет подсветки
                        graphics2D.fillRect(x, y, gameManager.jniLogicManager.cellSize(), gameManager.jniLogicManager.cellSize());
                    }else if(gameManager.jniLogicManager.isDead(i)){
                        graphics2D.setColor(Color.RED);
                        graphics2D.fillRect(x, y, gameManager.jniLogicManager.cellSize(), gameManager.jniLogicManager.cellSize());
                    }
                }
                for (int i = 0; i < gameManager.jniLogicManager.getShipSize(); i++) {
                    if (gameManager.jniLogicManager.getShipX(i) == col && gameManager.jniLogicManager.getShipY(i) == row) {
                        if (placeShips.countShips() == 0) {
                            graphics2D.setColor(new Color(0,0,0,0));
                        }else if (gameManager.jniLogicManager.positionValid()) {
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
}