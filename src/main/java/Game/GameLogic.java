package Game;

import Canvas.CanvasController;
import Canvas.CanvasModel;
import Canvas.CanvasView;
import WordAndGuess.GuessLogic;
import WordAndGuess.Word;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

class GameLogic {

    @Setter @Getter private CanvasView currentPainting;

    @Getter private  GuessLogic guessLogic;

    public GameLogic(){
        guessLogic = new GuessLogic();
    }

    public Word getCurrentWord() {
        return guessLogic.getCurrentWord();
    }

    public void setCurrentWord(Word word){
        guessLogic.setCurrentWord(word);

    }
    public List<Word> getPossibleWords(){return guessLogic.getPossibleWords();}

}
