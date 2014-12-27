package pl.slowly.team.client.connection.strategies;

import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.common.packets.Packet;

/**
 * Created by Maxym on 2014-12-23.
 */
public abstract class Strategy {

    protected ScreensController screensController;

    public Strategy(ScreensController screensController) {
        this.screensController = screensController;
    }

    public abstract void execute(Packet responsePacket);
}
