package Networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {


        private static final int PORT = 5000;
        private static Set<ClientHandler> clientHandlers = new HashSet<>();

        public static void main (String[]args){
            System.out.println("Chat Server started on port " + PORT);
            ServerSocket serverSocket = null;

            try {
                serverSocket = new ServerSocket(PORT);

                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("New client connected: " + socket.getInetAddress());

                    ClientHandler clientHandler = new ClientHandler(socket, clientHandlers);
                    clientHandlers.add(clientHandler);

                    Thread thread = new Thread(clientHandler);
                    thread.start();
                }
            } catch (IOException e) {
                System.err.println("Server error: " + e.getMessage());
            } finally {
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

