package pl.slowly.team.client.connection.strategies;

import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.common.packets.response.Response;

/**
 * Created by Maxym on 2014-12-24.
 */
public class AddBulletinResponseStrategy extends Strategy {
    public AddBulletinResponseStrategy(ScreensController screensController) {
        super(screensController);
    }

    @Override
    public void execute(Response response) {
        
    }
}
