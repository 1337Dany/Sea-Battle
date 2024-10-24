import javax.swing.*;
import java.awt.*;

public enum Resources {
    GAME_ICON(new ImageIcon("src/Resources/Images/seaBattleIcon.png")),
    MENU_BACKGROUND(new ImageIcon(("")));

    private final ImageIcon imageIcon;

    Resources(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }
    public Image getImage(){
        return imageIcon.getImage();
    }
}
