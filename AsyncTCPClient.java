import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncTCPClient {
  private static final String SERVER_IP = "127.0.0.1";
  private static final int SERVER_PORT = 5000;
  private static int requestCount = 0;
  private static int responseCount = 0;

  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(4);

    try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Scanner scanner = new Scanner(System.in)) {

      System.out.println("Connected to the server.");

      // Separate thread to listen for responses
      executor.execute(() -> {
        try {
          String response;
          while ((response = in.readLine()) != null) {
            int currentResponse = ++responseCount;
            synchronized (System.out) {
              System.out.println();
              System.out.println("Received: " + response );
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      });

      while (true) {
        System.out.print("Send(" + (requestCount + 1) + "): ");
        String message = scanner.nextLine();

        if (message.equalsIgnoreCase("exit")) {
          break;
        }

        int currentRequest = ++requestCount;
        out.println(message);
        System.out.println("Send(" + currentRequest + "): " + message);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      executor.shutdown();
    }
  }
}