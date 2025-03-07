package ui;

import domain.PresenterContract;
import domain.SettingsSetter;
import ui.menu.Menu;
import ui.menu.MenuContract;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame implements MenuContract {
    private final Dimension FRAME_SIZE = new Dimension(1500, 800);

    private PresenterContract presenterContract;
    private Menu menu = new Menu(this);


    public View() {
        SwingUtilities.invokeLater(() -> {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(FRAME_SIZE);
            setLayout(null);

            this.add(menu);

            new SettingsSetter(this);
        });
    }

    @Override
    public Dimension getFrameSize() {
        return FRAME_SIZE;
    }
}
