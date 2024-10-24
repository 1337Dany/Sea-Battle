import java.io.*;
import java.net.*;

public class SeaBattleServer{

     SeaBattleServer(){

    }
    public static void main(String[] args) {
        int port = 12345; // Порт для прослушивания

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту " + port);

            // Ожидание подключения клиента
            Socket clientSocket = serverSocket.accept();
            System.out.println("Клиент подключился: " + clientSocket.getInetAddress());

            // Потоки для приема и отправки данных
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Чтение сообщения от клиента
            String receivedMessage = in.readLine();
            System.out.println("Сообщение от клиента: " + receivedMessage);

            // Ответ клиенту
            String serverMessage = "Привет от сервера через Wi-Fi!";
            out.println(serverMessage);

            // Закрытие соединения
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}