package connection;

import pl.slowly.team.common.packets.Packet;
import pl.slowly.team.common.packets.request.Request;
import pl.slowly.team.common.packets.request.broadcast.SendNewBulletinBroadcast;

import java.io.*;
import java.net.Socket;

public class Client implements AutoCloseable{

    private int port;
    private String host;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Thread serverResponseListener;

    public Client(String host, int port) throws IOException {
        this.port = port;
        this.host = host;
    }

    public boolean connectToServer() throws IOException {
        socket = new Socket(host, port);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        serverResponseListener = new ServerResponseListener();
        serverResponseListener.start();
        return true;
    }

    public void sendRequest(Request request) throws IOException {
        outputStream.writeObject(request);
        System.out.println("wysyłanie żądania...");
    }


    @Override
    public void close() throws IOException, InterruptedException {
        inputStream.close();
        outputStream.close();
        socket.close();
        serverResponseListener.join();
    }

    public class ServerResponseListener extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    Packet serverResponse = getResponsePacket();
                    if (serverResponse == null)
                        return;
                    // tutaj aktualizacja widoku
//                    System.out.println(serverResponse.getResponseStatus().toString());
                    if (serverResponse instanceof SendNewBulletinBroadcast) {
                        System.out.println(((SendNewBulletinBroadcast) serverResponse).getBulletin().getBulletinTitle());
                    }
//                    List<? extends Entity> entities = serverResponse.getEntities();
//                    if (entities != null) {
//                        for (Entity entity : entities) {
//                            System.out.print(entity);
//                        }
//                    }
//                    System.out.println();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private Packet getResponsePacket() throws IOException, ClassNotFoundException {
            synchronized (socket) {
                if (!socket.isClosed()) {
                    return (Packet) inputStream.readObject();
                }
                return null;
            }
        }
    }
}
