package pl.slowly.team.client.connection.strategies;

import pl.slowly.team.client.GUI.GUI;
import pl.slowly.team.client.GUI.Screens;
import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.client.GUI.LoginScreenController;
import pl.slowly.team.common.packets.response.LogInResponse;
import pl.slowly.team.common.packets.response.Response;

/**
 * Created by Maxym on 2014-12-23.
 */
public class LogInResponseStrategy extends Strategy{

    public LogInResponseStrategy(ScreensController screensController){
        super(screensController);
    }


    @Override
    public void execute(Response response) {
        LoginScreenController loginScreenController = (LoginScreenController) screensController.getControlledScreen(Screens.loginScreen);
        loginScreenController.logInResponse((LogInResponse)response);
    }
}
