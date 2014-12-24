package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * Created by Maxym on 2014-11-21.
 */
public class EditNewBulletin extends AnchorPane {

    final private Title title;
    final private Content content;
    final private Controls controls;
    private BulletinGraphic bulletinGraphic;

    public EditNewBulletin() {
        getStyleClass().add("bulletinBackground");
        getStylesheets().add(getClass().getResource("../styles/bulletin.css").toExternalForm());
        setPrefSize(300, 300);
        setMaxSize(300, 300);

        setStyle("-fx-background-color: #ffc; -fx-border-color: black; -fx-border-style: solid;");

        content = new Content();
        setTopAnchor(content, 50.0);

        title = new Title();

        controls = new Controls();
        setTopAnchor(controls, 265.0);


        getChildren().addAll(title, content, controls);
    }

    public String getContent() {
        return content.getText();
    }

    public void setContent(String content) {
        this.content.setText(content);
    }

    public String getTitle() {
        return title.getText();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public BulletinGraphic getBulletinGraphic() {
        return bulletinGraphic;
    }

    public void setBulletinGraphic(BulletinGraphic bulletinGraphic) {
        this.bulletinGraphic = bulletinGraphic;
    }

    private class Title extends TextField {
        public Title() {
            getStyleClass().removeAll(getStyleClass());
            setPrefSize(300, 45);
            setMaxSize(300, 45);
            setPromptText("Title");
            setTooltip(new Tooltip("Delete"));
            setStyle("-fx-cursor: text");
            getStyleClass().add("title");
        }
    }

    private class Content extends TextArea {
        public Content() {
            getStyleClass().removeAll(getStyleClass());
            setPrefSize(300, 215);
            setMaxSize(300, 215);
            setWrapText(true);
            setPromptText("content");
            setStyle("-fx-cursor: text");
            skinProperty().addListener(new ChangeListener<Skin<?>>() {

                @Override
                public void changed(ObservableValue<? extends Skin<?>> ov, Skin<?> t, Skin<?> t1) {
                    if (t1 != null && t1.getNode() instanceof Region) {
                        Region r = (Region) t1.getNode();
                        r.setBackground(Background.EMPTY);

                        r.getChildrenUnmodifiable().stream().
                                filter(n -> n instanceof Region).
                                map(n -> (Region) n).
                                forEach(n -> n.setBackground(Background.EMPTY));

                        r.getChildrenUnmodifiable().stream().
                                filter(n -> n instanceof Control).
                                map(n -> (Control) n).
                                forEach(c -> c.skinProperty().addListener(this)); // *
                    }
                }
            });
            getStyleClass().add("contents");
        }
    }

    private class Controls extends HBox {
        public Controls() {
            setPrefSize(300, 35);
            setMaxSize(300, 35);
            setAlignment(Pos.CENTER);
            getStyleClass().add("controls");

            Label accept = new Label();
            accept.setPrefSize(35, 35);
            accept.getStyleClass().add("accept");


            Label delete = new Label();
            delete.setPrefSize(35, 35);
            delete.getStyleClass().add("delete");

            getChildren().addAll(accept, delete);
        }
    }
}
