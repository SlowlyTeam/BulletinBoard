package pl.slowly.team.client.connection.strategies.responseStrategies;

import pl.slowly.team.client.GUI.MainViewController;
import pl.slowly.team.client.GUI.Screens;
import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.client.connection.strategies.Strategy;
import pl.slowly.team.common.packets.Packet;
import pl.slowly.team.common.packets.helpers.ResponseStatus;
import pl.slowly.team.common.packets.response.AddBulletinResponse;
import pl.slowly.team.common.packets.response.DeleteBulletinResponse;

public class DeleteBulletinResponseStrategy extends Strategy {
    public DeleteBulletinResponseStrategy(ScreensController screensController) {
        super(screensController);
    }

    @Override
    public void execute(Packet responsePacket) {
        System.out.println("Odpowiedz po probie usuniecia bulletinu.");
        DeleteBulletinResponse deleteBulletinResponse = (DeleteBulletinResponse) responsePacket;
        MainViewController mainViewController = (MainViewController) screensController.getControlledScreen(Screens.mainScreen);
        if (deleteBulletinResponse.getResponseStatus() != ResponseStatus.ERROR)
            mainViewController.deletedBulletinInfo(true);
        else mainViewController.deletedBulletinInfo(false);
    }
}
