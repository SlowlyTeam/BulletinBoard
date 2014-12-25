package connection;

import GUI.ScreensController;
import connection.strategies.*;
import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.packets.Packet;
import pl.slowly.team.common.packets.helpers.Credentials;
import pl.slowly.team.common.packets.request.Request;
import pl.slowly.team.common.packets.request.authorization.LogInRequest;
import pl.slowly.team.common.packets.request.broadcast.DeleteBulletinBroadcast;
import pl.slowly.team.common.packets.request.broadcast.SendNewBulletinBroadcast;
import pl.slowly.team.common.packets.request.connection.DisconnectFromServerRequest;
import pl.slowly.team.common.packets.request.data.*;
import pl.slowly.team.common.packets.response.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ClientController {

    private final HashMap<Class<? extends Packet>, Strategy> strategyMap = new HashMap<>();
    private int port;
    private String host;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Thread serverResponseListener;
    private ScreensController screensController;

    public ClientController(String host, int port, ScreensController screensController) throws IOException {
        this.port = port;
        this.host = host;
        this.screensController = screensController;
        fillStrategyMap();
    }

    private void fillStrategyMap() {
        strategyMap.put(AddBulletinResponse.class, new AddBulletinResponseStrategy(screensController));
        strategyMap.put(DeleteBulletinBroadcast.class, new DeleteBulletinBroadcastStrategy(screensController));
        strategyMap.put(GetBulletinsResponse.class, new GetBulletinsResponseStrategy(screensController));
        strategyMap.put(GetCategoriesListResponse.class, new GetCategoriesResponseStrategy(screensController));
        strategyMap.put(LogInResponse.class, new LogInResponseStrategy(screensController));
        strategyMap.put(NotAuthorizedResponse.class, new NotAuthorizedResponseStrategy(screensController));
        strategyMap.put(SendNewBulletinBroadcast.class, new SendNewBulletinBroadcastStrategy(screensController));
        strategyMap.put(DeleteBulletinResponse.class, new DeleteBulletinResponseStrategy(screensController));
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
        sendRequest(new AddBulletinRequest(new Bulletin(new Random().nextInt(), "Bulletin 1", "Content 1")));
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
                    Response serverResponse = getResponse();
                    if (serverResponse == null)
                        return;

                    Strategy strategy = strategyMap.get(serverResponse.getClass());

                    if (!strategy.equals(null)) {
                        strategy.execute(serverResponse);
                    } else {
                        System.out.println("Nieznana strategia");
                    }

                    // tutaj aktualizacja widoku
//                    System.out.println(serverResponse.getResponseStatus().toString());
//                    if (serverResponse instanceof Response) {
//                        Response logInResponse = (Response) serverResponse;
//                        LoginScreenController loginScreenController = (LoginScreenController) screensController.getControlledScreen(GUI.loginScreenID);
//                        loginScreenController.logInResponse(logInResponse);
//                    }
//                    List<? extends Entity> entities = serverResponse.getEntities();
//                    if (entities != null) {
//                        for (Entity entity : entities) {
//                            System.out.print(entity);
//                        }
//                    }
//                    System.out.println();
                }
            } catch (IOException | ClassNotFoundException e) {
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
