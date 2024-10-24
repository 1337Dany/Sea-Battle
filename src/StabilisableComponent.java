import java.awt.*;

public class StabilisableComponent {
    public double getPositioningValueX() {
        return positioningValueX;
    }

    public double getPositioningValueY() {
        return positioningValueY;
    }

    private final float positioningValueX;

    private final float positioningValueY;
    private final float sizeValueX;
    private final float sizeValueY;
    private final float fontSize;
    private final Component component;




    public Component getComponent() {
        return component;
    }

    public float getSizeValueX() {
        return sizeValueX;
    }

    public float getSizeValueY() {
        return sizeValueY;
    }

    StabilisableComponent(Component component) {
        this.component = component;
        positioningValueX = (float) component.getX() / Toolkit.getDefaultToolkit().getScreenSize().width;
        positioningValueY = (float) component.getY() / Toolkit.getDefaultToolkit().getScreenSize().height;
        sizeValueX = (float) component.getWidth() / Toolkit.getDefaultToolkit().getScreenSize().width;
        sizeValueY = (float) component.getHeight() / Toolkit.getDefaultToolkit().getScreenSize().height;
        fontSize = (float) component.getFont().getSize()  / (Math.max(Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height));
    }

    public float getFont() {
        return fontSize;
    }
}
