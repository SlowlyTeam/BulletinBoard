package pl.slowly.team.client.GUI;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pl.slowly.team.client.connection.ClientController;
import pl.slowly.team.common.data.Category;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author Maxym
 */
public class ChooseCategoryScreenController implements Initializable, ControlledScreen {

    private ScreensController screensController;
    private ClientController clientController;
    private double xOffset;
    private double yOffset;
    private Map<String, Category> categoriesMap;

    @FXML
    private ListView categoriesList;

    @FXML
    private Label warning;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoriesList.setStyle("-fx-border-color: grey; -fx-border-style: solid; -fx-background-color:transparent;");
    }

    @Override
    public void setScreenController(ScreensController screenPage, ClientController clientController) {
        this.screensController = screenPage;
        this.clientController = clientController;
    }

    @Override
    public void load() {
        try {
            clientController.getCategories();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean categoriesListIsEmpty() {
        return categoriesMap == null;
    }

    public void fillCategories(List<Category> categories) {
        categoriesMap = categories.stream()
                .collect(Collectors.toMap(Category::getCategoryName, p -> p));
        categoriesList.setItems(FXCollections.observableArrayList(
                categoriesMap.keySet().stream().sorted().collect(Collectors.toList())));
        Platform.runLater(screensController::hideProgressScreen);
    }

    public void mousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    public void onMouseDragged(MouseEvent event) {
        Stage stage = (Stage) categoriesList.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            screensController.setScreen(Screens.loginScreen, true);
        }
    }

    public Category getCategory() {
        return categoriesMap.get(categoriesList.getSelectionModel().getSelectedItem());
    }

    public void showWarning() {
        warning.setVisible(true);
    }

    public void goToNextScreen() {
        screensController.showProgressScreen();
        MainViewController mainViewController = (MainViewController) screensController.getControlledScreen(Screens.mainScreen);
        Category category = getCategory();
        if (category == null) {
            screensController.hideProgressScreen();
            showWarning();
            return;
        }
        mainViewController.setCategory(category.getCategoryId());
        screensController.setMainScreen(Screens.mainScreen);
    }

}
