import javax.swing.*;
import java.awt.*;

public class SettingsSetter {

    public static Font font;

    public SettingsSetter(MainMenu window) {

        try {
            window.setIconImage(new ImageIcon("src/Resources/Images/seaBattleIcon.png").getImage());
            font = Font.createFont(Font.TRUETYPE_FONT,
                    new java.io.File("src/Resources/Fonts/Montserrat-BoldItalic.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setParametersToObjects(window);
    }

    public static void setParametersToObjects(Component component) {
        if(!(component.getFont() == null)){
            component.setFont(font.deriveFont((float) component.getFont().getSize()));
        }
        component.setVisible(true);
        for (Component child : ((Container) component).getComponents()) {
            setParametersToObjects(child);
        }
    }
}
