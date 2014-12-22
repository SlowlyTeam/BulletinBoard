package connection;

import pl.slowly.team.common.packages.data.Category;
import pl.slowly.team.common.packages.data.Entity;
import pl.slowly.team.common.packages.helpers.Credentials;
import pl.slowly.team.common.packages.request.Request;
import pl.slowly.team.common.packages.request.authorization.LogInRequest;
import pl.slowly.team.common.packages.request.data.GetCategoriesRequest;
import pl.slowly.team.common.packages.response.Response;

import java.io.*;
import java.net.Socket;
import java.util.List;

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
                    Response serverResponse = getResponse();
                    if (serverResponse == null)
                        return;
                    // tutaj aktualizacja widoku
                    System.out.println(serverResponse.getResponseStatus().toString());
                    List<? extends Entity> entities = serverResponse.getEntities();
                    for (Entity entity : entities) {
                        if (entity instanceof Category) {
                            System.out.println(((Category) entity).getCategoryName());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private Response getResponse() throws IOException, ClassNotFoundException {
            synchronized (socket) {
                if (!socket.isClosed()) {
                    return (Response) inputStream.readObject();
                }
                return null;
            }
        }
    }
}
