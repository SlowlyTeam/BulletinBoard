package GUI;

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

/**
 * Created by Maxym on 2014-11-21.
 */
public class BulletinGraphic extends AnchorPane {

    final DoubleProperty scaleX = scaleXProperty();
    final DoubleProperty scaleY = scaleYProperty();
    final private Label title;
    final private Content content;
    final private Controls controls;
    private final int bulletinNumber;
    private double rotate;

    public BulletinGraphic(int bulletinNumber) {
        this.bulletinNumber = bulletinNumber;
        getStyleClass().add("bulletinBackground");
        getStylesheets().add(getClass().getResource("../styles/bulletin.css").toExternalForm());
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

        title = new Title();

        controls = new Controls();
        //controls.setVisible(false);
        setTopAnchor(controls, 135.0);

        getChildren().addAll(title, content, controls);

        setOnMouseEntered(mouseEntered -> {
            rotate = getRotate();
            //controls.setVisible(true);
            new Timeline(new KeyFrame(Duration.millis(150), new KeyValue(scaleX, 1.17f)),
                    new KeyFrame(Duration.millis(150), new KeyValue(scaleY, 1.17f)),
                    new KeyFrame(Duration.millis(150), new KeyValue(rotateProperty(), 0f))
            ).play();
            toFront();
        });

        setOnMouseExited(mouseExited -> {
            //controls.setVisible(false);
            new Timeline(new KeyFrame(Duration.millis(150), new KeyValue(scaleX, 1f)),
                    new KeyFrame(Duration.millis(150), new KeyValue(scaleY, 1f)),
                    new KeyFrame(Duration.millis(150), new KeyValue(rotateProperty(), rotate))
            ).play();
        });
    }

//    public Bulletin(int number) {
//
//        getStyleClass().add("bulletinBackground");
//        getStylesheets().add(getClass().getResource("../styles/bulletin.css").toExternalForm());
//        setPrefSize(155, 155);
//        setMaxSize(155, 155);
//
//        if ((number + 1) % 2 == 0) {
//            setStyle("-fx-rotate: 4deg; -fx-background-color: #cfc;");
//        } else if ((number + 1) % 3 == 0) {
//            setStyle("-fx-rotate: -3deg; -fx-background-color: #ccf;");
//        } else if ((number + 1) % 5 == 0) {
//            setStyle("-fx-rotate: 5deg; -fx-background-color: #ffc;");
//        } else {
//            setStyle("-fx-rotate: -6deg; -fx-background-color: #ffc;");
//        }
//
//        contents = new Contents(number);
//        setTopAnchor(contents, 30.0);
//
//        title = new Title(number);
//
//        getChildren().addAll(title, contents);
//
//        setOnMouseEntered(mouseEntered -> {
//            rotate = getRotate();
//            new Timeline(new KeyFrame(Duration.millis(135), new KeyValue(scaleX, 1.17f)),
//                    new KeyFrame(Duration.millis(135), new KeyValue(scaleY, 1.17f)),
//                    new KeyFrame(Duration.millis(135), new KeyValue(rotateProperty(), 0f))
//            ).play();
//            toFront();
//        });
//
//        setOnMouseExited(mouseExited -> {
//            new Timeline(new KeyFrame(Duration.millis(135), new KeyValue(scaleX, 1f)),
//                    new KeyFrame(Duration.millis(135), new KeyValue(scaleY, 1f)),
//                    new KeyFrame(Duration.millis(135), new KeyValue(rotateProperty(), rotate))
//            ).play();
//        });
//    }

    public BulletinGraphic(int number, String title, String content) {
        this(number);
        setTitle(title);
        setContent(content);
    }

    public int getBulletinNumber() {
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
            setPrefSize(155, 30);
            setMaxSize(155, 30);
            getStyleClass().add("title");
        }
    }

    private class Content extends Label {
        public Content() {
            setPrefSize(155, 105);
            setMaxSize(155, 105);
            getStyleClass().add("contents");
        }
    }

    private class Controls extends HBox {
        public Controls() {
            setPrefSize(155, 20);
            setMaxSize(155, 20);
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
