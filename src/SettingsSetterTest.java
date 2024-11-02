import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class SettingsSetterTest {
    SettingsSetter settingsSetter = new SettingsSetter(new MainMenu());

    @Test
    public void areElementsVisible(){
        JPanel panel = new JPanel();
        SettingsSetter.setParametersToObjects(panel);
        assertTrue(panel.isVisible());
    }

    @Test
    public void areElementsHasSameFont(){
        JButton button = new JButton();
        JButton button1 = new JButton();
        button1.add(button);
        SettingsSetter.setParametersToObjects(button1);
        assertEquals(button.getFont(), button1.getFont());
    }

    @Test
    public void ignoringSomeCases(){
        JPanel panel1 = new JPanel();
        panel1.setVisible(false);
        SettingsSetter.ignoreSettingParametersToObjects(panel1);
        JPanel panel2 = new JPanel();
        panel1.add(panel2);
        assertNotEquals(panel1.isVisible(), panel2.isVisible());
    }

}