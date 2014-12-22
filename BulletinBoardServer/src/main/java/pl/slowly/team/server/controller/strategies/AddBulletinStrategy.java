package pl.slowly.team.server.controller.strategies;

import pl.slowly.team.common.packages.helpers.ResponseStatus;
import pl.slowly.team.common.packages.request.data.AddBulletinRequest;
import pl.slowly.team.common.packages.response.Response;
import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.io.IOException;

public class AddBulletinStrategy extends Strategy {

    public AddBulletinStrategy(IServer server, IModel model) {
        super(server, model);
    }

    @Override
    public void execute(final PacketWrapper packetWrapper) throws IOException {
        AddBulletinRequest addBulletin = (AddBulletinRequest) packetWrapper.getPacket();
        int clientId = packetWrapper.getUserID();
        boolean result = model.addBulletin(addBulletin.getBulletin(), server.getUsername(clientId));
        if (result) {
            server.sendResponse(new Response(ResponseStatus.OK, null), clientId);
        }
        else {
            server.sendResponse(new Response(ResponseStatus.ERROR, null), clientId);
        }
    }
}
