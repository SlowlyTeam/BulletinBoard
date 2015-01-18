package pl.slowly.team.client.GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class EditNewBulletin extends AnchorPane {

    final private Title title;
    final private Content content;
    final private Controls controls;
    final private EditNewBulletin editNewBulletin;
    private BulletinGraphic bulletinGraphic;

    public EditNewBulletin(BulletinGraphic bulletinGraphic) {
        this();
        setEditable(false);
        setBulletinGraphic(bulletinGraphic);
    }

    public EditNewBulletin() {
        getStyleClass().add("bulletinBackground");
        getStylesheets().add(ClassLoader.getSystemResource("styles/bulletin.css").toExternalForm());
        setPrefSize(300, 300);
        setMaxSize(300, 300);

        setStyle("-fx-background-color: #ffc; -fx-border-color: black; -fx-border-style: solid;");

        content = new Content();
        setTopAnchor(content, 35.0);
        setLeftAnchor(content, 0.0);
        setRightAnchor(content, 0.0);
        setBottomAnchor(content, 40.0);

        title = new Title();
        setTopAnchor(title, 0.0);
        setLeftAnchor(title, 0.0);
        setRightAnchor(title, 0.0);


        controls = new Controls();
        setBottomAnchor(controls, 0.0);
        setLeftAnchor(controls, 0.0);
        setRightAnchor(controls, 0.0);

        Label resizeButton = new Label();
        resizeButton.setPrefSize(30, 30);
        resizeButton.setMaxSize(30, 30);
        resizeButton.getStyleClass().add("resizeButton");
        resizeButton.setAlignment(Pos.CENTER);
        resizeButton.setOnMouseClicked(mouseClick -> {
            if (mouseClick.getButton() == MouseButton.PRIMARY)
                resize();
        });
        setBottomAnchor(resizeButton, 0.0);
        setRightAnchor(resizeButton, 0.0);

        getChildren().addAll(title, content, controls, resizeButton);

        editNewBulletin = this;
    }

    public void resize() {
        if (editNewBulletin.getMaxWidth() > 300)
            setMaxSize(300, 300);
        else
            setMaxSize(570, 420);
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

    public void setEditable(boolean isEditable) {
        this.title.setEditable(isEditable);
        this.content.setEditable(isEditable);
        controls.setVisible(isEditable);
    }

    public void setBulletinGraphic(BulletinGraphic bulletinGraphic) {
        this.bulletinGraphic = bulletinGraphic;
        if (bulletinGraphic == null)
            return;
        setContent(bulletinGraphic.getContent());
        setTitle(bulletinGraphic.getTitle());
        setStyle("-fx-background-color: " + bulletinGraphic.getBackground().getFills().get(0).getFill().toString().substring(2) + ";");
        getStyleClass().remove("failure");
    }

    private class Title extends TextField {

        public Title() {
            getStyleClass().removeAll(getStyleClass());
            setPromptText("Title");
            setTooltip(new Tooltip("Title"));
            setStyle("-fx-cursor: text");
            getStyleClass().add("title");
        }
    }

    private class Content extends TextArea {

        public Content() {
            getStyleClass().removeAll(getStyleClass());
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
                                forEach(c -> ((Control) c).skinProperty().addListener(this));
                    }
                }
            });
            getStyleClass().add("contents");
        }
    }

    private class Controls extends HBox {
        public Controls() {
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
