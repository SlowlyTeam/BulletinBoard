package pl.slowly.team.server.controller.strategies;

import pl.slowly.team.common.packets.request.broadcast.DeleteBulletinBroadcast;
import pl.slowly.team.common.packets.request.data.DeleteBulletinRequest;
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
        boolean result = model.deleteBulletin(bulletinId, server.getUsername(clientId));
        if (result) {
            // TODO dodać odpowiedź do klienta
//            server.sendResponseToClient(new DeleteBulletinResponse(ResponseStatus.OK), clientId);
            server.sendBroadcastPacket(new DeleteBulletinBroadcast(bulletinId), -1);
        }
//        else {
//            server.sendResponseToClient(new DeleteBulletinResponse(ResponseStatus.ERROR), clientId);
//        }
    }
}
