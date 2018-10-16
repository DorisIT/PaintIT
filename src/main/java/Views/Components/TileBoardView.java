package Views.Components;

import Controller.TileBoardController;
import Model.Game.GameSession;
import Util.CountDownUser;
import Util.Observer;
import Util.ButtonFactory;
import Model.WordAndGuess.Tile;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Timer;
import java.util.TimerTask;

/** Represents the backend Guess-components to the user with a window filled with Tiles (TileSlot), that are clickable
 *
 *
 */

public class TileBoardView extends VBox implements Observer, CountDownUser{


    @FXML HBox hBoxBottom;
    @FXML HBox hBoxTop;
    @FXML VBox vBoxRoot;
    @FXML
    Label countDownLbl;

    private TileSlot[] availableTileSlotArray;
    private final GameSession gameSession;
    private TileBoardController tileBoardController;
    private TileSlot[] guessTileSlotArray;
    private static final int guessTime = 30;

    String filePath = "/fxml/tileBoard.fxml";

    /** Loads itself from it´s fxml file, and instansiates the tiles that visualises the guess from the backend.
     * Further, allows for player to use keyboard to guess.
     */
    public TileBoardView(final GameSession gameSession) {
        this.gameSession = gameSession;
        initFXML();
        initTiles();
        this.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, m -> {
            tileBoardController.handleKeyCode(m.getCode().toString());
        });
        initCountDown();
    }
    private void initCountDown(){
        gameSession.startCountDown(guessTime,this);
        countDownLbl.setText("    " + guessTime);
    }
    private void initFXML(){
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filePath));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch(Exception e) {
            //ouch
        }
    }

    private void initTiles(){
        availableTileSlotArray =  new TileSlot[gameSession.getAvailableTiles().length];
        guessTileSlotArray = new TileSlot[gameSession.getCurrentWord().getWord().length()];
        createAvailableTileSlots(gameSession.getAvailableTiles());

        createEmptyTileSlots();

        tileBoardController = new TileBoardController(gameSession.getGuessLogic());
        setActionListeners();
        gameSession.addGuessLogicObservers(this);
        update();

    }

    private void createEmptyTileSlots(){
        for(int i = 0; i < gameSession.getCurrentWord().getWord().length(); i++){
            guessTileSlotArray[i] = new TileSlotGuess();
        }
        hBoxTop.getChildren().add(getGuessTileOffset());
        for(final TileSlot tileSlot: guessTileSlotArray){
            hBoxTop.getChildren().add(tileSlot);
        }

    }

    private void createAvailableTileSlots(final Tile... availableTiles){
        hBoxBottom.getChildren().add(getAvailableTileOffset());
        for(final Tile tile: availableTiles){
            final TileSlot temp = new TileSlotAvailable(tile);
            availableTileSlotArray[tile.getPosAvailable()] = temp;
            hBoxBottom.getChildren().add(temp);
        }
    }

    private AnchorPane getGuessTileOffset(){
        final AnchorPane buffer = new AnchorPane();
        buffer.setPrefSize((double)(50+(8-guessTileSlotArray.length)*50),100);
        return buffer;
    }

    private AnchorPane getAvailableTileOffset(){
        final AnchorPane buffer = new AnchorPane();
        buffer.setPrefSize(200,100);
        return buffer;

    }

    private void setActionListeners(){
        setTilesActionListeners();
    }

    private void setTilesActionListeners(){ //eventListeners

        for(final TileSlot t: availableTileSlotArray){
            t.getTileButton().setOnAction(e-> tileBoardController.addTileToGuess(t.getTile()));
        }
        for(final TileSlot t: guessTileSlotArray){
            t.getTileButton().setOnAction(e-> tileBoardController.removeTileFromGuess(t.getTile()));
        }
    }


    /** This method is called (through the observer pattern) whenever the backend is changed.
            * It loops updates the TileSlots so that they represent the backend and if a correct guess has been made
     * then it Links to handleCorrectGuess.
     */
    @Override
    public void update() {
        updateAvailableTileSlots();
        updateGuessTileSlots();

        if(isGuessComplete()){
            checkIfCorrectGuess();
        }
    }
    private boolean isGuessComplete(){
        for(final Tile t: gameSession.getGuessWord()){
            if(t == null){
                return false;
            }
        }
        return true;
    }

    private void checkIfCorrectGuess(){
        if(gameSession.guessCurrentWord()){
            handleCorrectGuess();
        }else{
            handleIncorrectGuess();
        }
    }

    /** This method handles a correct Guess - makes all the Tiles Green
     */
    private void handleCorrectGuess(){
        for(final TileSlot t: guessTileSlotArray){
           t.addCorrectGuessCss();
        }
        gameSession.resetTimer();
        startTimer();
        gameSession.incrementTeamStreak();
    }
    /** This method handles a correct Guess - makes all the Tiles Red
     */
    private void handleIncorrectGuess(){
        for(final TileSlot t: guessTileSlotArray){
          t.addIncorrectGuessCss();
        }
    }
    private void updateAvailableTileSlots(){
        for(final TileSlot t: availableTileSlotArray){
            t.update();
        }
    }
    private void updateGuessTileSlots(){
        int count = 0;
        for(final Tile t: gameSession.getGuessWord()){
            guessTileSlotArray[count].setTile(t);
            guessTileSlotArray[count].update();
            count++;
        }
    }

    public void startTimer() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                Platform.runLater(() -> {timer.cancel(); changeToDoneView();});
            }
        },1000,1);
    }

    public void changeToDoneView(){
        gameSession.show(ButtonFactory.createDoneViewBtnId());
    }

    @Override
    public void handleSecondPassed(int secondsLeft) {
        countDownLbl.setText("    " + Integer.toString(secondsLeft));
    }

    @Override
    public void handleTimerFinished() {
        System.out.println("Countdown Finshed");
    }
}