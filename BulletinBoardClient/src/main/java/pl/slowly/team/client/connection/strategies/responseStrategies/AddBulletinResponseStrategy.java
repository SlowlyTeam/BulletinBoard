package pl.slowly.team.client.connection.strategies.responseStrategies;

import pl.slowly.team.client.GUI.MainViewController;
import pl.slowly.team.client.GUI.Screens;
import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.client.connection.strategies.Strategy;
import pl.slowly.team.common.packets.Packet;
import pl.slowly.team.common.packets.helpers.ResponseStatus;
import pl.slowly.team.common.packets.response.AddBulletinResponse;

/**
 * Created by Maxym on 2014-12-24.
 */
public class AddBulletinResponseStrategy extends Strategy {
    public AddBulletinResponseStrategy(ScreensController screensController) {
        super(screensController);
    }

    @Override
    public void execute(Packet responsePacket) {
        System.out.println("Odpowiedz po probie dodania bulletinu.");
        AddBulletinResponse addBulletinResponse = (AddBulletinResponse) responsePacket;
        MainViewController mainViewController = (MainViewController) screensController.getControlledScreen(Screens.mainScreen);
        if (addBulletinResponse.getResponseStatus() != ResponseStatus.ERROR)
            mainViewController.addUserBulletinToView(addBulletinResponse.getBulletinId());
        else mainViewController.addUserBulletinToView(-1);
    }
}
