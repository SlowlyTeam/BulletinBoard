package pl.slowly.team.server.controller.strategies;

import pl.slowly.team.common.packages.helpers.ResponseStatus;
import pl.slowly.team.common.packages.request.broadcast.SendNewBulletin;
import pl.slowly.team.common.packages.request.data.AddBulletinRequest;
import pl.slowly.team.common.packages.response.Response;
import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class AddBulletinStrategy extends Strategy {

    private BlockingQueue<PacketWrapper> packetsQueue;

    public AddBulletinStrategy(IServer server, IModel model, BlockingQueue<PacketWrapper> packetsQueue) {
        super(server, model);
        this.packetsQueue = packetsQueue;
    }

    @Override
    public void execute(final PacketWrapper packetWrapper) throws IOException {
        AddBulletinRequest addBulletin = (AddBulletinRequest) packetWrapper.getPacket();
        int clientId = packetWrapper.getUserID();
        boolean result = model.addBulletin(addBulletin.getBulletin(), server.getUsername(clientId));
        if (result) {
            server.sendResponse(new Response(ResponseStatus.OK, null), clientId);
            // TODO dodac broadcast message
//            packetsQueue.put(new PacketWrapper(new SendNewBulletin(addBulletin.getBulletin(), new ArrayList<Integer>())));
        }
        else {
            server.sendResponse(new Response(ResponseStatus.ERROR, null), clientId);
        }
    }
}
