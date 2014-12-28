package pl.slowly.team.client.connection.strategies.responseStrategies;

import pl.slowly.team.client.GUI.MainViewController;
import pl.slowly.team.client.GUI.Screens;
import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.client.connection.strategies.Strategy;
import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.packets.Packet;
import pl.slowly.team.common.packets.response.GetBulletinsResponse;

import java.util.List;

/**
 * Created by Maxym on 2014-12-24.
 */
public class GetBulletinsResponseStrategy extends Strategy {
    public GetBulletinsResponseStrategy(ScreensController screensController) {
        super(screensController);
    }

    @Override
    public void execute(Packet responsePacket) {
        GetBulletinsResponse getBulletinsResponse = (GetBulletinsResponse) responsePacket;
        MainViewController mainViewController = (MainViewController) screensController.getControlledScreen(Screens.mainScreen);
        List<Bulletin> bulletinList = getBulletinsResponse.getBulletins();
        mainViewController.setBulletins(bulletinList);
    }
}
