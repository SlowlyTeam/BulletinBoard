package pl.slowly.team.client.connection;

import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.client.connection.strategies.Strategy;
import pl.slowly.team.client.connection.strategies.packetStrategies.DeleteBulletinBroadcastStrategy;
import pl.slowly.team.client.connection.strategies.packetStrategies.SendNewBulletinBroadcastStrategy;
import pl.slowly.team.client.connection.strategies.responseStrategies.*;
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

    public void disconnectFromServer() throws IOException, InterruptedException {
        sendRequest(new DisconnectFromServerRequest());
    }

    public void logIn(String userName, String password) throws IOException {
        sendRequest(new LogInRequest(new Credentials(userName, password)));
    }

    public void addBulletin(String bulletinTitle, String bulletinContent) throws IOException {
        System.out.println("Żądanie: dodaj nowy bulletin.");
        sendRequest(new AddBulletinRequest(new Bulletin(bulletinTitle, bulletinContent)));
    }

    public void deleteBulletin(int bulletinId) throws IOException {
        sendRequest(new DeleteBulletinRequest(bulletinId));
    }

    public void getCategories() throws IOException {
        sendRequest(new GetCategoriesRequest());
    }

    public void getBulletins(List<Integer> categoriesId) throws IOException {
        sendRequest(new GetBulletinsRequest(categoriesId, null));
    }

    public void getUserBulletins() throws IOException {
        sendRequest(new GetUserBulletinsRequest());
    }

    public void sendRequest(Request request) throws IOException {
        outputStream.writeObject(request);
        System.out.println("Wysłano żądanie...");
    }

    public class ServerResponseListener extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    Packet serverPacket = getResponse();
                    if (serverPacket == null)
                        return;

                    Strategy strategy = strategyMap.get(serverPacket.getClass());

                    if (!strategy.equals(null)) {
                        strategy.execute(serverPacket);
                    } else {
                        System.out.println("Nieznana strategia");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                // TODO dodać w widoku jakaś informację, która po kliknieciu okej zamknie aplikację.
                System.exit(0);
            }
        }

        private Packet getResponse() throws IOException, ClassNotFoundException {
            if (!socket.isClosed()) {
                return (Packet) inputStream.readObject();
            }
            else {
                System.out.println("gniazdo zostało zamkniete.");
            }
            return null;
        }
    }
}
