package pl.slowly.team.client.GUI;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.Random;

public class BulletinGraphic extends AnchorPane {

    final private DoubleProperty scaleX = scaleXProperty();
    final private DoubleProperty scaleY = scaleYProperty();
    final private Title title;
    final private Content content;
    final private Controls controls;
    private final int bulletinNumber;
    private double rotate;

    public BulletinGraphic(int bulletinNumber, boolean isBelongToUser) {
        this.bulletinNumber = bulletinNumber;
        getStyleClass().add("bulletinBackground");
        getStylesheets().add(ClassLoader.getSystemResource("styles/bulletin.css").toExternalForm());
        setPrefSize(155, 155);
        setMaxSize(155, 155);

        int rand = new Random().nextInt(1000);

        if (rand < 250) {
            setStyle("-fx-rotate: 5deg; -fx-background-color: #ffc;");
        } else if (250 < rand && rand < 500) {
            setStyle("-fx-rotate: -3deg; -fx-background-color: #ccf;");
        } else if (500 < rand && rand < 750) {
            setStyle("-fx-rotate: 4deg; -fx-background-color: #cfc;");
        } else {
            setStyle("-fx-rotate: -6deg; -fx-background-color: #fcf;");
        }

        content = new Content();
        setTopAnchor(content, 30.0);
        setBottomAnchor(content, 20.0);
        setLeftAnchor(content, 0.0);
        setRightAnchor(content, 0.0);

        title = new Title();
        setTopAnchor(title, 0.0);
        setLeftAnchor(title, 0.0);
        setRightAnchor(title, 0.0);

        controls = new Controls();
        controls.setVisible(isBelongToUser);
        setBottomAnchor(controls, 0.0);
        setLeftAnchor(controls, 0.0);
        setRightAnchor(controls, 0.0);

        getChildren().addAll(title, content, controls);

        setOnMouseEntered(mouseEntered -> {
            rotate = getRotate();
            new Timeline(new KeyFrame(Duration.millis(150), new KeyValue(scaleX, 1.17f)),
                    new KeyFrame(Duration.millis(150), new KeyValue(scaleY, 1.17f)),
                    new KeyFrame(Duration.millis(150), new KeyValue(rotateProperty(), 0f))
            ).play();
            toFront();
        });

        setOnMouseExited(mouseExited -> new Timeline(new KeyFrame(Duration.millis(150), new KeyValue(scaleX, 1f)),
                new KeyFrame(Duration.millis(150), new KeyValue(scaleY, 1f)),
                new KeyFrame(Duration.millis(150), new KeyValue(rotateProperty(), rotate))
        ).play());
    }

    public BulletinGraphic(int number, String title, String content, boolean isBelongToUser) {
        this(number, isBelongToUser);
        setTitle(title);
        setContent(content);
    }

    public boolean isEditable() {
        return controls.isVisible();
    }

    public int getBulletinID() {
        return bulletinNumber;
    }

    public String getTitle() {
        return title.getText();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public String getContent() {
        return content.getText();
    }

    public void setContent(String content) {
        this.content.setText(content);
    }

    private class Title extends Label {
        public Title() {
            getStyleClass().add("title");
        }

    }

    private class Content extends Label {
        public Content() {
            getStyleClass().add("contents");
        }
    }

    private class Controls extends HBox {
        public Controls() {
            setAlignment(Pos.CENTER_RIGHT);
            getStyleClass().add("controls");

            Label delete = new Label();
            delete.setPrefSize(20, 20);
            delete.getStyleClass().add("delete");


            Label edit = new Label();
            edit.setPrefSize(20, 20);
            edit.getStyleClass().add("edit");

            Platform.runLater(() -> {
                delete.setTooltip(new Tooltip("Delete note"));
                edit.setTooltip(new Tooltip("Edit note"));
            });

            getChildren().addAll(edit, delete);
        }
    }
}
