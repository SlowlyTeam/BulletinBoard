package pl.slowly.team.client;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.slowly.team.client.GUI.ProgressPanel;
import pl.slowly.team.client.GUI.Screens;
import pl.slowly.team.client.GUI.ScreensController;
import pl.slowly.team.client.connection.ClientController;

import java.net.InetAddress;

/**
 * @author Maxym
 */
public class BulletinBoard extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ScreensController screensController = new ScreensController();
        ClientController clientController = new ClientController(InetAddress.getLocalHost().getHostAddress(), 8081, screensController);

        screensController.loadScreen(Screens.loginScreen, clientController);
        screensController.loadScreen(Screens.changeCategoryScreen, clientController);
        screensController.loadScreen(Screens.mainScreen, clientController);
        screensController.addProgressScreen(new ProgressPanel());

        screensController.setScreen(Screens.loginScreen, false);

        Group root = new Group(screensController);
        Scene scene = new Scene(root);

        scene.setFill(new Color(0.098, 0.1137, 0.13333, 1));

        root.setStyle("-fx-border-radius: 7; -fx-background-radius: 7;");

        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("images/icon.png")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
