/**
 * Created by Maxym on 2014-11-16.
 */
package pl.slowly.team.client.GUI;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.slowly.team.client.connection.ClientController;
import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.data.Category;
import pl.slowly.team.common.packets.helpers.ResponseStatus;
import pl.slowly.team.common.packets.response.AddBulletinResponse;
import pl.slowly.team.common.packets.response.DeleteBulletinResponse;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainViewController implements ControlledScreen, Initializable {

    private final EventHandler onNoteClick;
    private final EventHandler onEditAddNoteClick;
    private CopyOnWriteArrayList<Bulletin> bulletinsList = new CopyOnWriteArrayList<>();
    private EditNewBulletin editNewBulletin;
    private ScreensController screensController;
    private BulletinBoardScreen bulletinBoardScreen;
    private int curPage;
    private double xOffset;
    private double yOffset;
    private ClientController clientController;
    private Integer categoryID;

    @FXML
    private Label exitImage;

    @FXML
    private HBox hBox1;

    public MainViewController() {
        onNoteClick = event -> {
            BulletinGraphic bulletinGraphic = (BulletinGraphic) event.getSource();
            int bulletinNumber = bulletinGraphic.getBulletinNumber();

            if (event.getTarget().toString().contains("delete")) {
                try {
                    clientController.deleteBulletin(bulletinNumber);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (event.getTarget().toString().contains("edit")) {
                bulletinGraphic.setVisible(false);
                editNewBulletin.getStyleClass().remove("failure");
                editNewBulletin.setBulletinGraphic(bulletinGraphic);
                editNewBulletin.setStyle("-fx-background-color: " + bulletinGraphic.getBackground().getFills().get(0).getFill().toString().substring(2) + ";");
                editNewBulletin.setTitle(bulletinGraphic.getTitle());
                editNewBulletin.setContent(bulletinGraphic.getContent());
                screensController.showOnScreen(editNewBulletin);
            }
        };

        onEditAddNoteClick = ed -> {
            if ((ed instanceof KeyEvent && ((KeyEvent) ed).getCode() == KeyCode.ESCAPE) || ed.getTarget().toString().contains("delete")) {
                if (editNewBulletin.getBulletinGraphic() != null) {
                    editNewBulletin.getBulletinGraphic().setVisible(true);
                }
                screensController.hideOnScreen();
                return;
            }
            if (ed.getTarget().toString().contains("accept")) {
                BulletinGraphic bulletinGraphic = editNewBulletin.getBulletinGraphic();
                editNewBulletin.setDisable(true);
                screensController.showProgressScreen();

                if (bulletinGraphic != null) {
                    try {
                        System.out.println("Editing bulletin...");
                        clientController.editBulletin(
                                new Bulletin(bulletinGraphic.getBulletinNumber(), editNewBulletin.getTitle(), editNewBulletin.getContent(), true));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        clientController.addBulletin(editNewBulletin.getTitle(), editNewBulletin.getContent());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public void refresh() {
        if ((curPage - 1) * 6 == bulletinsList.size() && curPage != 1)
            Platform.runLater(() -> setPage(--curPage));
        else {
            Platform.runLater(() -> setPage(curPage));
        }
    }

    /**
     * Adding bulletin after broadcast message.
     */
    public void addBulletinToView(int bulletinID, String bulletinTitle, String bulletinContent) {
        bulletinsList.add(0, new Bulletin(bulletinID, bulletinTitle, bulletinContent, false));
        Platform.runLater(() -> {
            setPage(curPage = 1);
            screensController.hideOnScreen();
        });
    }

    /**
     * Add bulletin after successfully processed request to add new bulletin and getting response;
     */
    public void addUserBulletinToView(AddBulletinResponse deleteBulletinResponse) {
        Platform.runLater(() -> {
            editNewBulletin.setDisable(false);
            screensController.hideProgressScreen();
        });
        if (deleteBulletinResponse.getResponseStatus() == ResponseStatus.OK) {
            bulletinsList.add(0, new Bulletin(deleteBulletinResponse.getBulletinId(), editNewBulletin.getTitle(), editNewBulletin.getContent(), true));
            Platform.runLater(() -> {
                setPage(curPage = 1);
                screensController.hideOnScreen();
            });
        } else {
            editNewBulletin.getStyleClass().add("failure");
        }
    }

    /**
     * Call method after trying to delete bulletin and getting response.
     */
    public void deletedBulletinInfo(DeleteBulletinResponse deleteBulletinResponse) {
        if (deleteBulletinResponse.getResponseStatus() == ResponseStatus.OK) {
            deleteBulletinFromView(deleteBulletinResponse.getBulletinId());
        } else {
            //TODO dialog o nie niepowodzeniu usuniecia ogloszenia
        }
    }


    /**
     * Delete bulletin after broadcast message.
     */
    public void deleteBulletinFromView(int bulletinId) {
        Bulletin bul = bulletinsList.stream().filter(bulletin -> bulletin.getBulletinId() == bulletinId).findFirst().get();
        if (bulletinsList.remove(bul)) {
            System.out.println("Usunalem bulletin");
            Platform.runLater(this::refresh);
        }
    }

    /**
     * Edit bulletin after trying to edit bulletin and getting response from server;
     */
    public void editUserBulletinInView(Bulletin bulletin) {

        Platform.runLater(() -> {
            editNewBulletin.setDisable(false);
            screensController.hideProgressScreen();
        });

        if (bulletin == null) {
            editNewBulletin.getStyleClass().add("failure");
            return;
        }

        for (int i = 0; i < bulletinsList.size(); i++) {
            if (bulletinsList.get(i).getBulletinId() == bulletin.getBulletinId()) {

                bulletinsList.set(i, bulletin);
                System.out.println("Editing bulletin.");
                Platform.runLater(() -> {
                    setPage(curPage);
                    screensController.hideOnScreen();
                });
                break;
            }
        }
    }

    /**
     * Edit bulletin after broadcast message.
     */
    public void editBulletinInView(Bulletin bulletin) {
        for (int i = 0; i < bulletinsList.size(); i++) {
            if (bulletinsList.get(i).getBulletinId() == bulletin.getBulletinId()) {
                System.out.println("Editing bulletin.");
                bulletinsList.set(i, bulletin);
                break;
            }
            //TODO odswiezenie widoku jesli edytowane ogloszenie znajduje sie na aktualnie przegladanej stronie
        }
    }


    @Override
    public void setScreenController(ScreensController screenPage, ClientController clientController) {
        screensController = screenPage;
        this.clientController = clientController;
    }

    @Override
    public void load() {
        try {
            clientController.getBulletins(new ArrayList<>(Arrays.asList(categoryID)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBulletins(List<Bulletin> bulletinsList) {
        this.bulletinsList = new CopyOnWriteArrayList<>(bulletinsList);

        for (int k = 0; k < 6 && k < bulletinsList.size(); ++k) {
            Bulletin bulletin = bulletinsList.get(k);
            BulletinGraphic bulletinGraphic = new BulletinGraphic(bulletin.getBulletinId(), bulletin.getBulletinTitle(), bulletin.getBulletinContent(), bulletin.isBelongToUser());
            bulletinGraphic.setOnMouseClicked(onNoteClick);
            Platform.runLater(() -> bulletinBoardScreen.addBulletin(bulletinGraphic));
        }
        Platform.runLater(screensController::hideProgressScreen);

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
            ((Stage) exitImage.getScene().getWindow()).close();
        }
    }

    @FXML
    private void mousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void onMouseDragged(MouseEvent event) {

        Stage stage = (Stage) exitImage.getScene().getWindow();
        if (!(event.getTarget() instanceof HBox) && !(event.getTarget() instanceof VBox))
            return;
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bulletinBoardScreen = new BulletinBoardScreen();
        editNewBulletin = new EditNewBulletin();
        editNewBulletin.setOnMouseClicked(onEditAddNoteClick);
        editNewBulletin.setOnKeyReleased(onEditAddNoteClick);

        hBox1.getChildren().add(bulletinBoardScreen);
        curPage = 1;

    }

    public void setPage(int pageNumber) {

        screensController.showProgressScreen();
        new Thread(() -> {
            Platform.runLater(bulletinBoardScreen::clear);
            int fromBulletin = (pageNumber - 1) * 6;
            int toBulletin = fromBulletin + 6;
            for (; fromBulletin != toBulletin && fromBulletin != bulletinsList.size(); ++fromBulletin) {
                Bulletin bulletin = bulletinsList.get(fromBulletin);
                BulletinGraphic bulletinGraphic = new BulletinGraphic(bulletin.getBulletinId(), bulletin.getBulletinTitle(), bulletin.getBulletinContent(), bulletin.isBelongToUser());
                bulletinGraphic.setOnMouseClicked(onNoteClick);
                Platform.runLater(() -> bulletinBoardScreen.addBulletin(bulletinGraphic));
            }
            Platform.runLater(screensController::hideProgressScreen);
        }).start();
    }


    public void setNextPage() {
        if (curPage * 6 >= bulletinsList.size())
            return;

        setPage(++curPage);

    }

    public void setPreviousPage() {
        if (curPage == 1)
            return;

        setPage(--curPage);
    }

    @FXML
    private void onKeyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                setPreviousPage();
                break;
            case RIGHT:
                setNextPage();
                break;
        }
    }

    public void addNote(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            editNewBulletin.getStyleClass().remove("failure");
            editNewBulletin.setBulletinGraphic(null);
            editNewBulletin.setStyle("-fx-background-color: white;");
            editNewBulletin.setTitle(null);
            editNewBulletin.setContent(null);
            screensController.showOnScreen(editNewBulletin);
        }
    }

    public void changeCategory(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            AnchorPane chooseCategoryScreen = (AnchorPane) screensController.getScreen(Screens.changeCategoryScreen);
            Button ok = (Button) chooseCategoryScreen.lookup("#button1");
            ok.setOnAction(action -> {
                bulletinBoardScreen.clear();
                bulletinsList.clear();
                screensController.hideOnScreen();
                screensController.showProgressScreen();
                ChooseCategoryScreenController chooseCategoryScreenController = (ChooseCategoryScreenController) screensController.getControlledScreen(Screens.changeCategoryScreen);
                Category category = chooseCategoryScreenController.getCategory();

                if (category == null) {
                    screensController.hideProgressScreen();
                    chooseCategoryScreenController.showWarning();
                    return;
                } else {
                    setCategory(category.getCategoryId());
                    load();
                }

            });
            chooseCategoryScreen.setOnKeyReleased(hide -> screensController.hideOnScreen());
            screensController.showOnScreen(screensController.getScreen(Screens.changeCategoryScreen));
        }
    }

    public void setCategory(Integer categoryID) {
        this.categoryID = categoryID;
    }

}
