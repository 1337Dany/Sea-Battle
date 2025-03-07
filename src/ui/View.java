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


    public View() {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(FRAME_SIZE);
            setLayout(null);

            new SettingsSetter(this);
    }

    public void start(){
        Menu menu = new Menu(this);
        this.add(menu);

        SettingsSetter.setParametersToObjects(this);
    }

    @Override
    public Dimension getFrameSize() {
        return FRAME_SIZE;
    }
}
