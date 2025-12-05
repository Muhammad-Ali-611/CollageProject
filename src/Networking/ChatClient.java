package Networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to chat server!");

            // Thread to read messages from server
            Thread receiveThread = new Thread(new ReceiveMessages(socket));
            receiveThread.start();

            // Main thread sends messages to server
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            String message;
            while (true) {
                message = scanner.nextLine();
                writer.println(message);

                if (message.equalsIgnoreCase("/quit")) {
                    break;
                }
            }

            scanner.close();
            socket.close();
            System.out.println("Disconnected from server.");

        } catch (UnknownHostException e) {
            System.err.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    // Inner class to handle incoming messages
    static class ReceiveMessages implements Runnable {
        private Socket socket;
        private BufferedReader reader;

        public ReceiveMessages(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;

                while ((message = reader.readLine()) != null) {
                    System.out.println(message);
                }

            } catch (IOException e) {
                System.err.println("Connection closed.");
            } finally {
                try {
                    if (reader != null) reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
