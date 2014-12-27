package pl.slowly.team.client.connection.strategies.packetStrategies;

import pl.slowly.team.client.GUI.MainViewController;
import pl.slowly.team.client.GUI.Screens;
import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.client.connection.strategies.Strategy;
import pl.slowly.team.common.packets.Packet;
import pl.slowly.team.common.packets.request.broadcast.DeleteBulletinBroadcast;

public class DeleteBulletinBroadcastStrategy extends Strategy {
    public DeleteBulletinBroadcastStrategy(ScreensController screensController) {
        super(screensController);
    }

    @Override
    public void execute(Packet responsePacket) {
        System.out.println("Broadcast po probie usuniecia bulletinu.");
        DeleteBulletinBroadcast deleteBulletinBroadcast = (DeleteBulletinBroadcast) responsePacket;
        MainViewController mainViewController = (MainViewController) screensController.getControlledScreen(Screens.mainScreen);
        mainViewController.deleteBulletinFromView(deleteBulletinBroadcast.getBulletinId());
    }
}
