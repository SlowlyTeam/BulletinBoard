package connection.strategies;

import GUI.ScreensController;
import pl.slowly.team.common.packets.response.Response;

/**
 * Created by Maxym on 2014-12-24.
 */
public class NotAuthorizedResponseStrategy extends Strategy {
    public NotAuthorizedResponseStrategy(ScreensController screensController) {
        super(screensController);
    }

    @Override
    public void execute(Response response) {

    }
}
