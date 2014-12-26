package pl.slowly.team.client.GUI;

/**
 * Created by Maxym on 2014-12-26.
 */
public enum Screens {

    loginScreen("loginScreen", "../../../../../fxmlFiles/loginScreen.fxml"),
    changeCategoryScreen("chooseCategory", "../../../../../fxmlFiles/chooseCategory.fxml"),
    mainScreen("mainScreen", "../../../../../fxmlFiles/mainView.fxml");

    public final String ID;
    public final String RESOURCE;

    private Screens(String ID, String RESOURCE) {
        this.ID = ID;
        this.RESOURCE = RESOURCE;

    }
}
