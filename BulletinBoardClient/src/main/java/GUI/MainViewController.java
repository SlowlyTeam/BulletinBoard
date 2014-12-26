/**
 * Created by Maxym on 2014-11-16.
 */
package GUI;

import connection.ClientController;
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
import pl.slowly.team.common.data.Bulletin;
import pl.slowly.team.common.data.Category;

import java.io.IOException;
import java.net.URL;
import java.util.*;
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
                for (Bulletin bul : bulletinsList) {
                    if (bul.getBulletinId() == bulletinNumber) {
                        bulletinsList.remove(bul);
                        break;
                    }
                }
                bulletinBoardScreen.remove(bulletinGraphic);

                if ((curPage - 1) * 6 == bulletinsList.size() && curPage != 1)
                    setPage(--curPage);
                else {
                    setPage(curPage);
                }
            } else if (event.getTarget().toString().contains("edit")) {
                bulletinGraphic.setVisible(false);
                editNewBulletin.setBulletinGraphic(bulletinGraphic);
                editNewBulletin.setStyle("-fx-background-color: " + bulletinGraphic.getBackground().getFills().get(0).getFill().toString().substring(2) + ";");
                editNewBulletin.setTitle(bulletinGraphic.getTitle());
                editNewBulletin.setContent(bulletinGraphic.getContent());
                screensController.showOnScreen(editNewBulletin);
            }
        };

        onEditAddNoteClick = ed -> {
            if (ed instanceof KeyEvent && ((KeyEvent) ed).getCode() == KeyCode.ESCAPE) {
                if (editNewBulletin.getBulletinGraphic() != null) {
                    editNewBulletin.getBulletinGraphic().setVisible(true);
                }
                screensController.hideOnScreen();
                return;
            }
            if (ed.getTarget().toString().contains("delete")) {
                if (editNewBulletin.getBulletinGraphic() != null) {
                    editNewBulletin.getBulletinGraphic().setVisible(true);
                }
                screensController.hideOnScreen();
            } else if (ed.getTarget().toString().contains("accept")) {
                BulletinGraphic bulletinGraphic = editNewBulletin.getBulletinGraphic();
                if (bulletinGraphic != null) {
                    int bulletinNumber = bulletinGraphic.getBulletinNumber();
                    for (Bulletin bul : bulletinsList) {
                        if (bul.getBulletinId() == bulletinNumber) {
                            bul.setBulletinTitle(editNewBulletin.getTitle());
                            bul.setBulletinContent(editNewBulletin.getContent());
                            break;
                        }
                    }
                    bulletinGraphic.setVisible(true);
                    setPage(curPage);
                } else {
                    bulletinsList.add(0, new Bulletin(new Random().nextInt(), editNewBulletin.getTitle(), editNewBulletin.getContent(), true));
                    setPage(curPage = 1);
                }
                screensController.hideOnScreen();
            }
        };
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
//        new Thread(() -> {
//            for (int i = 0; i < 100; ++i) {
//                bulletinsList.add(new Bulletin(i, "Note #" + i, "content number #" + i));
//            }
//            for (int k = 0; k < 6; ++k) {
//                BulletinGraphic bulletinGraphic = new BulletinGraphic(bulletinsList.get(k).getBulletinId(), bulletinsList.get(k).getBulletinTitle(), bulletinsList.get(k).getBulletinContent());
//                bulletinGraphic.setOnMouseClicked(onNoteClick);
//                Platform.runLater(() -> bulletinBoardScreen.addBulletin(bulletinGraphic));
//            }
//            Platform.runLater(() -> screensController.hideProgressScreen());
//        }).start();

    }

    public void setBulletins(List<Bulletin> bulletinsList) {
        this.bulletinsList = new CopyOnWriteArrayList<>(bulletinsList);

        for (int k = 0; k < 6; ++k) {
            Bulletin bulletin = bulletinsList.get(k);
            BulletinGraphic bulletinGraphic = new BulletinGraphic(bulletin.getBulletinId(), bulletin.getBulletinTitle(), bulletin.getBulletinContent(), bulletin.isBelongToUser());
            bulletinGraphic.setOnMouseClicked(onNoteClick);
            Platform.runLater(() -> bulletinBoardScreen.addBulletin(bulletinGraphic));
        }
        Platform.runLater(screensController::hideProgressScreen);

    }

    public void close(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
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
            editNewBulletin.setBulletinGraphic(null);
            editNewBulletin.setStyle("-fx-background-color: white;");
            editNewBulletin.setTitle(null);
            editNewBulletin.setContent(null);
            screensController.showOnScreen(editNewBulletin);
        }
    }

    public void changeCategory(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            AnchorPane chooseCategoryScreen = (AnchorPane) screensController.getScreen(GUI.chooseCategoryID);
            Button ok = (Button) chooseCategoryScreen.lookup("#button1");
            ok.setOnAction(action -> {
                bulletinBoardScreen.clear();
                bulletinsList.clear();
                screensController.hideOnScreen();
                screensController.showProgressScreen();
                load();
            });
            chooseCategoryScreen.setOnKeyReleased(hide -> screensController.hideOnScreen());
            screensController.showOnScreen(screensController.getScreen(GUI.chooseCategoryID));
        }
    }

    public void setCategory(Integer categoryID) {
        this.categoryID = categoryID;
    }
}
