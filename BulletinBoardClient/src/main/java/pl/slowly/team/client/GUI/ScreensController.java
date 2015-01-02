package pl.slowly.team.client.GUI;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.sun.istack.internal.Nullable;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import pl.slowly.team.client.connection.ClientController;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Maxym
 */
public class ScreensController extends StackPane {

    private final HashMap<String, Pair<Parent, ControlledScreen>> screens = new HashMap<>();

    public ControlledScreen getControlledScreen(final Screens screen) {
        return screens.get(screen.ID).getValue();
    }

    public void addScreen(String name, Pair<Parent, ControlledScreen> screen) {
        screens.put(name, screen);
    }

    public Node getScreen(Screens screen) {
        return screens.get(screen.ID).getKey();
    }

    public boolean loadScreen(Screens screen, ClientController clientController) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(screen.RESOURCE));
            Parent loadScreen = myLoader.load();
            ControlledScreen myScreenColector = myLoader.getController();
            myScreenColector.setScreenController(this, clientController);
            addScreen(screen.ID, new Pair<>(loadScreen, myScreenColector));
            System.out.println(screen.ID + " laoded");
            return true;
        } catch (IOException ioe) {
            return false;
        }
    }

    public boolean showProgressScreen() {
        System.out.println("show");
        if (!getChildren().isEmpty()) {
            //getChildren().get(0).setDisable(true);
            getChildren().add(screens.get("progressScreen").getKey());
            setOpacity(0.5f);
            return true;
        } else {
            return false;
        }
    }

    public boolean hideProgressScreen() {
        System.out.println("hide");
        if (!getChildren().isEmpty()) {
            getChildren().remove(screens.get("progressScreen").getKey());
            setOpacity(1.0f);
            //getChildren().get(0).setDisable(false);
            return true;
        } else {
            return false;
        }
    }

    public void setMainScreen(Screens mainScreen) {
        if (screens.get(mainScreen.ID) != null) {
            final Stage stage = (Stage) getParent().getScene().getWindow();

            final DoubleProperty opacity = opacityProperty();


            SimpleDoubleProperty stageWidthProperty = new SimpleDoubleProperty(stage.getWidth());
            stageWidthProperty.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldStageWidth, Number newStageWidth) {
                    stage.setWidth(newStageWidth.doubleValue());
                    stage.centerOnScreen();
                }
            });

            SimpleDoubleProperty stageHeightProperty = new SimpleDoubleProperty(stage.getHeight());
            stageHeightProperty.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldStageHeight, Number newStageHeight) {
                    stage.setHeight(newStageHeight.doubleValue());
                    stage.centerOnScreen();
                }
            });

            new Timeline(
                    new KeyFrame(Duration.millis(250),
                            width -> {
                                getChildren().remove(0);
                                getChildren().add(0, screens.get(mainScreen.ID).getKey());
                                new Timeline(new KeyFrame(Duration.millis(750),
                                        height -> new Timeline(new KeyFrame(Duration.millis(500),
                                                fadeIn -> new Timeline(new KeyFrame(Duration.millis(500),
                                                        load -> screens.get(mainScreen.ID).getValue().load(), new KeyValue(opacity, 1.0))).play(),
                                                new KeyValue(stageHeightProperty, 455, Interpolator.EASE_BOTH))).play(),
                                        new KeyValue(stageWidthProperty, 600, Interpolator.EASE_BOTH))).play();
                            }, new KeyValue(opacity, 0.0))
            ).play();

        }
    }

    public boolean setScreen(final Screens screen, boolean init) {
        if (screens.get(screen.ID) != null) {
            if (init) {
                screens.get(screen.ID).getValue().load();
            }
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {

                new Timeline(
                        new KeyFrame(Duration.millis(250), (ActionEvent) -> {
                            getChildren().remove(0);
                            getChildren().add(0, screens.get(screen.ID).getKey());
                            new Timeline(new KeyFrame(Duration.millis(250), new KeyValue(opacity, 1.0))).play();
                        }, new KeyValue(opacity, 0.0))
                ).play();

            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(screen.ID).getKey());
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(Duration.millis(1500), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }

            return true;
        } else {
            System.out.println("screen hasn't been loaded!!!");
            return false;
        }
    }

    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }

    public void addProgressScreen(ProgressPanel progressPanel) {
        addScreen("progressScreen", new Pair<>(progressPanel, null));
    }


    public void showOnScreen(Node screen) {
        getChildren().add(1, screen);
        new Timeline(new KeyFrame(Duration.millis(200), new KeyValue(getChildren().get(0).opacityProperty(), 0.1, Interpolator.EASE_IN))).play();
        getChildren().get(0).setDisable(true);

    }

    public void hideOnScreen() {
        getChildren().remove(1);
        getChildren().get(0).setDisable(false);
        getChildren().get(0).setOpacity(1);
    }

    public void showExitDialog(String message, @Nullable Exception exception) {
        if (exception != null) {
            Dialogs.create()
                    .owner(getParent().getScene().getWindow())
                    .title("Error")
                    .masthead(message)
                    .showException(exception);
        } else {
            Dialogs.create()
                    .owner(getParent().getScene().getWindow())
                    .title("Error")
                    .message(message)
                    .showError();
        }
        close();
    }

    private void close() {
        ((Stage) getParent().getScene().getWindow()).close();
    }

    public void showWarning(String message) {
        Dialogs.create()
                .owner(getParent().getScene().getWindow())
                .title("Warning")
                .message(message)
                .showWarning();
    }

    public Action showConfirmDialog(String message) {
        return Dialogs.create()
                .owner(getParent().getScene().getWindow())
                .actions(Dialog.Actions.YES, Dialog.Actions.NO)
                .title("Confirm")
                .message(message)
                .showConfirm();
    }
}
