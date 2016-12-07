package Server.Main;

import Server.Enums.BoardSize;
import Server.Enums.stoneColor;

import java.util.ArrayList;
/*
TO DO LIST:
1.Opakowanie do polaczenie lookfor i next move
2.Sprawdzenie KO (NIESKONCOZSC)
3.Sprawdzenie samobójstwa :<
 1-3 Probably done.
4. Metode wywołać dla góry,doły,prawo,lewo ze spojnikiem || później wsyzstko scalic.
 */
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

	//Nie powinna byc wywolywana bezposrednio!
	private boolean lookFor(boolean AlreadyFound,stoneColor[][] boardDup,int row,int column,stoneColor color){
		//DrawBoard(boardDup);
		if(column<0 || row < 0 || column>=board.length || row>=board.length){
			return true;
		}else if(boardDup[row][column]==stoneColor.UNDEFINED) {
			return false;
		}else if(boardDup[row][column]==null){
			return true;
		}else if(boardDup[row][column]!=color) {
			if(!AlreadyFound) {
				boardDup[row][column] = null;
				//gora
				if(!lookFor(false, boardDup, row-1, column, color))
					return false;
				//prawo
				if(!lookFor(false, boardDup, row, column+1, color))
					return false;
				//dol
				if(!lookFor(false, boardDup, row+1, column, color))
					return false;
				//lewo
				if(!lookFor(false, boardDup, row,column-1, color))
					return false;
			}else{
				return true;
			}
		}else if(boardDup[row][column]==color) {
			boardDup[row][column] = null;
			//zmiana AlreadyFound! Sprawdzamy czy kamyki przeciwniki są ograniczone z jakiejs strony.
			//gora
			if(!lookFor(true, boardDup, row-1, column, color))
				return false;
			//prawo
			if(!lookFor(true, boardDup, row,column+1, color))
				return false;
			//dol
			if(!lookFor(true, boardDup, row+1,column, color))
				return false;
			//lewo
			if(!lookFor(true, boardDup,row,column-1, color))
				return false;
		}
		return true;
	}

	public synchronized BoardPoint[] nextMove(BoardPoint move,stoneColor turn) throws WrongMoveException{
		int pointX=move.getIntegerHorizontal()-1;
		int pointY=board.length-move.getVertical();
		//We cannot place new stone if there is already one in this field or field is outside the board
		if(pointX<0 || pointX >=board.length || pointY<0 || pointX >=board.length){
			System.out.println("OutOfBounds move!["+(pointX+1)+"]["+(pointY+1)+"]");
			return null;
		}else if(board[pointY][pointX]!=stoneColor.UNDEFINED) {
			return null;
		}
		for(int i=0;i<board.length;i++) {
			System.arraycopy(boardHistory[1][i], 0, boardHistory[0][i], 0, board[i].length);
			System.arraycopy(board[i], 0, boardHistory[1][i], 0, board[i].length);
		}
		board[pointY][pointX] = turn;
		stoneColor[][] boardDup=new stoneColor[board.length][board.length];
		for(int i=0;i<board.length;i++){
			System.arraycopy(board[i],0,boardDup[i],0,board[i].length);
		}
		stoneColor lookForColor;
		if(turn==stoneColor.BLACK){
			lookForColor=stoneColor.WHITE;
		}else{
			lookForColor=stoneColor.BLACK;
		}
		boolean moveResults=lookFor(false,boardDup,pointY,pointX,lookForColor);
		//even if move looks like good we have to check for KO
		if(moveResults){
			DrawBoard(boardDup);
			for (int y = 0; y < board.length; y++) {
				for (int z = 0; z < board[y].length; z++) {
					if (boardDup[y][z]==null && lookForColor==board[y][z]) {
						board[y][z]=stoneColor.UNDEFINED;
					}
				}
			}
			//Checkin for KO (infinity)
			boolean ifnity=false;
			for (int y = 0; y < board.length; y++) {
				for (int z = 0; z < board[y].length; z++) {
					if (boardHistory[0][y][z] != board[y][z]) {
						ifnity=false;
					}
				}
			}
			//if there is a KO we have to undo last move.
			if (ifnity) {
				for(int i=0;i<board.length;i++) {
					System.arraycopy(boardHistory[1][i], 0, board[i], 0, board[i].length);
				}
				throw new WrongMoveException("Infitity");
			}
		}else {
			//Checkin for suicide
			for(int i=0;i<board.length;i++){
				System.arraycopy(board[i],0,boardDup[i],0,board[i].length);
			}
			if(lookFor(false,boardDup,pointY,pointX,turn)){
				board[pointY][pointX] = stoneColor.UNDEFINED;
				System.out.println("samobojstwo");
				throw new WrongMoveException("KO");
			}
		}
		//DrawBoard(board);
		return getBoardChanges();
	}

	//if move is good this method return Array of BoardPoints that has changed.
	private BoardPoint[] getBoardChanges(){
		ArrayList<BoardPoint> temporaryArray=new ArrayList<>();
		for(int row=0;row<board.length;row++){
			for(int column=0;column<board[row].length;column++){
				if(board[row][column]!=boardHistory[1][row][column])
					temporaryArray.add(new BoardPoint(new Integer(column),board.length-row));
			}
		}
		return temporaryArray.toArray(new BoardPoint[temporaryArray.size()]);
	}

	//method only for test with junit
	private void DrawBoard(stoneColor[][] toDraw) {
		for (int i = 0; i < toDraw.length; i++) {
			for (int j = 0; j < toDraw[i].length; j++) {
				System.out.print("(");
				if (toDraw[i][j] == stoneColor.UNDEFINED || toDraw[i][j] == null)
					System.out.print(" ");
				else if (toDraw[i][j] == stoneColor.BLACK) {
					System.out.print("X");
				} else if (toDraw[i][j] == stoneColor.WHITE) {
					System.out.print("O");
				}
				System.out.print(")");
			}
			System.out.println();
		}
		System.out.println();
	}
		public void DrawBoard(){
			for(int i=0;i<board.length;i++){
				for(int j=0;j<board[i].length;j++){
					System.out.print("(");
					if(board[i][j]==stoneColor.UNDEFINED || board[i][j]==null)
						System.out.print(" ");
					else if(board[i][j]==stoneColor.BLACK){
						System.out.print("X");
					}else if(board[i][j]==stoneColor.WHITE){
						System.out.print("O");
					}
					System.out.print(")");
				}
				System.out.println();
			}
			System.out.println();
		}
}
