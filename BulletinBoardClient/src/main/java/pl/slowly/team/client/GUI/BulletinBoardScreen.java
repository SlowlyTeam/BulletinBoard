package pl.slowly.team.client.GUI;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;


/**
 * Created by Maxym on 2014-11-20.
 */


public class BulletinBoardScreen extends GridPane {

    private int bulletinsNumber;

    public BulletinBoardScreen() {
        setPrefSize(600, 350);
        setAlignment(Pos.CENTER);
        ColumnConstraints columnConstraints = new ColumnConstraints(193);
        RowConstraints rowConstraints = new RowConstraints(170);
        getColumnConstraints().addAll(columnConstraints, columnConstraints, columnConstraints);
        getRowConstraints().addAll(rowConstraints, rowConstraints);
        bulletinsNumber = 0;
    }

    public void addBulletin(BulletinGraphic bulletinGraphic) {
        setHalignment(bulletinGraphic, HPos.CENTER);
        int row = (bulletinsNumber / 3);
        int column = bulletinsNumber % 3;
        bulletinsNumber++;
        add(bulletinGraphic, column, row);
    }

    public void clear() {
        bulletinsNumber = 0;
        getChildren().clear();

    }
}
