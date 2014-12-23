package GUI;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import connection.ClientController;
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

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Maxym
 */
public class ScreensController extends StackPane {

    private final HashMap<String, Pair<Parent, ControlledScreen>> screens = new HashMap<>();

    public ControlledScreen getControlledScreen(String controlledScreenID) {
        return screens.get(controlledScreenID).getValue();
    }

    public void addScreen(String name, Pair<Parent, ControlledScreen> screen) {
        screens.put(name, screen);
    }

    public Node getScreen(String name) {
        return screens.get(name).getKey();
    }

    public boolean loadScreen(String name, String resource, ClientController clientController) {
        try {
            System.out.println(resource);
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = myLoader.load();
            ControlledScreen myScreenColector = myLoader.getController();
            myScreenColector.setScreenController(this, clientController);
            addScreen(name, new Pair<>(loadScreen, myScreenColector));
            return true;
        } catch (IOException ioe) {
            return false;
        }
    }

    public boolean showProgressScreen() {

        if (!getChildren().isEmpty()) {
            //getChildren().get(0).setDisable(true);
            getChildren().add(1, screens.get("progressScreen").getKey());
            setOpacity(0.5f);
            return true;
        } else {
            return false;
        }
    }

    public boolean hideProgressScreen() {
        System.out.println("hide");
        if (!getChildren().isEmpty()) {
            getChildren().remove(1);
            setOpacity(1.0f);
            //getChildren().get(0).setDisable(false);
            return true;
        } else {
            return false;
        }
    }

    public void setMainScreen(String name) {
        if (screens.get(name) != null) {
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
                                getChildren().add(0, screens.get(name).getKey());
                                new Timeline(new KeyFrame(Duration.millis(750),
                                        height -> new Timeline(new KeyFrame(Duration.millis(500),
                                                fadeIn -> new Timeline(new KeyFrame(Duration.millis(500),
                                                        load -> screens.get(name).getValue().load(), new KeyValue(opacity, 1.0))).play(),
                                                new KeyValue(stageHeightProperty, 455, Interpolator.EASE_BOTH))).play(),
                                        new KeyValue(stageWidthProperty, 600, Interpolator.EASE_BOTH))).play();
                            }, new KeyValue(opacity, 0.0))
            ).play();

        }
    }

    public boolean setScreen(final String name, boolean init) {
        if (screens.get(name) != null) {
            if (init) {
                screens.get(name).getValue().load();
            }
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {

                new Timeline(
                        new KeyFrame(Duration.millis(250), (ActionEvent) -> {
                            getChildren().remove(0);
                            getChildren().add(0, screens.get(name).getKey());
                            new Timeline(new KeyFrame(Duration.millis(250), new KeyValue(opacity, 1.0))).play();
                        }, new KeyValue(opacity, 0.0))
                ).play();

            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name).getKey());
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
        //getChildren().get(0).setOpacity(0.3);
        getChildren().add(1, screen);
        new Timeline(new KeyFrame(Duration.millis(200),new KeyValue(getChildren().get(0).opacityProperty(),  0.1, Interpolator.EASE_IN))).play();
        getChildren().get(0).setDisable(true);

    }

    public void hideOnScreen() {
        getChildren().remove(1);
        getChildren().get(0).setDisable(false);
        getChildren().get(0).setOpacity(1);
    }
}
