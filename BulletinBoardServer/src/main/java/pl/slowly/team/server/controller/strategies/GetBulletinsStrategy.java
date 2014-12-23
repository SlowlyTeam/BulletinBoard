package pl.slowly.team.server.controller.strategies;

import pl.slowly.team.common.packages.data.Bulletin;
import pl.slowly.team.common.packages.data.Entity;
import pl.slowly.team.common.packages.helpers.ResponseStatus;
import pl.slowly.team.common.packages.request.data.AddBulletinRequest;
import pl.slowly.team.common.packages.request.data.GetBulletinsRequest;
import pl.slowly.team.common.packages.response.Response;
import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.io.IOException;
import java.util.List;

public class GetBulletinsStrategy extends Strategy {

    public GetBulletinsStrategy(IServer server, IModel model) {
        super(server, model);
    }

    @Override
    public void execute(final PacketWrapper packetWrapper) throws IOException {
        GetBulletinsRequest getBulletins = (GetBulletinsRequest) packetWrapper.getPacket();
        int clientId = packetWrapper.getUserID();
        List<Bulletin> bulletins = model.getBulletins(getBulletins.getCategoriesIds(), getBulletins.getSince());
        server.sendResponse(new Response(ResponseStatus.OK, bulletins), clientId);
    }
}
