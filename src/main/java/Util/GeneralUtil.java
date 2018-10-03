package Util;

import WordAndGuess.Tile;

import java.util.ArrayList;

public class GeneralUtil {

    public static String CharArrayListToString(ArrayList<Character> charArray){
       StringBuilder sB = new StringBuilder();
       for (Character c: charArray){
            sB.append(c);
        }
        return sB.toString() ;
    }
    public static String tileListToString(ArrayList<Tile> tiles){

        StringBuilder sB = new StringBuilder();
        for(Tile t: tiles){
            sB.append(t.getLetter());
        }
        return sB.toString();
    }
    public static String tileArrayToString(Tile[] tiles){
        StringBuilder sB = new StringBuilder();
        for(int i= 0; i<tiles.length; i++){
            if(tiles[i] != null){
                sB.append(tiles[i].getLetter());
            }
        }
        return sB.toString();
    }

}
