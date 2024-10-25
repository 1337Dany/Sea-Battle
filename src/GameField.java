import javax.swing.*;
import java.awt.*;

public class GameField extends JPanel {
    public void drawField(){
        this.setLayout(null);
    }
    @Override
    protected void paintComponent(Graphics graphics){
        for (int i = 1; i <= 9; i++) {
            graphics.drawRect(this.getWidth()/10*i, 0, 5,this.getHeight());
            graphics.drawRect(0, this.getHeight()/10*i, this.getWidth(), 5);
        }
    }
}
