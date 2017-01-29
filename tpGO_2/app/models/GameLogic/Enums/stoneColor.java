package models.GameLogic.Enums;

/**
 * Created by jakub on 12/2/16.
 */
public enum stoneColor {
	WHITE,BLACK,UNDEFINED;

	public stoneColor opposite(){
		if(this==WHITE){
			return BLACK;
		}else if(this==BLACK){
			return WHITE;
		}else{
			return UNDEFINED;
		}
	}
}
