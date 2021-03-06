package Views.Components;

import Model.WordAndGuess.Tile;
/**
 *Represents an available Tile from the backend.
 */
public class TileSlotAvailable extends TileSlot {


    public TileSlotAvailable(){
        super();
    }
    public TileSlotAvailable(Tile tile){
        super(tile);
    }

    @Override
    public void update(){
        tileButton.getStyleClass().clear();
        if(getTile().getStatus() == Tile.Status.Available){
            tileButton.setDisable(false);
            tileButton.getStyleClass().add("visibleButton");
        }else{
            tileButton.getStyleClass().add("emptyButton");
            tileButton.setText(Character.toString(getTile().getLetter()));
            tileButton.setDisable(true);
        }
    }
}
