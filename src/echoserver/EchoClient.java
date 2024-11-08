package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
    public static final int PORT_NUMBER = 6013;

    public static void main(String[] args) throws IOException {
        EchoClient client = new EchoClient();
        client.start();
    }

    private void start() throws IOException {
        	Socket socket = new Socket("localhost", PORT_NUMBER);
            InputStream socketInputStream = socket.getInputStream();
            OutputStream socketOutputStream = socket.getOutputStream();

            Thread inputThread = new Thread(() -> {
                try {
                    int data;
                    while ((data = System.in.read()) != -1) {
                        socketOutputStream.write(data);
                    }
                    socket.shutdownOutput(); // Signal end of output stream
                } catch (IOException e) {
                    System.err.println("Error in input thread: " + e.getMessage());
                }
            });

            Thread outputThread = new Thread(() -> {
                try {
                    int data;
                    while ((data = socketInputStream.read()) != -1) {
                        System.out.write(data);
                    }
                    System.out.flush(); // Ensure all data is output
                } catch (IOException e) {
                    System.err.println("Error in output thread: " + e.getMessage());
                }
            });

            // Start both threads
            inputThread.start();
            outputThread.start();

            // Wait for both threads to complete
            try {
                inputThread.join();
                outputThread.join();
            } catch (InterruptedException e) {
                System.err.println("Client threads interrupted: " + e.getMessage());
            }
         // Socket will automatically close here due to try-with-resources
    }
}
