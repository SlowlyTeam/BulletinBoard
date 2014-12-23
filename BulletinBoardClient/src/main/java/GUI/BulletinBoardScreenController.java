package GUI;

import connection.Client;

/**
 * Created by Maxym on 2014-11-20.
 */
public class BulletinBoardScreenController implements ControlledScreen {

    ScreensController bulletinBoardScreensController;
    private Client client;

    @Override
    public void setScreenController(ScreensController screenPage, Client client) {
        this.bulletinBoardScreensController = screenPage;
        this.client = client;
    }

    @Override
    public void load() {

    }
}
