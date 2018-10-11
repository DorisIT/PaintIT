package Views;

import Game.GameSession;
import Game.Team;
import Util.ButtonFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class GameSetupView extends AnchorPane implements GameScreen {

    @FXML private TextField player1TextField;
    @FXML private TextField player2TextField;
    @FXML private Button startDrawing;
    @FXML private Button backButton;
    @FXML private ImageView backButtonImageView;

    private GameSession gameSession;

    public GameSetupView (FXMLLoader fxmlLoader, GameSession gameSession){
        this.gameSession = gameSession;

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
            // Create team and add it to game backend
            setNames();
            // Start word reveal countdown (in WordRevealView)
            // gameSession.startWordRevealCountdown();
            // Show next view
            gameSession.show(startDrawing.getId());
        });

        backButtonImageView.setId(ButtonFactory.createMainMenuViewBtnId());
        backButtonImageView.setOnMouseClicked(e -> {
            String path = "images/icon_back.png";
            backButtonImageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream((path))));
            gameSession.show(backButtonImageView.getId());
        });

    }

    @FXML
    private void backButtonImageViewEntered (){
        String path = "images/icon_back_hover.png";
        backButtonImageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream((path))));
    }

    @FXML
    private void backButtonImageViewExited (){
        String path = "images/icon_back.png";
        backButtonImageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream(path)));
    }

    private void setNames () {

        String player1 = player1TextField.getText();
        String player2 = player2TextField.getText();

        gameSession.addTeam(new Team(player1, player2));


    }


    @Override
    public void init() {

    }

    @Override
    public Pane getPane() {
        return this;
    }
}
