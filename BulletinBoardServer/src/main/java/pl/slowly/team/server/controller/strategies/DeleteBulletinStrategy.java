package pl.slowly.team.server.controller.strategies;

import pl.slowly.team.common.packages.data.Category;
import pl.slowly.team.common.packages.helpers.ResponseStatus;
import pl.slowly.team.common.packages.request.data.GetCategoriesRequest;
import pl.slowly.team.common.packages.response.Response;
import pl.slowly.team.server.connection.IServer;
import pl.slowly.team.server.helpers.PacketWrapper;
import pl.slowly.team.server.model.IModel;

import java.io.IOException;
import java.util.List;

public class DeleteBulletinStrategy extends Strategy {

    public DeleteBulletinStrategy(IServer server, IModel model) {
        super(server, model);
    }

    @Override
    public void execute(final PacketWrapper packetWrapper) throws IOException {
//        GetCategoriesRequest getCategories = (GetCategoriesRequest) packetWrapper.getPacket();
//        int userId = packetWrapper.getUserID();
//        List<Category> categories = model.getCategories();
//        server.sendResponse(new Response(ResponseStatus.OK, categories), userId);
    }
}
