import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "localhost"; // change if server is remote
        int port = 5000;

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connected to server.");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            String message;

            while (true) {
                System.out.print("Enter message: ");
                message = scanner.nextLine();
                out.println(message);

                String response = in.readLine();
                System.out.println("Server: " + response);

                if (message.equalsIgnoreCase("quit")) {
                    System.out.println("Closing client...");
                    break;
                }
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
