import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

class Hub {

    public static void main(String[] args) throws IOException {

        int port = 8006;
        ArrayList<ClientHandler> clients = new ArrayList<>();
        //ArrayList<ServerThread> serverThreads = new ArrayList<>();

        ServerSocket serverSocket = new ServerSocket(port);
        int numPlayers = 0;

        while(true){
            try{

                Socket holdSocket;
                holdSocket = serverSocket.accept();
                System.out.println("HERE IN HUB");

                ClientHandler newClient = new ClientHandler(holdSocket);
                clients.add(newClient);
                numPlayers += 1;
                if (numPlayers > 1){
                    newClient.opponent = clients.get(0);
                    clients.get(0).opponent = newClient;
                    newClient.startThreads();
                    clients.get(0).startThreads();
                }

;

            } catch(IOException ioe){
                System.out.println("ACK! ACK!! It's an Exception!!");
                System.out.println(ioe);
            }
        }



//        try {
//
//            // STEP 1: SERVER LISTENS
//
//            int port = 31415;
//            ServerSocket serverSocket = new ServerSocket(port);
//            System.out.println("Server listening on port " + port);
//
//            Socket socket = serverSocket.accept();  // blocks waiting for connection
//
//            // STEP 3: SERVER ACCEPTS CONNECTION
//
//            System.out.println("Server accepted connection");
//
//            InputStream inputStream = socket.getInputStream();
//            OutputStream outputStream = socket.getOutputStream();
//
//            PrintStream printStream = new PrintStream(outputStream);
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//            // STEP 5: SERVER RECEIVES A MESSAGE
//
//            String message = bufferedReader.readLine();
//            System.out.println("SERVER RECEIVED: " + message);
//
//            // STEP 6: SERVER SENDS A RESPONSE
//
//            printStream.println("Hello, nice to meet you.");
//
//            // STEP 8A: SERVER CLOSES CONNECTION
//
//            bufferedReader.close();
//            socket.close();
//            serverSocket.close();
//
//        }
//        catch (IOException ioe) {
//            System.out.println("ACK! ACK!! It's an Exception!!");
//            System.out.println(ioe);
//        }
    }
//
//    class ServerThread extends Thread{
//
//        socket = socket;
//        inputStream = socket.getInputStream();
//        outputStream = socket.getOutputStream();
//
//        dataOutputStream = new DataOutputStream(outputStream);
//        dataInputStream = new DataInputStream(inputStream);
//
//        BlockingQueue<byte[]> blockingQueue = new LinkedBlockingDeque<>();
//
//        public void run() {
//            while(true){
//                try {
//                    //Point point = blockingQueue.take();
//                    String message = serverQueue.take();
//                    System.out.println("WRITING TOOK: " + message);
//                    dataOutputStream.writeUTF(message);
//                } catch (InterruptedException | IOException e) {
//                    e.printStackTrace();
//                }
//
//
//                //pull thing from your blocking queue
//            }
//        }
//
//        // loop forever to see if there is anything in blocking queue
//
//        // if thing in blocking queue, send first message in queue
//    }

}