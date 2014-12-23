package GUI;

/**
 * Created by Maxym on 2014-11-20.
 */
public class BulletinBoardScreenController implements ControlledScreen {
    ScreensController bulletinBoardScreensController;

    @Override
    public void setScreenController(ScreensController screenPage) {
        bulletinBoardScreensController = screenPage;
    }

    @Override
    public void load() {

    }
}
