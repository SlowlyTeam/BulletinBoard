package pl.slowly.team.client.connection.strategies.responseStrategies;

import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.client.connection.strategies.Strategy;
import pl.slowly.team.common.packets.Packet;

/**
 * Created by Maxym on 2014-12-24.
 */
public class NotAuthorizedResponseStrategy extends Strategy {
    public NotAuthorizedResponseStrategy(ScreensController screensController) {
        super(screensController);
    }

    @Override
    public void execute(Packet responsePacket) {
        screensController.showExitDialog("Błąd autoryzacji. Aplikacja zostanie zamknięta", null);
    }
}
