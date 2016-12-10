package Server.Main;

import Server.Enums.BoardSize;
import Server.Enums.stoneColor;

import java.util.ArrayList;
/*
TO DO LIST:
6.Terytorium!!!!!
 */
/**
 * Created by jakub on 12/3/16.
 * Class that implements whole logic of the GO Game. Tests if move is correct.
 * Returns BoardPoints that has changed.
 */
public class GameLogicGO {
	protected stoneColor[][] board;
	private  stoneColor[][][] boardHistory;
	private stoneColor[][][] boardDup;
	protected BoardSize boardSize;
	boolean firstMove=true;
	public GameLogicGO(){
	}

	public void setSize(BoardSize i){
		boardSize=i;
		board=new stoneColor[i.getSize()][i.getSize()];
		boardHistory=new stoneColor[2][i.getSize()][i.getSize()];
		boardDup=new stoneColor[4][i.getSize()][i.getSize()];
		for(int j=0;j<i.getSize();j++){
			for(int y=0;y<i.getSize();y++) {
				board[j][y]=stoneColor.UNDEFINED;
				boardHistory[0][j][y] = stoneColor.UNDEFINED;
				boardHistory[1][j][y] = stoneColor.UNDEFINED;
			}
		}
	}

	public BoardPoint[][] countTerritories(){
		ArrayList<BoardPoint> blackTerritory=new ArrayList<>();
		ArrayList<BoardPoint> whiteTerritory=new ArrayList<>();
		int black=0;
		int white=0;
		for(int i=0;i<board.length;i++){
			for(int j=0;j<board.length;j++){
				if(black<2){
					if(board[i][j]==stoneColor.BLACK){
						blackTerritory.add(new BoardPoint(i,boardSize.getSize()-1-j));
						black++;
					}
				}
				if(white<2){
					if(board[i][j]==stoneColor.WHITE){
						whiteTerritory.add(new BoardPoint(i,boardSize.getSize()-1-j));
						white++;
					}
				}
			}
		}
		BoardPoint[][] toReturn=new BoardPoint[2][];
		toReturn[1]=whiteTerritory.toArray(new BoardPoint[whiteTerritory.size()]);
		toReturn[0]=blackTerritory.toArray(new BoardPoint[blackTerritory.size()]);
		return toReturn;
	}
	//Nie powinna byc wywolywana bezposrednio!
	private boolean lookFor(boolean AlreadyFound,stoneColor[][] boardDup,int row,int column,stoneColor color){
		if(column<0 || row < 0 || column>=board.length || row>=board.length){
			//has to be added because of suicide
			if(!AlreadyFound)
				return false;
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
		if(firstMove){
			if(turn==stoneColor.WHITE) {
				firstMove = false;
			}else{
				BoardPoint center=null;
				if(board.length==13){
					center=new BoardPoint('G',7);
				}else if(board.length==19){
					center=new BoardPoint('K',10);
				}else{
					center=new BoardPoint('E',5);
				}
				if(center.equals(move))
					throw new WrongMoveException();
				else
					firstMove=false;
			}
		}
		int pointX=move.getIntegerVertical()-1;
		int pointY=board.length-move.getHorizontal();
		//We cannot place new stone if there is already one in this field or field is outside the board
		if(pointX<0 || pointX >=board.length || pointY<0 || pointX >=board.length){
			System.out.println("OutOfBounds move!["+(pointX+1)+"]["+(pointY+1)+"]");
			return null;
		}else if(board[pointY][pointX]!=stoneColor.UNDEFINED) {
			throw new WrongMoveException("Point Already Taken!");
		}


		board[pointY][pointX] = turn;

		for(int i=0;i<board.length;i++){
			System.arraycopy(board[i],0,boardDup[0][i],0,board[i].length);
			System.arraycopy(board[i],0,boardDup[1][i],0,board[i].length);
			System.arraycopy(board[i],0,boardDup[2][i],0,board[i].length);
			System.arraycopy(board[i],0,boardDup[3][i],0,board[i].length);
		}
		stoneColor lookForColor;
		if(turn==stoneColor.BLACK){
			lookForColor=stoneColor.WHITE;
		}else{
			lookForColor=stoneColor.BLACK;
		}

		boolean[] moveResults=new boolean[4];
		//up
		moveResults[0]=lookFor(false,boardDup[0],pointY-1,pointX,lookForColor);
		//right
		moveResults[1]=lookFor(false,boardDup[1],pointY,pointX+1,lookForColor);
		//down
		moveResults[2]=lookFor(false,boardDup[2],pointY+1,pointX,lookForColor);
		//left
		moveResults[3]=lookFor(false,boardDup[3],pointY,pointX-1,lookForColor);
		//moveResults[0]=lookFor(false,boardDup,pointY,pointX,lookForColor);
		//even if move looks like good we have to check for KO
		if(moveResults[0] || moveResults[1] || moveResults[2] || moveResults[3]){

			if(moveResults[0]){
				for (int y = 0; y < board.length; y++) {
					for (int z = 0; z < board[y].length; z++) {
						if (boardDup[0][y][z] == null && lookForColor == board[y][z]) {
							board[y][z] = stoneColor.UNDEFINED;
						}
					}
				}
			}
			if(moveResults[1]){
				for (int y = 0; y < board.length; y++) {
					for (int z = 0; z < board[y].length; z++) {
						if (boardDup[1][y][z] == null && lookForColor == board[y][z]) {
							board[y][z] = stoneColor.UNDEFINED;
						}
					}
				}
			}
			if(moveResults[2]){
				for (int y = 0; y < board.length; y++) {
					for (int z = 0; z < board[y].length; z++) {
						if (boardDup[2][y][z] == null && lookForColor == board[y][z]) {
							board[y][z] = stoneColor.UNDEFINED;
						}
					}
				}
			}
			if(moveResults[3]){
				for (int y = 0; y < board.length; y++) {
					for (int z = 0; z < board[y].length; z++) {
						if (boardDup[3][y][z] == null && lookForColor == board[y][z]) {
							board[y][z] = stoneColor.UNDEFINED;
						}
					}
				}
			}
			//Checkin for KO (infinity)
			boolean ifnity=true;
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
				System.out.println("KO");
				throw new WrongMoveException("KO");
			}
		}else {
			//Checkin for suicide
			for(int i=0;i<board.length;i++){
				System.arraycopy(board[i],0,boardDup[0][i],0,board[i].length);
			}
			if(lookFor(false,boardDup[0],pointY,pointX,turn)){
				board[pointY][pointX] = stoneColor.UNDEFINED;
				System.out.println("SUICIDE");
				throw new WrongMoveException("SUICIDE");
			}
		}
		//updating our boardHistory array, that stores situatuion that was on board 1 and 2 turned before
		for(int i=0;i<board.length;i++) {
			System.arraycopy(boardHistory[1][i], 0, boardHistory[0][i], 0, board[i].length);
			System.arraycopy(board[i], 0, boardHistory[1][i], 0, board[i].length);
		}
		return getBoardChanges(move);
	}

	//if move is good this method return Array of BoardPoints that has changed.
	private BoardPoint[] getBoardChanges(BoardPoint move){
		ArrayList<BoardPoint> temporaryArray=new ArrayList<>();
		temporaryArray.add(move);
		for(int row=0;row<board.length;row++){
			for(int column=0;column<board[row].length;column++){
				if(board[row][column]!=boardHistory[0][row][column]) {
					BoardPoint hasChanged=new BoardPoint(new Integer(column),board.length-row-1);
					if(!move.equals(hasChanged)){
						temporaryArray.add(hasChanged);
					}
				}
			}
		}
		return temporaryArray.toArray(new BoardPoint[temporaryArray.size()]);
	}

	//method only for test with junit
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
