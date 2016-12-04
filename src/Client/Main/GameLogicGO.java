package Server.Main;

import Server.BoardSize;
import Server.stoneColor;

import java.util.ArrayList;

/**
 * Created by jakub on 12/3/16.
 * Class that implements whole logic of the GO Game. Tests if move is correct/
 */
public class GameLogicGO {
	protected  stoneColor[][] board;
	protected  stoneColor[][][] boardHistory;

	public GameLogicGO(){
	}

	public void setSize(BoardSize i){
		board=new stoneColor[i.getSize()][i.getSize()];
		boardHistory=new stoneColor[2][i.getSize()][i.getSize()];
		for(int j=0;j<i.getSize();j++){
			for(int y=0;y<i.getSize();y++) {
				board[j][y]=stoneColor.UNDEFINED;
				boardHistory[0][j][y] = stoneColor.UNDEFINED;
				boardHistory[1][j][y] = stoneColor.UNDEFINED;
			}
		}
	}

	public boolean boardFilledUp(){
		for(stoneColor[] y : board) {
			for (stoneColor e : y) {
				if (e == stoneColor.UNDEFINED)
					return false;
			}
		}
		return true;
	}

	public BoardPoint[] nextMove(BoardPoint move,stoneColor turn){
		if(board[move.getIntegerHorizontal()-1][move.getVertical()-1]!=stoneColor.UNDEFINED)
			return null;
		System.arraycopy(boardHistory[1],0,boardHistory[0],0,board.length);
		System.arraycopy(board,0,boardHistory[1],0,board.length);
		board[move.getHorizontal()-1][move.getVertical()-1]=turn;
		return getBoardChanges();
	}

	private BoardPoint[] getBoardChanges(){
		ArrayList<BoardPoint> temporaryArray=new ArrayList<>();
		for(int column=0;column<board.length;column++){
			for(int row=0;row<board[column].length;row++){
				if(board[column][row]!=boardHistory[1][column][row])
					temporaryArray.add(new BoardPoint((char) ('A'+column),row+1));
			}
		}
		return temporaryArray.toArray(new BoardPoint[temporaryArray.size()]);
	}

}
