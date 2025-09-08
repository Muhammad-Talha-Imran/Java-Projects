import java.io.*;
import java.net.*;

public class MultiServer {

    private static final int PORT = 12345;

    public static void main(String[] args) {
        System.out.println("Server started. Waiting for clients...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Create and start a new thread for each client
                ClientHandler handler = new ClientHandler(clientSocket);
                Thread thread = new Thread(handler);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;

    ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try (
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
        ) {
            out.println("Welcome to the server. Type 'bye' to exit.");

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received from client: " + message);
                if ("bye".equalsIgnoreCase(message)) {
                    out.println("Goodbye!");
                    break;
                }
                out.println("Echo: " + message);
            }

        } catch (IOException e) {
            System.err.println("Client exception: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("Connection with client closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
