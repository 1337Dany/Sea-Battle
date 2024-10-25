import javax.swing.*;

public class Game extends JPanel {
    GameField myField = new GameField();
    GameField enemyProjection = new GameField();

    Game() {
        this.setLayout(null);
        this.add(myField);

    }
    private void history(){}
    private void connection(){}
    private void dataExchange(){}
    private void fieldPainting(){}
}
