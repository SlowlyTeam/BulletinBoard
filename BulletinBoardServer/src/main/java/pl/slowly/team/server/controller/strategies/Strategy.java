package pl.slowly.team.server.controller.strategies;

import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * Logic strategy based on packages from clients.
 */
public abstract class Strategy {

    protected IServer server;
    protected IModel model;

    public Strategy(IServer server, IModel model) {
        this.server = server;
        this.model = model;
    }

    /**
     * Execute strategy for the package.
     *
     * @param packetWrapper PacketWrapper for which strategy is executed.
     */
    public abstract void execute(final PacketWrapper packetWrapper) throws IOException, InterruptedException;
}

