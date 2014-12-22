package pl.slowly.team.server;

import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.connection.MultiThreadedServer;
import pl.slowly.team.server.controller.Controller;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.FakeModel;
import pl.slowly.team.server.model.IModel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Main class to run the server.
 * <p/>
 * Created by Emil Kleszcz on 2014-12-17.
 */
public class ServerRun {
    public static void main(String[] args) {
        final BlockingQueue<PacketWrapper> blockingQueue = new LinkedBlockingQueue<>();
        final IModel model = new FakeModel();
        final IServer server;

        if (args.length >= 1) {
            System.out.println("Port: " + args[0]);
            server = new MultiThreadedServer(Integer.parseInt(args[0]), blockingQueue);
        } else {
            System.out.println("Port: 8081");
            server = new MultiThreadedServer(8081, blockingQueue);
        }
        server.listen();
        System.out.println("Serwer został uruchomiony.");
        final Controller controller = new Controller(model, server, blockingQueue);
        controller.takePackagesAndExecuteStrategy();
        System.out.println("Serwer został zamknięty.");
    }
}
