import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class SeaBattleServerTest {

    @Test
    public void checkTheConnection() throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket clientSocket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        String password = in.readLine();
        assertEquals(password, "UTP_12345");
        if(password.equals("UTP_12345")) out.println("accepted");
    }

}