import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeaBattleClientTest {

    @Test
    public void checkConnectionToTheServer(){
        String serverAddress = "";
        assertEquals(SeaBattleClient.checkConnection(serverAddress), true);
    }


}