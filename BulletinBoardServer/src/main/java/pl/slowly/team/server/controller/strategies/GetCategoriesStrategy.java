package pl.slowly.team.server.controller.strategies;

import pl.slowly.team.common.data.Category;
import pl.slowly.team.common.packets.helpers.ResponseStatus;
import pl.slowly.team.common.packets.request.data.GetCategoriesRequest;
import pl.slowly.team.common.packets.response.GetCategoriesListResponse;
import pl.slowly.team.common.packets.response.Response;
import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.io.IOException;
import java.util.List;

/**
 * Strategy executed when user tries to log in.
 *
 * @see GetCategoriesRequest
 */
public class GetCategoriesStrategy extends Strategy {

    public GetCategoriesStrategy(IServer server, IModel model) {
        super(server, model);
    }

    @Override
    public void execute(final PacketWrapper packetWrapper) throws IOException {
        int userId = packetWrapper.getUserID();
        List<Category> categories = model.getCategories();
        server.sendResponseToClient(new GetCategoriesListResponse(ResponseStatus.OK, categories), userId);
    }
}
