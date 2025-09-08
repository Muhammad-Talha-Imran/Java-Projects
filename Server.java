import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        System.out.println("Server started...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                System.out.println("Waiting for a client...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");

                // Start a new thread to handle the client
                ClientHandler handler = new ClientHandler(clientSocket);
                handler.start();

                // Wait for this client to finish before accepting the next
                handler.join();
                System.out.println("Client disconnected.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println("Welcome to the server. Type 'exit' to quit.");
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if ("exit".equalsIgnoreCase(inputLine)) {
                    out.println("Goodbye!");
                    break;
                }
                out.println("Server: You said '" + inputLine + "'");
            }
        } catch (IOException e) {
            System.out.println("Exception in client handler: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
