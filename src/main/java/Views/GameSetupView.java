package Views;

import Controller.TopController;
import Model.Game.Team;
import Util.ButtonFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class GameSetupView extends AnchorPane implements GameScreen {

    @FXML private TextField player1TextField;
    @FXML private TextField player2TextField;
    @FXML private Button startDrawing;
    @FXML private ImageView backButtonImageView;
    @FXML private Label playerOneWrongName;
    @FXML private Label playerTwoWrongName;


    private TopController topController;

    public GameSetupView (FXMLLoader fxmlLoader, TopController topController){
        this.topController = topController;

        fxmlLoader.setLocation(getClass().getResource("/fxml/GameSetupView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        startDrawing.setId(ButtonFactory.createWordRevealViewBtnId());
        startDrawing.setOnAction(e -> {
            final Boolean textFieldOne = checkLabel(player1TextField, playerOneWrongName);
            final Boolean textFieldTwo = checkLabel(player2TextField, playerTwoWrongName);
            //checks if names are entered and if they are valid
            if (textFieldOne && textFieldTwo){
                // Create team and add it to game backend
                setNames();
                // Start word reveal countdown (in WordRevealView)
                // topController.startWordRevealCountdown();
                // Cleat text fields and show next view
                player1TextField.clear(); player2TextField.clear();
                topController.show(startDrawing.getId());
            }
            else {
                //wait
            }
        });

        backButtonImageView.setId(ButtonFactory.createMainMenuViewBtnId());
        backButtonImageView.setOnMouseClicked(e -> {
            final String path = "images/icon_back.png";
            backButtonImageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream((path))));
            topController.show(backButtonImageView.getId());
        });

    }

    /**
     * Changes the image for {@link GameSetupView#backButtonImageView} when it is hovered.
     */
    @FXML
    private void backButtonImageViewEntered (){
        final String path = "images/icon_back_hover.png";
        backButtonImageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream((path))));
    }

    /**
     * Changes the image for {@link GameSetupView#backButtonImageView} when the mouse exits.
     */
    @FXML
    private void backButtonImageViewExited (){
        final String path = "images/icon_back.png";
        backButtonImageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream(path)));
    }

    /**
     * Adds a new team. The names are the ones entered in {@link GameSetupView#player1TextField} and
     * {@link GameSetupView#player2TextField}.
     */
    private void setNames () {

        final String player1 = player1TextField.getText();
        final String player2 = player2TextField.getText();

        topController.addTeam(new Team(player1, player2));
    }

    /**
     * Checks if the input in a textField is correct.
     * @param textField The textField which will be checked
     * @param label the label that should be updated depending on if the input is correct
     * @return a boolean, true if the input is correct and false if it is wrong
     */
    private boolean checkLabel(TextField textField, Label label){
        // Check for empty text fields and illegal characters
        String input = textField.getText();
        //CharSequence illegalCharacters = ":";
        String illegalCharacters = ":!";
        int MAX_LENGTH = 10;
        Tooltip currentToolTip = new Tooltip();

        if (input.isEmpty()) {
            setLabelRed(textField, label, "Enter a name!");
            currentToolTip.setText(
                    "You surely do have a name!"
            );
            textField.setTooltip(currentToolTip);
            return false;
        }

        if (input.length() > MAX_LENGTH) {
            setLabelRed(textField, label, "Too long name!");
            currentToolTip.setText(
                    "Your name can't be longer than " + MAX_LENGTH + " characters"
            );
            textField.setTooltip(currentToolTip);
            return false;
        }

        for (char illegalCharacter : illegalCharacters.toCharArray()) {
            // Iterate over every illegal character and check if textfield contains anything that is not allowed
            if (input.indexOf(illegalCharacter) >= 0) {
                setLabelRed(textField, label, "Illegal name!");
                currentToolTip.setText(
                        "Your name can't contain any of the following characters: " +
                                illegalCharacters + ""
                );
                textField.setTooltip(currentToolTip);
                return false;
            }
        }


        setLabelNormal(textField, label);
        return true;

    }

    /**
     * Changes the style of a label and textField to inform the player that the input in the textField is incorrect.
     * @param textField the textField, which style will be updated
     * @param label the label that will be updated with information to the player
     * @param errorMessage the string that informs the player about what they should change in their name
     */
    private void setLabelRed (TextField textField, Label label, String errorMessage){
        textField.setStyle("-fx-border-color: red;" + "-fx-border-width: 3px;" + "-fx-border-radius: 5px");
        label.setText(errorMessage);
    }

    /**
     * Changes the style of a label and textField back to the normal layout.
     * @param textField the textField, which style will be updated
     * @param label the label that is no longer relevant
     */
    private void setLabelNormal (TextField textField, Label label){
        textField.setStyle("-fx-border-color: lightgrey;" + "-fx-border-width: 1px;" + "-fx-border-radius: 3px");
        label.setText("");
    }


    @Override
    public void init() {

    }

    @Override
    public Pane getPane() {
        return this;
    }
}
