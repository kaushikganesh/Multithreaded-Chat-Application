import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 6000);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner sc = new Scanner(System.in);

            // Enter username
            System.out.print("Enter your name: ");
            String username = sc.nextLine();
            out.println(username);

            // Read messages
            Thread readThread = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            readThread.start();

            // Send messages
            while (true) {
                String message = sc.nextLine();
                out.println(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}