import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6000);
            System.out.println("Server started... Waiting for clients");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler clientHandler = new ClientHandler(socket, clients);
                clients.add(clientHandler);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}