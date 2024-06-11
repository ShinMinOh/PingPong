import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncTCPServer {

  private static final int PORT = 5000;
  private static int requestCount = 0;

  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(4);

    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      System.out.println("Server started. Waiting for clients...");

      while (true) {
        Socket clientSocket = serverSocket.accept();
        executor.execute(new ClientHandler(clientSocket));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      executor.shutdown();
    }
  }

  private static class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
      this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
      try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
          BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

        InetAddress clientAddress = clientSocket.getInetAddress();
        System.out.println("Client connected: " + clientAddress);

        String request;
        while ((request = in.readLine()) != null) {
          int currentRequest = ++requestCount;
          System.out.println("Received(" + currentRequest + "): " + request);

          String response = request.equalsIgnoreCase("Ping") ? "Pong" : request;

          // Delay response for 3 seconds to simulate asynchronous processing
          try {
            Thread.sleep(5000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          out.println(response + " (" + currentRequest + ")");
          System.out.println("Send: " + response + " (" + currentRequest + ")");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}