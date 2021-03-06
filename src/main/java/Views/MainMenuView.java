package Views;

import Controller.TopController;
import Model.Game.HighScoreList;
import Model.Game.Score;
import Util.ButtonFactory;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MainMenuView extends AnchorPane implements GameScreen{

    @FXML private AnchorPane mainMenuAnchorPane;
    @FXML private Button play;
    @FXML private Button howToPlay;
    @FXML private Button highScore;

    @FXML private AnchorPane howToPlayAnchorPane;
    @FXML private AnchorPane lightBoxAnchorPane;
    @FXML private ImageView closeButtonImageView;
    @FXML private TextArea instructionsTextArea;
    private final String INSTRUCTTIONS_URL = "src/main/resources/instructions.txt";

    @FXML private AnchorPane highScoreAnchorPane;
    @FXML private ImageView closeHighScoreImageView;
    @FXML private TextArea teamNameTextArea;

    TopController topController;

    public MainMenuView (FXMLLoader fxmlLoader, TopController topController) {
        this.topController = topController;
        fxmlLoader.setLocation(getClass().getResource("/fxml/MainMenuView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        showMainMenu();

        play.setId(ButtonFactory.createGameSetupViewBtnId());
        play.setOnAction(e -> {
            topController.show(play.getId());
        });

        howToPlay.setOnAction(e -> {
                showHowToPlay();
        });

        highScore.setOnAction(e -> {
            loadHighScoreList();
            showHighScore();
        });

        lightBoxAnchorPane.setOnMouseClicked(e ->{
                showMainMenu();
        });

        howToPlayAnchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseTrap(event);
            }
        });

        highScoreAnchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseTrap(event);
            }
        });

        getInstructionsText();

    }

    private void getInstructionsText() {
        // Fetch instructions from instruction.txt
        StringBuilder sb = new StringBuilder();
        // Prepare to read from backend
        try {
            final Scanner sc = new Scanner(new File(INSTRUCTTIONS_URL));

            while (sc.hasNextLine()) {
                // Append instruction (line) to StringBuilder
                final String currentInstruction = sc.nextLine();
                sb.append(currentInstruction + '\n');
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Done with iterating over all lines, write instructions to instructions text area!
        instructionsTextArea.setText(sb.toString());
    }

    /**
     * Changes the image for {@link MainMenuView#closeButtonImageView} when the mouse hovers over it.
     */
    @FXML
    private void closeButtonMouseEntered(){
        changeIconCloseHover(closeButtonImageView);
    }

    /**
     * Changes the image for {@link MainMenuView#closeButtonImageView} when it is clicked on,
     * as well as changes the focus to {@link MainMenuView#mainMenuAnchorPane}.
     */
    @FXML
    private void closeButtonMouseClicked(){
        changeIconClose(closeButtonImageView);
        showMainMenu();
    }

    /**
     * Changes the image for {@link MainMenuView#closeButtonImageView} when the mouse doesn't hover over it any more.
     */
    @FXML
    private void closeButtonMouseExited(){
        changeIconClose(closeButtonImageView);
    }

    /**
     * Changes the image for {@link MainMenuView#closeHighScoreImageView} when the mouse hovers over it.
     */
    @FXML
    private void closeHighScoreMouseEntered(){
        changeIconCloseHover(closeHighScoreImageView);
    }

    /**
     * Changes the image for {@link MainMenuView#closeHighScoreImageView} back to normal when it is clicked on,
     * as well as changes the focus to {@link MainMenuView#mainMenuAnchorPane}.
     */
    @FXML
    private void closeHighScoreMouseClicked() {
        changeIconClose(closeHighScoreImageView);
        showMainMenu();
    }

    /**
     * Changes the image back to normal for {@link MainMenuView#closeHighScoreImageView} when the mouse doesn't
     * hover over it any more.
     */
    @FXML
    private void closeHighScoreMouseExited (){
        changeIconClose(closeHighScoreImageView);
    }

    /**
     * Takes an imageView and changes it to the image for a close-icon that is hovered.
     * @param imageView the ImageView that the image is being changed for
     */
    private void changeIconCloseHover (ImageView imageView){
        final String path = "images/icon_close_hover.png";
        imageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream(path)));
    }

    /**
     * Takes an imageView and changes it to the image for a normal close-icon.
     * @param imageView the ImageView that the image is being changed for
     */
    private void changeIconClose (ImageView imageView){
        final String path = "images/icon_close.png";
        imageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream(path)));
    }

    /**
     * Sets {@link MainMenuView#mainMenuAnchorPane} as visible and sets {@link MainMenuView#howToPlayAnchorPane}
     * and {@link MainMenuView#lightBoxAnchorPane} as not visible.
     */
    private void showMainMenu (){
        mainMenuAnchorPane.setVisible(true);
        lightBoxAnchorPane.setVisible(false);
        howToPlayAnchorPane.setVisible(false);
    }

    /**
     * Sets {@link MainMenuView#howToPlayAnchorPane} and the background {@link MainMenuView#lightBoxAnchorPane}
     * as visible as well as sets {@link MainMenuView#mainMenuAnchorPane} and {@link MainMenuView#highScoreAnchorPane}
     * as not visible.
     */
    private void showHowToPlay (){
        lightBoxAnchorPane.setVisible(true);
        howToPlayAnchorPane.setVisible(true);
        mainMenuAnchorPane.setVisible(false);
        highScoreAnchorPane.setVisible(false);
    }

    /**
     * Sets {@link MainMenuView#highScoreAnchorPane} and the background {@link MainMenuView#lightBoxAnchorPane}
     * as visible as well as sets {@link MainMenuView#mainMenuAnchorPane} and {@link MainMenuView#howToPlayAnchorPane}
     * as not visible.
     */
    private void showHighScore (){
        // Load high scores
        final List<Score> highScores = topController.getHighScores();
        final StringBuilder teamScores = new StringBuilder();
        int index = 0;
        for(final Score score : highScores) {
            index++;
            teamScores.append(index + ". " + score.getFormattedScore() + '\n');
        }
        // Set text in team name and team streak text area
        teamNameTextArea.setText(teamScores.toString());

        // Show high score view
        lightBoxAnchorPane.setVisible(true);
        highScoreAnchorPane.setVisible(true);
        mainMenuAnchorPane.setVisible(false);
        howToPlayAnchorPane.setVisible(false);
    }

    private void mouseTrap(Event event){
        event.consume();
    }

    @Override
    public void init() {

    }

    public void loadHighScoreList() {
        List<Score> highScoreList = topController.getHighScores();
        new HighScoreList(highScoreList);
    }

    @Override
    public Pane getPane() {
        return this;
    }
}


