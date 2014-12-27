package pl.slowly.team.client.GUI;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import pl.slowly.team.client.connection.ClientController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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
            done(false);
        } else {
            clientController.logIn(login.getText(), password.getText());
        }
    }

    public void logInResponse(LogInResponse logInResponse) {
        done(logInResponse.getResponseStatus().equals(ResponseStatus.AUTHORIZED));
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

//    private void doInBackground() throws Exception {
//        TimeUnit.SECONDS.sleep(1);
//        if (!login.getText().equals("Maxym") || !password.getText().equals("open13")) {
//            throw new Exception();
//        }
//    }

    protected void done(boolean isLogged) {
        Platform.runLater(() -> {
            if (isLogged) {
                screensController.setScreen(Screens.changeCategoryScreen, true);
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
