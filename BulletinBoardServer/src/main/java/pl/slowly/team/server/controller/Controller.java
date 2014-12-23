package pl.slowly.team.server.controller;

import pl.slowly.team.common.packages.Packet;
import pl.slowly.team.common.packages.helpers.ResponseStatus;
import pl.slowly.team.common.packages.request.authorization.LogInRequest;
import pl.slowly.team.common.packages.request.data.*;
import pl.slowly.team.common.packages.response.Response;
import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.controller.strategies.*;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Controller to manage logic of the server and connection
 * between the clients and the server during sending packages.
 */
public class Controller {
    /** Model being main class of the logic. */
    private final IModel model;
    /** Server managing connections with clients. */
    private final IServer server;
    /** Strategy map for the receiving packages from the client. */
    private final Map<Class<? extends Packet>, Strategy> strategyMap = new HashMap<>();

    /**
     * Reference to the event's queue which is transfer to a controller.
     */
    private final BlockingQueue<PacketWrapper> packetsQueue;

    public Controller(IModel model, IServer server, BlockingQueue<PacketWrapper> packetsQueue) {
        this.model = model;
        this.server = server;
        this.packetsQueue = packetsQueue;
        fillStrategyMap();
    }

    private void fillStrategyMap() {
        strategyMap.put(LogInRequest.class, new LogInStrategy(server, model));
        strategyMap.put(GetCategoriesRequest.class, new GetCategoriesStrategy(server, model));
        strategyMap.put(AddBulletinRequest.class, new AddBulletinStrategy(server, model, packetsQueue));
        strategyMap.put(DeleteBulletinRequest.class, new DeleteBulletinStrategy(server, model, packetsQueue));
        strategyMap.put(GetUserBulletinsRequest.class, new GetUserBulletinsStrategy(server, model));
        strategyMap.put(GetBulletinsRequest.class, new GetBulletinsStrategy(server, model));
    }

    /**
     * Taking incoming packages from the blocking queue and executing the strategy.
     */
    public void takePacketAndExecuteStrategy() {
        while (true) {
            try {
                PacketWrapper wrappedPacket = packetsQueue.take();
                executeStrategyIfAuthorized(wrappedPacket);
            } catch (final InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                System.out.println("Could not execute strategy. Network problems.");
                e.printStackTrace();
            }
        }
    }

    private void executeStrategyIfAuthorized(PacketWrapper wrappedPacket) throws IOException, InterruptedException {
        Packet packet = wrappedPacket.getPacket();
        Strategy strategy = strategyMap.get(packet.getClass());
        if (!canExecuteStrategies(wrappedPacket)) {
            sendNotAuthorizedResponse(wrappedPacket);
        } else {
            executeStrategy(wrappedPacket, strategy);
        }
    }

    private boolean canExecuteStrategies(PacketWrapper wrappedPacket) throws IOException {
        Packet packet = wrappedPacket.getPacket();
        int userId = wrappedPacket.getUserID();
        return (isLoginRequest(packet) ||
                (!(isLoginRequest(packet)) && server.isAuthorized(userId)));
    }

    private boolean isLoginRequest(Packet packet) {
        return packet instanceof LogInRequest;
    }

    private void sendNotAuthorizedResponse(PacketWrapper wrappedPacket) throws IOException {
        server.sendResponseToClient(new Response(ResponseStatus.NOT_AUTHORIZED), wrappedPacket.getUserID());
    }

    private void executeStrategy(PacketWrapper wrappedPacket, Strategy strategy) throws IOException, InterruptedException {
        if (strategy != null) {
            strategy.execute(wrappedPacket);
        } else {
            System.out.println("Unknown strategy.");
        }
    }
}