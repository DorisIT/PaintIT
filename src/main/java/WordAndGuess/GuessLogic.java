package WordAndGuess;

import Tools.Observable;
import Tools.Observer;
import Util.GeneralUtil;

import java.util.ArrayList;
/**
 *Contains the logic for the guess of the word of the Game.
 * It´s purpose is to keep track of the guess and check whether it is correct or not
 */
public class GuessLogic implements Observable {

    private String currentWord;

    private Tile[] guessWord;
    private Tile[] availableTiles;
    private WordHandler wordHandler;
    private ArrayList<Observer> observers;



    /**
     *Instansiates a wordHandler that generates a word for the game and random Tiles to pick from
     */
    public GuessLogic(){
        this.wordHandler = new WordHandler();
        pickNewWord();
    }


    public String getCurrentWord() {
        return wordHandler.getCurrentWord();
    }


    public Tile[] getAvailableTiles() {
        return availableTiles.clone();
    }

    /**
     *Adds a tile t to the first empty slot of the guessWord ( Tile[] )
     * @param t The tile that is added to the guess.
     */
    public void addTileToGuess(Tile t){
        if(!isGuessFilled()) {
            for (int i = 0; i < guessWord.length; i++) {
                if (guessWord[i] == null) {
                    guessWord[i] = t;
                    break;
                }
            }
            t.setStatus(Tile.Status.Used);
            notifyObservers();

        }
    }
    private boolean isGuessFilled(){
        for(Tile t: guessWord){
            if(t == null){
                return false;
            }
        }
        return true;
    }
    public String getGuessString(){
        return  GeneralUtil.tileArrayToString(guessWord);
    }


    public Tile[] getGuessWord() {
        return guessWord.clone();
    }

    /**
     *Removes a tile From the guess
     * @param tile the tile that is removed from the guess.
     */
    public void removeTileFromGuess(Tile tile){
        int count = 0;
        for(Tile t: guessWord){
            if(guessWord[count] == tile) {
                tile.setStatus(Tile.Status.Available);
                guessWord[count] = null;
                count++;
                break;
            }
            count++;
        }
        notifyObservers();
    }

    /**
     * Removes rightmost tile from the guessed word
     */
    public void removeRightMostTileFromGuess(){
        for (int c = currentWord.length()-1; c >= 0; c-- ) {
            if(guessWord[c]!=null) {
                removeTileFromGuess(guessWord[c]);
                break;
            }
        }
    }


    /**
     *@return false if guess was wrong
     * @return True if guess was correct
     */
    public boolean guessCurrentWord(){
        String guessWord = GeneralUtil.tileArrayToString(this.guessWord);
        if(guessWord.toLowerCase().equals(currentWord.toLowerCase())){
            return true;
        }
        return false;
    }


    public void pickNewWord(){
        wordHandler.pickRandomWord();
        this.availableTiles = wordHandler.getTiles();
        this.currentWord = wordHandler.getCurrentWord();
        guessWord = new Tile[currentWord.length()];
        observers = new ArrayList<>();

    }
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer obs: observers){
            obs.update();
        }
    }
}
