package pl.slowly.team.server.controller.strategies;

import pl.slowly.team.common.packages.helpers.ResponseStatus;
import pl.slowly.team.common.packages.request.broadcast.DeleteBulletinBroadcast;
import pl.slowly.team.common.packages.request.data.DeleteBulletinRequest;
import pl.slowly.team.common.packages.response.Response;
import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class DeleteBulletinStrategy extends Strategy {

    private BlockingQueue<PacketWrapper> packetsQueue;

    public DeleteBulletinStrategy(IServer server, IModel model, BlockingQueue<PacketWrapper> packetsQueue) {
        super(server, model);
        this.packetsQueue = packetsQueue;
    }

    @Override
    public void execute(final PacketWrapper packetWrapper) throws IOException {
        DeleteBulletinRequest deleteBulletin = (DeleteBulletinRequest) packetWrapper.getPacket();
        int clientId = packetWrapper.getUserID();
        int bulletinId = deleteBulletin.getBulletinId();
        boolean result = model.deleteBulletin(bulletinId, server.getUsername(clientId));
        if (result) {
            server.sendResponseToClient(new Response(ResponseStatus.OK, null), clientId);
            server.sendBroadcastPacket(new DeleteBulletinBroadcast(bulletinId));
        }
        else {
            server.sendResponseToClient(new Response(ResponseStatus.ERROR, null), clientId);
        }
    }
}
