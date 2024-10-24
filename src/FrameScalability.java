import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FrameScalability {
    private static File fontFile;
    private static final Set<StabilisableComponent> stabilizedComponents = new HashSet<>();

    FrameScalability(Component window) {

        fontFile = new File("src/Resources/Fonts/Montserrat-Bold.ttf");

        scale(window);
    }

    private static void scale(Component window) {
        window.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                for (StabilisableComponent stabilisableComponent : stabilizedComponents) {
                    stabilisableComponent.getComponent().setBounds(
                            (int) (stabilisableComponent.getPositioningValueX() * window.getWidth()),
                            (int) (stabilisableComponent.getPositioningValueY() * window.getHeight()),
                            (int) (stabilisableComponent.getSizeValueX() * window.getWidth()),
                            (int) (stabilisableComponent.getSizeValueY() * window.getHeight())
                    );
                    try {
                        stabilisableComponent.getComponent().setFont(Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(stabilisableComponent.getFont() * (Math.max(window.getWidth(),
                                window.getHeight())))
                        );
                    } catch (FontFormatException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println(stabilisableComponent.getComponent().getFont().getSize());
                }
            }
        });
        updateComponents(window);
    }

    public static void updateComponents(Component component) {
        //  There we're checking all the components in frame recursively by components and change the font size by deriving
        if (component instanceof JButton || component instanceof JLabel || component instanceof JPanel) {
            stabilizedComponents.add(new StabilisableComponent(component));
        }
        for (Component child : ((Container) component).getComponents()) {
            updateComponents(child);
        }
    }

}
