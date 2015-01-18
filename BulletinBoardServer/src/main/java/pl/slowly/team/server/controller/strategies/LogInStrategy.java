package pl.slowly.team.server.controller.strategies;

import pl.slowly.team.common.data.Category;
import pl.slowly.team.common.packets.helpers.Credentials;
import pl.slowly.team.common.packets.helpers.ResponseStatus;
import pl.slowly.team.common.packets.request.authorization.LogInRequest;
import pl.slowly.team.common.packets.response.LogInResponse;
import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.io.IOException;

/**
 * Strategy executed when user tries to log in.
 *
 * @see LogInRequest
 */
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
            server.authorizeClient(userId, credentials.getUsername());
            Category categoryID = model.getUserCategory(credentials.getUsername());
            server.sendResponseToClient(new LogInResponse(ResponseStatus.AUTHORIZED, categoryID), userId);
        } else {
            server.sendResponseToClient(new LogInResponse(ResponseStatus.NOT_AUTHORIZED, null), userId);
        }
    }
}
