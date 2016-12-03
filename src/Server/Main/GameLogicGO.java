package Server.Main;

import Server.BoardSize;
import Server.stoneColor;

/**
 * Created by jakub on 12/3/16.
 * Class that implements whole logic of the GO Game. Tests if move is correct/
 */
public class GameLogicGO {
	protected  stoneColor[] board;
	protected  stoneColor[][] boardHistory;
	public GameLogicGO(){
	}
	public void setSize(BoardSize i){
		board=new stoneColor[i.getSize()];
		boardHistory=new stoneColor[2][i.getSize()];
		for(int j=0;j<i.getSize();j++){
			board[j]=stoneColor.UNDEFINED;
			boardHistory[0][j]=stoneColor.UNDEFINED;
			boardHistory[1][j]=stoneColor.UNDEFINED;
		}
	}

	public boolean boardFilledUp(){
		for(stoneColor e: board){
			if(e==stoneColor.UNDEFINED)
				return false;
		}
		return true;
	}

	public boolean nextMove(BoardPoint move){

		return false;
	}

	public BoardPoint[] getBoardChanges(){
		return null;
	}

}
