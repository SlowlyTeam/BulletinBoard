package pl.slowly.team.server.controller.strategies;

import pl.slowly.team.common.packages.helpers.Credentials;
import pl.slowly.team.common.packages.helpers.ResponseStatus;
import pl.slowly.team.common.packages.request.authorization.LogInRequest;
import pl.slowly.team.common.packages.response.Response;
import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.io.IOException;

public class LogInStrategy extends Strategy {

    public LogInStrategy(IServer server, IModel model) {
        super(server, model);
    }

    @Override
    public void execute(final PacketWrapper packetWrapper) throws IOException {
        LogInRequest logIn = (LogInRequest) packetWrapper.getPacket();
        Credentials credentials = logIn.getUserCredentials();
        int userId = packetWrapper.getUserID();
        if (model.checkCredentials(credentials)) {
            server.authorizeClient(userId);
            server.sendResponse(new Response(ResponseStatus.AUTHORIZED), userId);
        } else {
            server.sendResponse(new Response(ResponseStatus.NOT_AUTHORIZED), userId);
        }
    }
}
