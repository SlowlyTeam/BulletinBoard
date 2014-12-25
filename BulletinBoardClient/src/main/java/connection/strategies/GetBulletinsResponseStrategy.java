package connection.strategies;

import GUI.GUI;
import GUI.MainViewController;
import GUI.ScreensController;
import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.packets.response.Response;

import java.util.List;

/**
 * Created by Maxym on 2014-12-24.
 */
public class GetBulletinsResponseStrategy extends Strategy {
    public GetBulletinsResponseStrategy(ScreensController screensController) {
        super(screensController);
    }

    @Override
    public void execute(Response response) {
        MainViewController mainViewController = (MainViewController) screensController.getControlledScreen(GUI.mainScreenID);
        mainViewController.setBulletins((List<Bulletin>) response.getEntities());
    }
}
