package GUI;

import connection.ClientController;

/**
 * Created by Maxym on 2014-11-20.
 */
public class BulletinBoardScreenController implements ControlledScreen {

    ScreensController bulletinBoardScreensController;
    private ClientController clientController;

    @Override
    public void setScreenController(ScreensController screenPage, ClientController clientController) {
        this.bulletinBoardScreensController = screenPage;
        this.clientController = clientController;
    }

    @Override
    public void load() {

    }
}
