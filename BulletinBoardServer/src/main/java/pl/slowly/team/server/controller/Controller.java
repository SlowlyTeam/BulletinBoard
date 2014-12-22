package pl.slowly.team.server.controller;

import pl.slowly.team.common.packages.Packet;
import pl.slowly.team.common.packages.helpers.Credentials;
import pl.slowly.team.common.packages.request.authorization.LogInRequest;
import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Controller to manage logic of the server and connection
 * between the clients and the server during sending packages.
 * <p/>
 * Created by Emil Kleszcz on 2014-12-17.
 */
public class Controller {
    /**
     * Model being main class of the logic.
     */
    private final IModel model;

    /**
     * Server managing connections with clients.
     */
    private final IServer server;

    /**
     * Strategy map for the receiving packages from the client.
     */
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
        //TODO - put into strategy map package with bind strategy
        strategyMap.put(LogInRequest.class, new LogInStrategy());

    }

    /**
     * Funkcja pobierająca w nieskończonej pętli przychodzące zdarzenia z
     * kolejki blokującej.
     * <p/>
     * Taking incoming packages from the blocking queue and executing the strategy.
     */
    public void takePackagesAndExecuteStratagy() {
        while (true) {
            try {
                PacketWrapper wrappedPacket = packetsQueue.take();
                Packet packet = wrappedPacket.getPacket();
                Strategy strategy = strategyMap.get(packet.getClass());
                strategy.execute(packet, wrappedPacket.getUserID());
            } catch (final InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Logic strategy based on packages from clients.
     */
    private abstract class Strategy {
        /**
         * Execute strategy for the package.
         *
         * @param packet Packet for which strategy is executed.
         */
        public abstract void execute(final Packet packet, final int clientId);
    }

    private class LogInStrategy extends Strategy {

        @Override
        public void execute(final Packet packet, final int clientId) {
            LogInRequest logIn = (LogInRequest) packet;
            Credentials credentials = logIn.getUserCredentials();
            if (model.checkCredentials(credentials)) {
                server.authorizeClient(clientId);
                System.out.println("Sukces");
            }
        }
    }
}
