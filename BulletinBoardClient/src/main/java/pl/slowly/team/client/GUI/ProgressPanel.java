package pl.slowly.team.client.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;

public class ProgressPanel extends HBox {

    public ProgressPanel() {
        final ProgressIndicator progressIndicator = new ProgressIndicator();
        setSpacing(5);
        setAlignment(Pos.CENTER);
        getChildren().add(progressIndicator);
    }
}