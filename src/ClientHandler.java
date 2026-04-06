import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private List<ClientHandler> clients;
    private String username;

    public ClientHandler(Socket socket, List<ClientHandler> clients) {
        this.socket = socket;
        this.clients = clients;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // First message = username
            this.username = in.readLine();
            broadcast("🔵 " + username + " joined the chat");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String message;

        try {
            while ((message = in.readLine()) != null) {
                broadcast(username + ": " + message);
            }
        } catch (Exception e) {
            System.out.println(username + " disconnected");
        } finally {
            try {
                socket.close();
                clients.remove(this);
                broadcast("🔴 " + username + " left the chat");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.out.println(message);
        }
    }
}