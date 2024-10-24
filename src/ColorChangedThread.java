import javax.swing.*;
import java.awt.*;

public class ColorChangedThread extends Thread {
    boolean ipCorrect = false;
    JTextArea ip;
    JPanel connectMenu;

    public ColorChangedThread(JTextArea ip, JPanel connectMenu) {
        this.ip = ip;
        this.connectMenu = connectMenu;
    }

    @Override
    public void run() {
        try {
            while (true) {

                ipCorrect = ip.getText().equals("127");
                connectMenu.repaint();

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
