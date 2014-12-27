package pl.slowly.team.client.connection.strategies.responseStrategies;

import pl.slowly.team.client.GUI.ChooseCategoryScreenController;
import pl.slowly.team.client.GUI.Screens;
import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.client.connection.strategies.Strategy;
import pl.slowly.team.common.data.Category;
import pl.slowly.team.common.packets.Packet;
import pl.slowly.team.common.packets.response.GetCategoriesListResponse;

import java.util.List;

/**
 * Created by Maxym on 2014-12-24.
 */
public class GetCategoriesResponseStrategy extends Strategy {
    public GetCategoriesResponseStrategy(ScreensController screensController) {
        super(screensController);
    }

    @Override
    public void execute(Packet responsePacket) {
        GetCategoriesListResponse getCategoriesListResponse = (GetCategoriesListResponse) responsePacket;
        ChooseCategoryScreenController chooseCategoryScreenController = (ChooseCategoryScreenController) screensController.getControlledScreen(Screens.changeCategoryScreen);
        chooseCategoryScreenController.fillCategories((List<Category>) getCategoriesListResponse.getEntities());
    }
}
