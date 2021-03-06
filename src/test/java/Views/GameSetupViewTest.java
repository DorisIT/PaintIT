package Views;

import Controller.TopController;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class GameSetupViewTest extends ApplicationTest{

    TopController topController;
    Scene scene;
    Label playerOneWrongLabel;
    Label playerTwoWrongLabel;

    public <T extends Node> T find(final String query) {
        /** TestFX provides many operations to retrieve elements from the loaded GUI. */
        return lookup(query).query();
    }

    @Override
    public void start (Stage stage){
        topController = new TopController();

        scene = new Scene(topController.getCurrentPane());

        topController.show(GameSetupView.class.getSimpleName());
        stage.setScene(scene);
        stage.show();

        playerOneWrongLabel = (Label) find("#playerOneWrongName");
        playerTwoWrongLabel = (Label) find("#playerTwoWrongName");
    }


    @Test
    public void savingNamesTest () {
        clickOn("#player1TextField");
        write("Test One");
        clickOn("#player2TextField");
        write("Test Two");
        clickOn(".button-play");
        assert (topController.getTeamName().equals("Test One and Test Two"));
    }

    @Test
    public void startDrawingTest (){
        clickOn("#player1TextField");
        write("Test One");
        clickOn("#player2TextField");
        write("Test Two");
        clickOn(".button-play");
        assert (topController.getCurrentPane().getChildren().toString().contains("WordRevealView"));
    }

    @Test
    public void noNamesEnteredTest (){
        clickOn(".button-play");
        assert (playerOneWrongLabel.getText().equals("Enter a name!") &&
                playerTwoWrongLabel.getText().equals("Enter a name!"));
    }

    @Test
    public void illegalNamesEnteredTest (){
        clickOn("#player1TextField");
        write("T1 : T2");
        clickOn(".button-play");
        assert (playerOneWrongLabel.getText().equals("Illegal name!") &&
                playerTwoWrongLabel.getText().equals("Enter a name!"));
    }

    @Test
    public void oneNameEnteredTest (){
        clickOn("#player1TextField");
        write("Test One");
        clickOn(".button-play");
        assert (playerOneWrongLabel.getText().equals("") &&
                playerTwoWrongLabel.getText().equals("Enter a name!"));
    }

}
