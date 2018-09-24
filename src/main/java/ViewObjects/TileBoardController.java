package ViewObjects;

import WordAndGuess.GuessLogic;
import WordAndGuess.WordHandler;
import javafx.event.EventHandler;


public class TileBoardController {


    TileBoard tileBoard;
    GuessLogic guessLogic;
    public TileBoardController(GuessLogic guessLogic, TileBoard tileBoard) {
        this.tileBoard = tileBoard;
        this.guessLogic = guessLogic;
        setTilesEL();
    }

    public void addTileToGuess(Character c){
        System.out.println(c.toString());
        guessLogic  .addCharToGuess(c);
    }

    private void setTilesEL(){ //eventListeners
       for(TileSlot t: tileBoard.getTileSlotList()){
           t.getTileButton().setOnAction(e-> addTileToGuess(t.getTileLetter()));
       }
    }
}
