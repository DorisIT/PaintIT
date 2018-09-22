package ViewObjects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TileBoard extends VBox {

    @FXML HBox hBoxTop;
    @FXML HBox hBoxBottom;
    @FXML VBox vBoxRoot;
    TextField guessTxtf;

    ArrayList<Character> availableTiles;
    String filePath = "/fxml/tileBoard.fxml";
    public TileBoard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filePath));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);


        try {
            fxmlLoader.load();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public TileBoard(ArrayList<Character> availableTiles) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filePath));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        for(Character c: availableTiles){
            TileSlot temp = new TileSlot(c);
            hBoxTop.getChildren().add(temp);
        }
         guessTxtf = new TextField();
        hBoxBottom.getChildren().add(guessTxtf);



    }
}
