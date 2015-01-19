package pl.slowly.team.client.connection.strategies;

import org.apache.log4j.Logger;
import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.client.connection.ClientController;
import pl.slowly.team.common.packets.Packet;

/**
 * Created by Maxym on 2014-12-23.
 */
public abstract class Strategy {

    protected final static Logger LOGGER = Logger.getLogger(Strategy.class);

    protected ScreensController screensController;

    public Strategy(ScreensController screensController) {
        this.screensController = screensController;
    }

    public abstract void execute(Packet responsePacket);
}
