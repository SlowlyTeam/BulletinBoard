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
 * Strategy executed when user wants to get his bulletins.
 *
 * @see GetBulletinsRequest
 */
public class GetUserBulletinsStrategy extends Strategy {

    public GetUserBulletinsStrategy(IServer server, IModel model) {
        super(server, model);
    }

    @Override
    public void execute(final PacketWrapper packetWrapper) throws IOException {
        int userId = packetWrapper.getUserID();
        String userName = server.getUsername(userId);
        List<Bulletin> bulletins = model.getUserBulletins(userName);
        server.sendResponseToClient(new Response(ResponseStatus.OK, bulletins), userId);
    }
}
