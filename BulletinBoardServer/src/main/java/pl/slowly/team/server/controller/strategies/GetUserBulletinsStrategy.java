package pl.slowly.team.server.controller.strategies;

import pl.slowly.team.common.packages.data.Bulletin;
import pl.slowly.team.common.packages.helpers.ResponseStatus;
import pl.slowly.team.common.packages.request.data.GetUserBulletinsRequest;
import pl.slowly.team.common.packages.response.Response;
import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.io.IOException;
import java.util.List;

public class GetUserBulletinsStrategy extends Strategy {

    public GetUserBulletinsStrategy(IServer server, IModel model) {
        super(server, model);
    }

    @Override
    public void execute(final PacketWrapper packetWrapper) throws IOException {
        GetUserBulletinsRequest getMyBulletins = (GetUserBulletinsRequest) packetWrapper.getPacket();
        int userId = packetWrapper.getUserID();
        String userName = server.getUsername(userId);
        List<Bulletin> bulletins = model.getUserBulletins(userName);
        server.sendResponse(new Response(ResponseStatus.OK, bulletins), userId);
    }
}
