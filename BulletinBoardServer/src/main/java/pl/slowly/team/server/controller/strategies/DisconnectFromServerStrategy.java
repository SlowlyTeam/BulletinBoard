package pl.slowly.team.server.controller.strategies;

import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.io.IOException;

public class DisconnectFromServerStrategy extends Strategy {

    public DisconnectFromServerStrategy(IServer server, IModel model) {
        super(server, model);
    }

    @Override
    public void execute(PacketWrapper packetWrapper) throws IOException, InterruptedException {
        int clientId = packetWrapper.getUserID();
        server.disconnectClient(clientId);
    }

}
