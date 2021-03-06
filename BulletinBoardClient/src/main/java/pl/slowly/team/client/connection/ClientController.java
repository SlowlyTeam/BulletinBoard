package pl.slowly.team.client.connection;

import javafx.application.Platform;
import org.apache.log4j.Logger;
import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.client.connection.strategies.Strategy;
import pl.slowly.team.client.connection.strategies.packetStrategies.DeleteBulletinBroadcastStrategy;
import pl.slowly.team.client.connection.strategies.packetStrategies.EditBulletinBroadcastStrategy;
import pl.slowly.team.client.connection.strategies.packetStrategies.SendNewBulletinBroadcastStrategy;
import pl.slowly.team.client.connection.strategies.responseStrategies.*;
import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.packets.Packet;
import pl.slowly.team.common.packets.helpers.Credentials;
import pl.slowly.team.common.packets.request.Request;
import pl.slowly.team.common.packets.request.authorization.LogInRequest;
import pl.slowly.team.common.packets.request.broadcast.DeleteBulletinBroadcast;
import pl.slowly.team.common.packets.request.broadcast.EditBulletinBroadcast;
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

    private final static Logger LOGGER = Logger.getLogger(ClientController.class);
    private final HashMap<Class<? extends Packet>, Strategy> strategyMap = new HashMap<>();

    private int port;
    private String host;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Thread serverResponseListener;
    private ScreensController screensController;

    public ClientController(String host, int port, ScreensController screensController) {
        this.port = port;
        this.host = host;
        this.screensController = screensController;
        fillStrategyMap();
        LOGGER.info("Client Controller created. Host: " + host + ", port: " + port);
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
        strategyMap.put(EditBulletinResponse.class, new EditBulletinResponseStrategy(screensController));
        strategyMap.put(EditBulletinBroadcast.class, new EditBulletinBroadcastStrategy(screensController));
    }

    public boolean connectToServer() throws IOException {
        if (socket != null)
            return true;
        socket = new Socket(host, port);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        serverResponseListener = new ServerResponseListener();
        serverResponseListener.start();
        LOGGER.info("Connected to server.");
        return true;
    }

    public void disconnectFromServer() throws IOException, InterruptedException {
        if (socket == null)
            return;
        serverResponseListener.stop();
        sendRequest(new DisconnectFromServerRequest());
        LOGGER.info("Disconnected from server.");
    }

    public void logIn(String userName, String password) throws IOException {
        LOGGER.info("Request LogInRequest was sent.");
        sendRequest(new LogInRequest(new Credentials(userName, password)));
    }

    public void addBulletin(String bulletinTitle, String bulletinContent) throws IOException {
        LOGGER.info("Request AddBulletinRequest was sent. Title: " + bulletinTitle);
        sendRequest(new AddBulletinRequest(new Bulletin(bulletinTitle, bulletinContent)));
    }

    public void deleteBulletin(int bulletinId) throws IOException {
        LOGGER.info("Request DeleteBulletinRequest was sent. Bulletin id: " + bulletinId);
        sendRequest(new DeleteBulletinRequest(bulletinId));
    }

    public void editBulletin(Bulletin bulletin) throws IOException {
        LOGGER.info("Request EditBulletinRequest was sent. Bulletin id: " + bulletin.getBulletinId());
        sendRequest(new EditBulletinRequest(bulletin));
    }

    public void getCategories() throws IOException {
        LOGGER.info("Request GetCategoriesRequest was sent.");
        sendRequest(new GetCategoriesRequest());
    }

    public void getBulletins(List<Integer> categoriesId) throws IOException {
        LOGGER.info("Request GetBulletinsRequest was sent.");
        sendRequest(new GetBulletinsRequest(categoriesId, null));
    }

    public void sendRequest(Request request) throws IOException {
        outputStream.writeObject(request);
    }

    public class ServerResponseListener extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    Packet serverPacket = getResponse();
                    if (serverPacket == null)
                        return;
                    LOGGER.info("Received packet " + serverPacket.getClass().getSimpleName() + ".");
                    Strategy strategy = strategyMap.get(serverPacket.getClass());

                    if (!strategy.equals(null)) {
                        strategy.execute(serverPacket);
                    } else {
                        LOGGER.info("Unknown strategy.");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.error("Error: " + e);
                Platform.runLater(() -> screensController.showExitDialog("Błąd połączenia z serwerem", e));
            }
        }

        private Packet getResponse() throws IOException, ClassNotFoundException {
            if (!socket.isClosed()) {
                return (Packet) inputStream.readObject();
            } else {
                LOGGER.info("Gniazdo zostało zamkniete.");
            }
            return null;
        }
    }
}
