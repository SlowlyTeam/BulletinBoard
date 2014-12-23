package GUI;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

/**
 * FXML Controller class
 *
 * @author Maxym
 */
public class LoginScreenController implements ControlledScreen {

    private ScreensController screensController;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    private double xOffset;
    private double yOffset;

    public LoginScreenController() {
        super();
    }

    public void login() {
        // login.getParent().setDisable(true);
        screensController.showProgressScreen();
        new Thread(() -> {
            try {
                doInBackground();
                done(true);
            } catch (Exception e) {
                done(false);
            }
        }).start();
    }

    @Override
    public void setScreenController(ScreensController screenPage) {
        screensController = screenPage;
    }

    @Override
    public void load() {
        if (login.getStyle().contains("-fx-background-color: rgba(230, 10, 10, 0.8)")) {
            login.setStyle("-fx-background-color: rgba(235, 235, 235, 0.5), rgba(0, 0, 0, 0.4), rgb(255, 255, 255);");
            password.setStyle("-fx-background-color: rgba(235, 235, 235, 0.5), rgba(0, 0, 0, 0.4), rgb(255, 255, 255);");
        }
    }

    public void close(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            ((Stage) login.getScene().getWindow()).close();
        }
    }

    public void mousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    public void onMouseDragged(MouseEvent event) {
        Stage stage = (Stage) login.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    private void doInBackground() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        if (!login.getText().equals("Maxym") || !password.getText().equals("open13")) {
            throw new Exception();
        }
    }

    protected void done(boolean isLogged) {
        Platform.runLater(() -> {
            if (isLogged) {
                screensController.hideProgressScreen();
                screensController.setScreen(GUI.chooseCategoryID, false);
            } else {
                screensController.hideProgressScreen();
                login.setStyle("-fx-background-color: rgba(230, 10, 10, 0.8)");
                password.setStyle("-fx-background-color: rgba(230, 10, 10, 0.8)");
            }
            // login.getParent().setDisable(false);
        });
    }

    @FXML
    private void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            ((Stage) login.getScene().getWindow()).close();
        }
    }

}
