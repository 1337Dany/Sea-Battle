import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    SettingsSetter settingsSetter = new SettingsSetter(new MainMenu());
    SeaBattleServer testServer = new SeaBattleServer();
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    GameManager manager = new GameManager(testServer, frame, panel);
    @Test
    public void checkDoesLogicExecuted(){
        assertNotNull(manager.getJniLogicManager());
        assertTrue(manager.isMyTurn());
    }

    @Test
    public void testVisibilityOfEnemyDeskPlanner(){
        assertFalse(manager.getEnemyField().isVisible());
        assertEquals(10, manager.jniLogicManager.countShips());
    }

    @Test
    public void testDeskOpening(){
        assertTrue(manager.getGameField().isVisible());
    }

    @Test
    public void testChat(){

        String message = "The world!";
        manager.getInGameChat().sendMessage(message);
        JLabel jLabel = new JLabel();
        jLabel.setText(message);

        assertTrue(manager.getInGameChat().getLabelList().get(0).getText().equals(message));
    }

    @Test
    public void doesEverythingIsClosing(){
        manager.closeAll();

        assertTrue(panel.isVisible());
    }

    @Test
    public void checkTheSizeOfCell(){
        assertEquals(57, manager.jniLogicManager.cellSize());
    }

    @Test
    public void checkWorkOfLogs(){
        String message = "game is starting!";
        manager.addMessageToGameLogs(message);
        JLabel jLabel = new JLabel();
        jLabel.setText(message);

        assertEquals(manager.getGameLogs().getLabelList().get(0).getText(), message);
    }

}