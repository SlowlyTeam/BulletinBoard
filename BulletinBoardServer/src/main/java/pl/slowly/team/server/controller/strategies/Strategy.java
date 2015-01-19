package pl.slowly.team.server.controller.strategies;

import org.apache.log4j.Logger;
import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.io.IOException;

/**
 * Logic strategy based on packets from clients.
 */
public abstract class Strategy {

    public final static Logger LOGGER = Logger.getLogger(Strategy.class);
    protected IServer server;
    protected IModel model;

    public Strategy(IServer server, IModel model) {
        this.server = server;
        this.model = model;
    }

    /**
     * Execute strategy for the package.
     *
     * @param packetWrapper PacketWrapper with field packet for which strategy is executed.
     */
    public abstract void execute(final PacketWrapper packetWrapper) throws IOException, InterruptedException;
}

