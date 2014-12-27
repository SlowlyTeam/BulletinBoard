package pl.slowly.team.client.connection.Strategies.packetStrategies;

import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.client.connection.Strategies.Strategy;
import pl.slowly.team.common.packets.Packet;

/**
 * Created by Maxym on 2014-12-24.
 */
public class DeleteBulletinBroadcastStrategy extends Strategy {
    public DeleteBulletinBroadcastStrategy(ScreensController screensController) {
        super(screensController);
    }

    @Override
    public void execute(Packet responsePacket) {
        //TODO
    }
}
