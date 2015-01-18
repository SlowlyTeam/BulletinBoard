package pl.slowly.team.client.connection.strategies.packetStrategies;

import pl.slowly.team.client.GUI.MainViewController;
import pl.slowly.team.client.GUI.Screens;
import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.client.connection.strategies.Strategy;
import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.packets.Packet;
import pl.slowly.team.common.packets.request.broadcast.EditBulletinBroadcast;

public class EditBulletinBroadcastStrategy extends Strategy {

    public EditBulletinBroadcastStrategy(ScreensController screensController) {
        super(screensController);
    }

    @Override
    public void execute(Packet responsePacket) {
        System.out.println("Broadcast ze zmienionym bulletinem.");
        EditBulletinBroadcast editBulletinBroadcast = (EditBulletinBroadcast) responsePacket;
        MainViewController mainViewController = (MainViewController) screensController.getControlledScreen(Screens.mainScreen);
        Bulletin bulletin = editBulletinBroadcast.getBulletin();
        mainViewController.editBulletinInView(bulletin);
    }
}
