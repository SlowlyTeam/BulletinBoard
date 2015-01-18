package pl.slowly.team.server.controller.strategies;

import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.packets.helpers.ResponseStatus;
import pl.slowly.team.common.packets.request.broadcast.EditBulletinBroadcast;
import pl.slowly.team.common.packets.request.data.EditBulletinRequest;
import pl.slowly.team.common.packets.response.EditBulletinResponse;
import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.io.IOException;

public class EditBulletinStrategy extends Strategy {

    public EditBulletinStrategy(IServer server, IModel model) {
        super(server, model);
    }

    @Override
    public void execute(PacketWrapper packetWrapper) throws IOException, InterruptedException {
        EditBulletinRequest editBulletinRequest = (EditBulletinRequest) packetWrapper.getPacket();
        Bulletin bulletin = editBulletinRequest.getBulletin();
        int clientId = packetWrapper.getUserID();
        boolean success = model.editBulletin(bulletin, server.getUsername(clientId), server.getUserCategory(clientId));
        if (success) {
            server.sendResponseToClient(new EditBulletinResponse(ResponseStatus.OK, bulletin), clientId);
            server.sendBroadcastPacket(new EditBulletinBroadcast(bulletin), clientId);
        } else {
            server.sendResponseToClient(new EditBulletinResponse(ResponseStatus.ERROR, null), clientId);
        }
    }
}
