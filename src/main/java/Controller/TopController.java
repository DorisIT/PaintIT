package Controller;

import Model.Canvas.CanvasModel;
import Model.Game.Score;
import Model.Game.Team;
import Util.CountDownTimer;
import Util.CountDownUser;
import Util.Observer;
import Util.ViewFactory;
import Views.GameScreen;
import Model.WordAndGuess.Tile;
import Model.WordAndGuess.Word;
import javafx.scene.layout.Pane;
import Model.WordAndGuess.GuessLogic;

import java.util.List;

/**
 * Main class, this is where most parts of the application is connected.
 */

public class TopController {

    private Team team;

    private final GameLogic GAMELOGIC;
    private final ViewController VIEWCONTROLLER;
    private final CountDownTimer COUNTDOWNTIMER;
    private boolean isLastWord;

    /**
     * A boolean for if the game is over or if the players can keep playing.
     */
    private Boolean gameOver;

    public TopController() {
        GAMELOGIC = new GameLogic();
        final List<GameScreen> gameScreens = ViewFactory.createAllViews(this);
        VIEWCONTROLLER = new ViewController(gameScreens);
        COUNTDOWNTIMER = new CountDownTimer();

        GAMELOGIC.setupHighScores();
        gameOver = false;
        isLastWord = false;
    }


    public void resetTimer() {
        COUNTDOWNTIMER.resetTimer();
    }

    public Pane getCurrentPane() { return VIEWCONTROLLER.getCurrentView(); }

    /**
     * Method for invoking {@link GameScreen#init()} of view to be shown and switching current view
     * in {@link ViewController}.
     * @param url
     */
    public void show(String url) {
        // Get next view to be shown
        VIEWCONTROLLER.prepareNextView(url);
        // Call init method on next view
        final GameScreen gameScreenTmp = VIEWCONTROLLER.getNextScreen();
        gameScreenTmp.init();
        // Show next view
        VIEWCONTROLLER.show();
    }


    public void startCountDown(int seconds, CountDownUser caller){
        COUNTDOWNTIMER.startCountDown(seconds,caller);
    }
    /**
     * If there's no prior instance of {@link Team}, add passed reference to {@link TopController#team}.
     * @param team
     */

    public void addTeam(Team team) {
        if(this.team == null) { this.team = team; }
    }

    /**
     * @return Name of {@link TopController#team}.
     */
    public String getTeamName() {
        if(team != null)
            return team.getTeamName();
        else return "There's no team!";
    }
    public GuessLogic getGuessLogic(){
        return GAMELOGIC.getGuessLogic();
    }

    /**
     *
     * @return Name of players in {@link TopController#team} as a pair of Strings.
     */
    public String getGuesserName() {
        return team.getGuesserName();
    }

    public String getDrawerName (){
        return team.getDrawerName();
    }

    public int getTeamStreak() { return team.getStreak(); }

    public void incrementTeamStreak() {
        Word currentWord = getCurrentWord();
        int points = GAMELOGIC.getPoints(currentWord.getDifficulty_level());
        team.incrementStreak(points);
    }

    public int getPoints(Word.Difficulty difficulty) {
        return GAMELOGIC.getPoints(difficulty);
    }

    public void resetTeamStreak() {
        team.resetStreak();
    }

    public CanvasModel getCanvas() { return GAMELOGIC.getCurrentPainting(); }

    public void setCurrentPainting(CanvasModel canvasModel) { GAMELOGIC.setCurrentPainting(canvasModel); }

    public Word getCurrentWord() {
        return GAMELOGIC.getCurrentWord();
    }

    public void setCurrentWord(Word word){
        GAMELOGIC.setCurrentWord(word);
    }

    public Tile[] getAvailableTiles(){return getGuessLogic().getAvailableTiles();}

    public void addGuessLogicObservers(Observer observer){getGuessLogic().addObserver(observer);}

    public Tile[] getGuessWord(){return getGuessLogic().getGuessWord();}

    public boolean guessCurrentWord(){return getGuessLogic().guessCurrentWord();}

    public List<Word> getPossibleWords(){
        return GAMELOGIC.getPossibleWords();
    }

    public Boolean getGameOver (){
        return gameOver;
    }

    /**
     * Sets {@link TopController#gameOver} to true, which means that they have lost or quit the game session.
     */
    public void setToGameOver(boolean gameState){
        // Mark that current game session is over!
        gameOver = gameState;
    }

    /**
     * Saves the score, resets the team and calls on {@link GameLogic#newGame()}.
     */
    public void gameOver() {
        // Game over, save team's streak if necessary
        saveScore();
        // Reset words
        GAMELOGIC.newGame();
        isLastWord = false;
        // Remove current team
        team = null;
    }

    /**
     * Changes the drawer to guesser and vice versa.
     */
    public void newTurn() {
        // Turn over, generate, switch guesser/drawer..
        team.changeDrawer();
    }

    /**
     * Calls on {@link GameLogic#saveScore(String, int)} and sets the string to the team's name and the int to the team's streak.
     */
    public void saveScore() {
        if (team != null) {
            GAMELOGIC.saveScore(team.getTeamName(), team.getStreak());
        }
    }

    public List<Score> getHighScores() {
        return GAMELOGIC.getHighScores();
    }

    public boolean getIsLastWord (){
        return isLastWord;
    }

    public void setIsLastWord (Boolean lastWord){
        //Mark that it is the last word!
        isLastWord = lastWord;
    }

}
