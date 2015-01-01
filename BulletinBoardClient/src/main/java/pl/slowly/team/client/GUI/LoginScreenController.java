package pl.slowly.team.client.GUI;/*
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
import pl.slowly.team.client.connection.ClientController;
import pl.slowly.team.common.packets.helpers.ResponseStatus;
import pl.slowly.team.common.packets.response.LogInResponse;

import java.io.IOException;

/**
 * FXML Controller class
 *
 * @author Maxym
 */
public class LoginScreenController implements ControlledScreen {

    private ScreensController screensController;
    private ClientController clientController;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    private double xOffset;
    private double yOffset;

    public void login() throws IOException {
        screensController.showProgressScreen();
        if (login.getText().isEmpty() || password.getText().isEmpty()) {
            done(false, null);
        } else {
            clientController.logIn(login.getText(), password.getText());
        }
    }

    public void logInResponse(LogInResponse logInResponse) {
        done(logInResponse.getResponseStatus().equals(ResponseStatus.AUTHORIZED), logInResponse.getCategory());
    }

    @Override
    public void setScreenController(ScreensController screenPage, ClientController clientController) {
        this.screensController = screenPage;
        this.clientController = clientController;
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
            try {
                clientController.disconnectFromServer();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    protected void done(boolean isLogged, Integer categoryID) {
        Platform.runLater(() -> {
            if (isLogged) {
                if (categoryID != null) {
                    MainViewController mainViewController = (MainViewController) screensController.getControlledScreen(Screens.mainScreen);
                    mainViewController.setCategory(categoryID);
                    screensController.setMainScreen(Screens.mainScreen);
                } else {
                    screensController.setScreen(Screens.changeCategoryScreen, true);
                }
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
