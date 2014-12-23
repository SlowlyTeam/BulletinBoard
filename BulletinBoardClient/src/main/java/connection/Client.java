package connection;

import GUI.*;
import GUI.ScreensController;
import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.packets.Packet;
import pl.slowly.team.common.packets.helpers.Credentials;
import pl.slowly.team.common.packets.request.Request;
import pl.slowly.team.common.packets.request.authorization.LogInRequest;
import pl.slowly.team.common.packets.request.broadcast.SendNewBulletinBroadcast;
import pl.slowly.team.common.packets.request.connection.DisconnectFromServerRequest;
import pl.slowly.team.common.packets.request.data.*;
import pl.slowly.team.common.packets.response.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Client {

    private int port;
    private String host;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Thread serverResponseListener;
    private ScreensController screensController;

    public Client(String host, int port, ScreensController screensController) throws IOException {
        this.port = port;
        this.host = host;
        this.screensController = screensController;
    }

    public boolean connectToServer() throws IOException {
        socket = new Socket(host, port);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        serverResponseListener = new ServerResponseListener();
        serverResponseListener.start();
        return true;
    }

    private void disconnectFromServer() throws IOException, InterruptedException {
        sendRequest(new DisconnectFromServerRequest());
    }

    public void logIn(String userName, String password) throws IOException {
        sendRequest(new LogInRequest(new Credentials(userName, password)));
    }

    public void addBulletin() throws IOException {
        sendRequest(new AddBulletinRequest(new Bulletin("my own bulletin?")));
    }

    public void deleteBulletin() throws IOException {
        sendRequest(new DeleteBulletinRequest(1));
    }

    public void getCategories() throws IOException {
        sendRequest(new GetCategoriesRequest());
    }

    public void getBulletins(List<Integer> bulletinsIds) throws IOException {
        sendRequest(new GetBulletinsRequest(bulletinsIds, null));
    }

    public void getUserBulletins() throws IOException {
        sendRequest(new GetUserBulletinsRequest());
    }

    public void sendRequest(Request request) throws IOException {
        outputStream.writeObject(request);
        System.out.println("wysyłanie żądania...");
    }

    public void close() throws IOException, InterruptedException {
//        inputStream.close();
//        outputStream.close();
//        socket.close();
//        serverResponseListener.join();
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
                    if (serverResponse instanceof Response) {
                        Response logInResponse = (Response) serverResponse;
                        LoginScreenController loginScreenController = (LoginScreenController) screensController.getControlledScreen(GUI.loginScreenID);
                        loginScreenController.logInResponse(logInResponse);
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
