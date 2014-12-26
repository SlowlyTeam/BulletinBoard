package pl.slowly.team.client.connection.strategies;

import pl.slowly.team.client.GUI.GUI;
import pl.slowly.team.client.GUI.MainViewController;
import pl.slowly.team.client.GUI.Screens;
import pl.slowly.team.client.GUI.ScreensController;
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
        MainViewController mainViewController = (MainViewController) screensController.getControlledScreen(Screens.mainScreen);
        mainViewController.setBulletins((List<Bulletin>) response.getEntities());
    }
}
