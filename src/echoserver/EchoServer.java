package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
    public static final int PORT_NUMBER = 6013;
    private static final int THREAD_POOL_SIZE = 12;

    public static void main(String[] args) throws IOException {
        EchoServer server = new EchoServer();
        server.start();
    }

    private void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
            ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

            System.out.println("EchoServer is running on port " + PORT_NUMBER);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to client at " + clientSocket.getRemoteSocketAddress());

                // Submit a task to the thread pool to handle the client connection
                threadPool.submit(() -> handleClient(clientSocket));
            }
        }
    }

    private void handleClient(Socket socket) {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {

            int data;
            while ((data = inputStream.read()) != -1) {
                outputStream.write(data); // Echo data back to client
            }

            System.out.println("Client at " + socket.getRemoteSocketAddress() + " disconnected.");
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Failed to close client socket: " + e.getMessage());
            }
        }
    }
}
