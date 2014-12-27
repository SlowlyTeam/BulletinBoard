package pl.slowly.team.client.connection.strategies.responseStrategies;

import pl.slowly.team.client.GUI.LoginScreenController;
import pl.slowly.team.client.GUI.Screens;
import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.client.connection.strategies.Strategy;
import pl.slowly.team.common.packets.Packet;
import pl.slowly.team.common.packets.response.LogInResponse;

/**
 * Created by Maxym on 2014-12-23.
 */
public class LogInResponseStrategy extends Strategy {

    public LogInResponseStrategy(ScreensController screensController) {
        super(screensController);
    }


    @Override
    public void execute(Packet responsePacket) {
        LogInResponse logInResponse = (LogInResponse) responsePacket;
        LoginScreenController loginScreenController = (LoginScreenController) screensController.getControlledScreen(Screens.loginScreen);
        loginScreenController.logInResponse(logInResponse);
    }
}
