package domain;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The SettingsSetter class is responsible for setting the font and visibility parameters
 * to the components of a given JFrame window. It also allows certain components to be
 * ignored from these settings.
 */
public class SettingsSetter {

    private static Font font;
    private static final ArrayList<Component> ignoredComponents = new ArrayList<>();

    /**
     * Constructs a SettingsSetter object and sets the icon image and font for the given window.
     *
     * @param window the JFrame window to which the settings will be applied
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public SettingsSetter(JFrame window) {
        try {
            window.setIconImage(new ImageIcon("src/Resources/images/seaBattleIcon.png").getImage());
            font = Font.createFont(Font.TRUETYPE_FONT,
                    new java.io.File("src/Resources/fonts/Montserrat-BoldItalic.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setParametersToObjects(window);
    }

    /**
     * Sets the font and visibility parameters to the given component and its children.
     *
     * @param component the component to which the settings will be applied
     */
    public static void setParametersToObjects(Component component) {
        if (!ignoredComponents.contains(component)) {
            if (!(component.getFont() == null)) {
                component.setFont(font.deriveFont((float) component.getFont().getSize()));
            }
            component.setVisible(true);
        }
        for (Component child : ((Container) component).getComponents()) {
            setParametersToObjects(child);
        }
    }

    /**
     * Adds a component to the list of components that will be ignored from the settings.
     *
     * @param component the component to be ignored
     */
    public static void ignoreSettingParametersToObjects(Component component) {
        ignoredComponents.add(component);
    }
}