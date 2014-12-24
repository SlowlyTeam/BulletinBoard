package connection.strategies;

import GUI.GUI;
import GUI.ScreensController;
import GUI.LoginScreenController;
import pl.slowly.team.common.packets.Packet;
import pl.slowly.team.common.packets.response.Response;

/**
 * Created by Maxym on 2014-12-23.
 */
public class LogInResponseStrategy extends Strategy{

    public LogInResponseStrategy(ScreensController screensController){
        super(screensController);
    }


    @Override
    public void execute(Packet packet) {
        LoginScreenController loginScreenController = (LoginScreenController) screensController.getControlledScreen(GUI.loginScreenID);
        loginScreenController.logInResponse((Response)packet);
    }
}
