import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiClient {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);
        ) {
            System.out.println("Connected to the server.");
            System.out.println("Server says: " + in.readLine());

            String input;
            while (true) {
                System.out.print("You: ");
                input = scanner.nextLine();
                out.println(input);
                String response = in.readLine();
                System.out.println("Server: " + response);
                if ("Goodbye!".equalsIgnoreCase(response)) break;
            }

        } catch (IOException e) {
            System.err.println("Client exception: " + e.getMessage());
        }
    }
}
