package pl.slowly.team.client.connection.strategies.packetStrategies;

import pl.slowly.team.client.GUI.MainViewController;
import pl.slowly.team.client.GUI.Screens;
import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.client.connection.strategies.Strategy;
import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.packets.Packet;
import pl.slowly.team.common.packets.request.broadcast.SendNewBulletinBroadcast;

public class SendNewBulletinBroadcastStrategy extends Strategy {
    public SendNewBulletinBroadcastStrategy(ScreensController screensController) {
        super(screensController);
    }

    @Override
    public void execute(Packet responsePacket) {
        System.out.println("Broadcast z nowym bulletinem.");
        SendNewBulletinBroadcast newBulletinBroadcast = (SendNewBulletinBroadcast) responsePacket;
        MainViewController mainViewController = (MainViewController) screensController.getControlledScreen(Screens.mainScreen);
        Bulletin bulletin = newBulletinBroadcast.getBulletin();
        mainViewController.addBulletinToView(
                bulletin.getBulletinId(), bulletin.getBulletinTitle(), bulletin.getBulletinContent());
        LOGGER.info("Strategy " + this.getClass().getSimpleName() + " was executed. ");
    }
}
