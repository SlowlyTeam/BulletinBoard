package pl.slowly.team.server.controller;

import pl.slowly.team.common.packages.Package;
import pl.slowly.team.server.connection.IServer;
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
    private final Map<Class<? extends Package>, Strategy> strategyMap = new HashMap<>();

    /**
     * Reference to the event's queue which is transfer to a controller.
     */
    private final BlockingQueue<Package> packagesQueue;

    public Controller(IModel model, IServer server, BlockingQueue<Package> packagesQueue) {
        this.model = model;
        this.server = server;
        this.packagesQueue = packagesQueue;
        fillStrategyMap();
    }

    private void fillStrategyMap() {
        //TODO - put into strategy map package with bind strategy
    }

    /**
     * Funkcja pobierająca w nieskończonej pętli przychodzące zdarzenia z
     * kolejki blokującej.
     * <p/>
     * Taking coming packages from the blocking queue and executing them.
     */
    public void takePackagesAndExecuteStratagies() {
        while (true) {
            try {
                final Package pack = packagesQueue.take();
                final Strategy strategy = strategyMap.get(pack.getClass());
                strategy.execute(pack);
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
         * @param pack Package for which strategy is executed.
         */
        public abstract void execute(final Package pack);
    }
}
