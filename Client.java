import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(
                new InputStreamReader(System.in))
        ) {
            String serverMessage;
            System.out.println("Connected to server.");

            // Read welcome message
            if ((serverMessage = in.readLine()) != null) {
                System.out.println("Server: " + serverMessage);
            }

            String input;
            while (true) {
                System.out.print("You: ");
                input = userInput.readLine();
                if (input == null || input.equalsIgnoreCase("exit")) {
                    out.println("exit");
                    break;
                }
                out.println(input);
                serverMessage = in.readLine();
                if (serverMessage != null) {
                    System.out.println(serverMessage);
                }
            }

            System.out.println("Disconnected from server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
