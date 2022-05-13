import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ClientHandler {

    ClientHandler opponent;

    Socket socket;
    InputStream inputStream;
    OutputStream outputStream;
    NetworkReading networkReading;
    NetworkWriting networkWriting;

    String playerName;


    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;

    //BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>();
    BlockingQueue<byte[]> blockingQueue = new LinkedBlockingDeque<>();


    public ClientHandler (Socket socket) throws IOException {

        this.socket = socket;
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();

        dataOutputStream = new DataOutputStream(outputStream);
        dataInputStream = new DataInputStream(inputStream);

        System.out.println("NEW CLIENT HANDLER");

    }

    public void startThreads() {
        networkReading = new NetworkReading();
        networkWriting = new NetworkWriting();

        networkReading.start();
        networkWriting.start();
    }

    class NetworkReading extends Thread{
        public void run() {
            try {
                while(true) {
                    int initialData = inputStream.read();
                    if (initialData == -1) {
                        break;
                    }
                    int availableBytes = inputStream.available();
                    byte[] holdArray = new byte[availableBytes + 1];
                    holdArray[0] = (byte) initialData;
                    //System.out.println("READ " + holdArray.length);

                    inputStream.read(holdArray, 1, availableBytes);

                    opponent.blockingQueue.put(holdArray);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();

            }
            System.out.println("SERVER NETWORK READING THREAD ENDING");

        }
    }

    class NetworkWriting extends Thread{
        public void run() {
            try {
                while(true) {
                    byte[] message = blockingQueue.take();
                    //System.out.println("SENT " + message.length);
                    outputStream.write(message);

                    outputStream.flush();
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
            System.out.println("SERVER NETWORK WRITING THREAD ENDING");
        }

    }
}
