package pl.slowly.team.client.connection.strategies.responseStrategies;

import pl.slowly.team.client.GUI.MainViewController;
import pl.slowly.team.client.GUI.Screens;
import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.client.connection.strategies.Strategy;
import pl.slowly.team.common.packets.Packet;
import pl.slowly.team.common.packets.helpers.ResponseStatus;
import pl.slowly.team.common.packets.response.EditBulletinResponse;

public class EditBulletinResponseStrategy extends Strategy {

    public EditBulletinResponseStrategy(ScreensController screensController) {
        super(screensController);
    }

    @Override
    public void execute(Packet responsePacket) {
        EditBulletinResponse editBulletinResponse = (EditBulletinResponse) responsePacket;
        MainViewController mainViewController = (MainViewController) screensController.getControlledScreen(Screens.mainScreen);
        if (editBulletinResponse.getResponseStatus() != ResponseStatus.ERROR)
            mainViewController.editUserBulletinInView(editBulletinResponse);
        else mainViewController.editUserBulletinInView(null);
        LOGGER.info("Strategy " + this.getClass().getSimpleName() + " was executed. ");
    }
}
