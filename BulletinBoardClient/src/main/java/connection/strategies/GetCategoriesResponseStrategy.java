package connection.strategies;

import GUI.ChooseCategoryScreenController;
import GUI.ScreensController;
import GUI.GUI;
import pl.slowly.team.common.packets.response.Response;

/**
 * Created by Maxym on 2014-12-24.
 */
public class GetCategoriesResponseStrategy extends Strategy {
    public GetCategoriesResponseStrategy(ScreensController screensController1) {
        super(screensController1);
    }

    @Override
    public void execute(Response response) {
        ChooseCategoryScreenController chooseCategoryScreenController = (ChooseCategoryScreenController) screensController.getControlledScreen(GUI.chooseCategoryID);
        //chooseCategoryScreenController;
    }
}
