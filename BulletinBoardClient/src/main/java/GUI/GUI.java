package GUI;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import connection.ClientController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * @author Maxym
 */
public class GUI extends Application {

    public static final String loginScreenID = "loginScreen";
    public static final String loginScreenFile = "../fxmlFiles/loginScreen.fxml";
    public static final String chooseCategoryID = "chooseCategory";
    public static final String chooseCategoryFile = "../fxmlFiles/chooseCategory.fxml";
    public static final String mainScreenID = "mainScreen";
    public static final String mainScreenFile = "../fxmlFiles/mainView.fxml";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ScreensController screensController = new ScreensController();
        ClientController clientController;

        try {
            clientController = new ClientController(null, 8081, screensController);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        clientController.connectToServer();

        screensController.loadScreen(GUI.loginScreenID, GUI.loginScreenFile, clientController);
        screensController.loadScreen(GUI.chooseCategoryID, GUI.chooseCategoryFile, clientController);
        screensController.loadScreen(GUI.mainScreenID, GUI.mainScreenFile, clientController);
        screensController.addProgressScreen(new ProgressPanel());

        screensController.setScreen(GUI.loginScreenID, false);

        Group root = new Group(screensController);
        Scene scene = new Scene(root);

        scene.setFill(new Color(0.098, 0.1137, 0.13333, 1));

        root.setStyle("-fx-border-radius: 7; -fx-background-radius: 7;");

        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/icon.png")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
