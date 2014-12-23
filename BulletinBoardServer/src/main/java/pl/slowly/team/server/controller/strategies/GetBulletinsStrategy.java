package pl.slowly.team.server.controller.strategies;

import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.packets.helpers.ResponseStatus;
import pl.slowly.team.common.packets.request.data.GetBulletinsRequest;
import pl.slowly.team.common.packets.response.Response;
import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.io.IOException;
import java.util.List;

/**
 * Strategy executed when user wants to retrieve bulletins from specified categories, since specified date.
 *
 * @see GetBulletinsRequest
 */
public class GetBulletinsStrategy extends Strategy {

    public GetBulletinsStrategy(IServer server, IModel model) {
        super(server, model);
    }

    @Override
    public void execute(final PacketWrapper packetWrapper) throws IOException {
        GetBulletinsRequest getBulletins = (GetBulletinsRequest) packetWrapper.getPacket();
        int clientId = packetWrapper.getUserID();
        List<Bulletin> bulletins = model.getBulletins(getBulletins.getCategoriesIds(), getBulletins.getSince());
        server.sendResponseToClient(new Response(ResponseStatus.OK, bulletins), clientId);
    }
}
