package GUI;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import connection.ClientController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * @author Maxym
 */
public class ChooseCategoryScreenController implements Initializable, ControlledScreen {

    private ScreensController screensController;
    private ClientController clientController;
    private double xOffset;
    private double yOffset;

    @FXML
    private ListView list;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //List<String> values = Arrays.asList("Antyki i Sztuka", "Bilety", "Biuro i Reklama", "Biżuteria i Zegarki", "Delikatesy", "Dla Dzieci", "Dom i Ogród", "Filmy", "Fotografia", "Gry", "Instrumenty", "Kolekcje", "Komputery", "Konsole i automaty", "Książki i Komiksy", "Motoryzacja", "Muzyka", "Nieruchomości", "Odzież, Obuwie, Dodatki", "Przemysł", "Rękodzieło", "RTV i AGD", "Sport i Turystyka", "Sprzęt estradowy, studyjny i DJ-ski", "Telefony i Akcesoria", "Uroda", "Usługi", "Wakacje", "Zdrowie");
        list.setStyle("-fx-border-color: grey; -fx-border-style: solid; -fx-background-color:transparent;");
        //list.setItems(FXCollections.observableList(values));
    }

    @Override
    public void setScreenController(ScreensController screenPage, ClientController clientController) {
        this.screensController = screenPage;
        this.clientController = clientController;
    }

    @Override
    public void load() {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(4);
                List<String> values = Arrays.asList("Antyki i Sztuka", "Bilety", "Biuro i Reklama", "Biżuteria i Zegarki", "Delikatesy", "Dla Dzieci", "Dom i Ogród", "Filmy", "Fotografia", "Gry", "Instrumenty", "Kolekcje", "Komputery", "Konsole i automaty", "Książki i Komiksy", "Motoryzacja", "Muzyka", "Nieruchomości", "Odzież, Obuwie, Dodatki", "Przemysł", "Rękodzieło", "RTV i AGD", "Sport i Turystyka", "Sprzęt estradowy, studyjny i DJ-ski", "Telefony i Akcesoria", "Uroda", "Usługi", "Wakacje", "Zdrowie");
                list.setItems(FXCollections.observableList(values));
                Platform.runLater(() -> screensController.hideProgressScreen());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void mousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    public void onMouseDragged(MouseEvent event) {
        Stage stage = (Stage) list.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            screensController.setScreen(GUI.loginScreenID, true);
        }
    }

    public void goToNextScreen() {
        screensController.showProgressScreen();
        screensController.setMainScreen(GUI.mainScreenID);
    }
}
