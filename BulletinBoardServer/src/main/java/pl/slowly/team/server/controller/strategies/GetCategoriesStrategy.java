package pl.slowly.team.server.controller.strategies;

import pl.slowly.team.common.packets.data.Category;
import pl.slowly.team.common.packets.helpers.ResponseStatus;
import pl.slowly.team.common.packets.response.Response;
import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.io.IOException;
import java.util.List;

public class GetCategoriesStrategy extends Strategy {

    public GetCategoriesStrategy(IServer server, IModel model) {
        super(server, model);
    }

    @Override
    public void execute(final PacketWrapper packetWrapper) throws IOException {
        int userId = packetWrapper.getUserID();
        List<Category> categories = model.getCategories();
        server.sendResponseToClient(new Response(ResponseStatus.OK, categories), userId);
    }
}
