package pl.slowly.team.client.connection.strategies;

import pl.slowly.team.client.GUI.ChooseCategoryScreenController;
import pl.slowly.team.client.GUI.Screens;
import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.client.GUI.GUI;
import pl.slowly.team.common.data.Category;
import pl.slowly.team.common.packets.response.Response;

import java.util.List;

/**
 * Created by Maxym on 2014-12-24.
 */
public class GetCategoriesResponseStrategy extends Strategy {
    public GetCategoriesResponseStrategy(ScreensController screensController) {
        super(screensController);
    }

    @Override
    public void execute(Response response) {
        ChooseCategoryScreenController chooseCategoryScreenController = (ChooseCategoryScreenController) screensController.getControlledScreen(Screens.changeCategoryScreen);
        chooseCategoryScreenController.fillCategories((List<Category>) response.getEntities());
    }
}
