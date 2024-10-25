import javax.swing.*;

public class ConnectionThread extends Thread {
    boolean ipCorrect = false;
    JTextArea ip;
    JPanel connectMenu;

    public ConnectionThread(JTextArea ip, JPanel connectMenu) {
        this.ip = ip;
        this.connectMenu = connectMenu;
    }

    @Override
    public void run() {
        try {
            while (true) {

                ipCorrect = SeaBattleClientOne.checkConnection(ip.getText());
                System.out.println(ip.getText());
                connectMenu.repaint();

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
