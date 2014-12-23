package pl.slowly.team.server.controller.strategies;

import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.packets.helpers.ResponseStatus;
import pl.slowly.team.common.packets.request.broadcast.SendNewBulletinBroadcast;
import pl.slowly.team.common.packets.request.data.AddBulletinRequest;
import pl.slowly.team.common.packets.response.Response;
import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.io.IOException;

/**
 * Strategy executed when user wants to add new bulletin.
 *
 * @see AddBulletinRequest
 */
public class AddBulletinStrategy extends Strategy {

    public AddBulletinStrategy(IServer server, IModel model) {
        super(server, model);
    }

    @Override
    public void execute(final PacketWrapper packetWrapper) throws IOException, InterruptedException {
        AddBulletinRequest addBulletinReq = (AddBulletinRequest) packetWrapper.getPacket();
        Bulletin bulletin = addBulletinReq.getBulletin();
        int clientId = packetWrapper.getUserID();
        boolean result = model.addBulletin(bulletin, server.getUsername(clientId));
        if (result) {
            server.sendResponseToClient(new Response(ResponseStatus.OK, null), clientId);
            server.sendBroadcastPacket(new SendNewBulletinBroadcast(bulletin));
        }
        else {
            server.sendResponseToClient(new Response(ResponseStatus.ERROR, null), clientId);
        }
    }
}
