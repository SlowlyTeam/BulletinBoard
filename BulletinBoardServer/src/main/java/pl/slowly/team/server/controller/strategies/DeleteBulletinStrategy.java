package pl.slowly.team.server.controller.strategies;

import pl.slowly.team.common.packets.helpers.ResponseStatus;
import pl.slowly.team.common.packets.request.broadcast.DeleteBulletinBroadcast;
import pl.slowly.team.common.packets.request.data.DeleteBulletinRequest;
import pl.slowly.team.common.packets.response.DeleteBulletinResponse;
import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.io.IOException;

/**
 * Strategy executed when user wants to delete one of his bulletins.
 *
 * @see DeleteBulletinRequest
 */
public class DeleteBulletinStrategy extends Strategy {

    public DeleteBulletinStrategy(IServer server, IModel model) {
        super(server, model);
    }

    @Override
    public void execute(final PacketWrapper packetWrapper) throws IOException {
        DeleteBulletinRequest deleteBulletin = (DeleteBulletinRequest) packetWrapper.getPacket();
        int clientId = packetWrapper.getUserID();
        int bulletinId = deleteBulletin.getBulletinId();
        boolean result = model.deleteBulletin(bulletinId);
        if (result) {
            server.sendResponseToClient(new DeleteBulletinResponse(ResponseStatus.OK, bulletinId), clientId);
            server.sendBroadcastPacket(new DeleteBulletinBroadcast(bulletinId), clientId);
        } else {
            server.sendResponseToClient(new DeleteBulletinResponse(ResponseStatus.ERROR, bulletinId), clientId);
        }
        LOGGER.info("Server executed strategy: Delete bulletin. Bulletin Id: " + bulletinId + " Client id: " + clientId);
    }
}
