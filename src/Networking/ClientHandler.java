package Networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

public class ClientHandler implements Runnable{
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Set<ClientHandler> clientHandlers;
    private String clientName;

    public ClientHandler(Socket socket, Set<ClientHandler> clientHandlers) {
        this.socket = socket;
        this.clientHandlers = clientHandlers;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            // Get client name
            writer.println("Enter your name: ");
            clientName = reader.readLine();

            if (clientName == null || clientName.trim().isEmpty()) {
                clientName = "Anonymous";
            }

            System.out.println(clientName + " has joined the chat!");
            broadcastMessage("SERVER: " + clientName + " has joined the chat!");

            // Listen for messages
            String message;
            while ((message = reader.readLine()) != null) {
                if (message.equalsIgnoreCase("/quit")) {
                    break;
                }
                System.out.println(clientName + ": " + message);
                broadcastMessage(clientName + ": " + message);
            }

        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    private void broadcastMessage(String message) {
        for (ClientHandler client : clientHandlers) {
            if (client != this) {
                client.writer.println(message);
            }
        }
    }

    private void disconnect() {
        clientHandlers.remove(this);

        if (clientName != null) {
            System.out.println(clientName + " has left the chat.");
            broadcastMessage("SERVER: " + clientName + " has left the chat.");
        }

        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
