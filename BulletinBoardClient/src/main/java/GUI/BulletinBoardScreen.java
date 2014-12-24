package GUI;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;


/**
 * Created by Maxym on 2014-11-20.
 */


public class BulletinBoardScreen extends GridPane {

    // private ArrayList<Bulletin> bulletinsList = new ArrayList<>();
    private int bulletinsNumber;

    public BulletinBoardScreen() {
        setPrefSize(600, 350);
        //setPadding(new Insets(-10, 7, 0, 0));
        setAlignment(Pos.CENTER);
        ColumnConstraints columnConstraints = new ColumnConstraints(193);
        RowConstraints rowConstraints = new RowConstraints(170);
        getColumnConstraints().addAll(columnConstraints, columnConstraints, columnConstraints);
        getRowConstraints().addAll(rowConstraints, rowConstraints);
        bulletinsNumber = 0;
    }

    public void addBulletin(BulletinGraphic bulletinGraphic) {
        setHalignment(bulletinGraphic, HPos.CENTER);
        //int row = (bulletinsList.size() / 3) % 2;
        //int column = bulletinsList.size() % 3;
        //bulletinsList.add(bulletin);
        int row = (bulletinsNumber / 3);
        int column = bulletinsNumber % 3;
        bulletinsNumber++;
        add(bulletinGraphic, column, row);
    }

    public void clear() {
        //bulletinsList.clear();
        bulletinsNumber = 0;
        getChildren().clear();

    }

    public void remove(BulletinGraphic bulletinGraphic) {
        getChildren().remove(bulletinGraphic);
    }

//    public int getBulletinsNumber() {
//        return bulletinsList.size();
//    }

    //public Bulletin getBulletin(int index) {
//        return bulletinsList.get(index);
//    }
}
