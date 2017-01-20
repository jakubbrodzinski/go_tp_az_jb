package Server.BotGO;

import Server.Enums.BoardSize;
import Server.Enums.stoneColor;
import Server.Main.BoardPoint;
import Server.Main.GameLogicGO;
import Server.Main.WrongMoveException;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.ThreadLocalRandom;

/**
 * In this class whole logic and way how Bot makes new moves are implemented.
 * It extends GameLogicGo and because of that we can actually test if move is ok or not, before sending it
 * to the Server. Bot has copy of the board and because of it he can analyze what move would be better than others.
 */
public class Bot extends GameLogicGO {
	private stoneColor botColor;
	private ArrayList<BoardPoint> historyOfMoves=new ArrayList<>();
	private BoardPoint[] handicaps=new BoardPoint[3];

	public Bot(){}
	public void setBotColor(stoneColor col){
		botColor=col;
	}
	/**
	 * Initialize board and sets 3 possible first moves.
	 * @param e size of the Board
	 */
	public void setSize(BoardSize e){
		super.setSize(e);
		if(boardSize==BoardSize.SMALL){
			handicaps[0]=new BoardPoint('C',7);
			handicaps[1]=new BoardPoint('E',5);
			handicaps[2]=new BoardPoint('G',3);
		}else if(boardSize==BoardSize.MEDIUM){
			handicaps[0]=new BoardPoint('D',10);
			handicaps[1]=new BoardPoint('G',7);
			handicaps[2]=new BoardPoint('K',4);
		}else{
			handicaps[0]=new BoardPoint('D',16);
			handicaps[1]=new BoardPoint('K',10);
			handicaps[2]=new BoardPoint('Q',4);
		}
	}
	public Bot(BoardSize e,stoneColor col){
		setSize(e);
		this.botColor=col;
	}

	/**
	 * With this method we keep updated our copy of the board.
	 * @param point Point where second Player made move.
	 */
	public void insertChanges(BoardPoint point){
		try{
			nextMove(point,botColor.opposite());
		}catch(WrongMoveException e){
			System.out.println("Wrong move passed to BOT!");
		}
	}

	private BoardPoint getFirstMove(){
		boolean firstMove=false;
		while(!firstMove) {
			try {
				BoardPoint possibleMove=handicaps[0];
				nextMove(possibleMove,botColor);
				historyOfMoves.add(possibleMove);
				return possibleMove;
			} catch (WrongMoveException e) { }
			try {
				BoardPoint possibleMove=handicaps[1];
				nextMove(possibleMove,botColor);
				historyOfMoves.add(possibleMove);
				return possibleMove;
			} catch (WrongMoveException e) { }
			try {
				BoardPoint possibleMove=handicaps[2];
				nextMove(possibleMove,botColor);
				historyOfMoves.add(possibleMove);
				return possibleMove;
			} catch (WrongMoveException e) { }
			firstMove=true;
		}
		return getRandomPoint();
	}

	private BoardPoint checkSurrounding(BoardPoint p) {
		int m1 = boardSize.getSize() - p.getHorizontal();
		int m2 = p.getIntegerVertical() - 1;
		int countOpponentsStones=0;
		if(m1+1>=boardSize.getSize() || board[m1+1][m2]==botColor.opposite()){
			countOpponentsStones++;
		}
		if(m2+1>=boardSize.getSize() || board[m1][m2+1]==botColor.opposite()){
			countOpponentsStones++;
		}
		if(m1-1<0 || board[m1-1][m2]==botColor.opposite()){
			countOpponentsStones++;
		}
		if(m2-1<0 || board[m1][m2-1]==botColor.opposite()){
			countOpponentsStones++;
		}
		if(countOpponentsStones<2)
			return null;
		return moveNextToPoint(m1,m2);

	}

	private BoardPoint getRandomPoint(){
		int i=0;
		do {
			int p1 = ThreadLocalRandom.current().nextInt(0, boardSize.getSize());
			int p2 = ThreadLocalRandom.current().nextInt(0, boardSize.getSize());
			try {
				BoardPoint temp=new BoardPoint(p1,boardSize.getSize()-1-p2);
				nextMove(temp, botColor);
				historyOfMoves.add(temp);
				return temp;
			} catch (WrongMoveException e) {
			}
		} while (i++<30);
		for(int x=0;x<board.length;x++){
			for(int y=0;y<board[x].length;y++){
				if(board[x][y]==stoneColor.UNDEFINED){
					System.out.println("Not that random");
					return new BoardPoint(y,boardSize.getSize()-1-x);
				}
			}
		}
		return null;
	}

	@SuppressWarnings("Duplicates")
	private BoardPoint moveNextToPoint(int m1, int m2){
		BoardPoint results;
		try {
			if(m1+1<boardSize.getSize()) {
				results = new BoardPoint(m2, boardSize.getSize()-1-(m1+1));
				nextMove(results, botColor);
				historyOfMoves.add(results);
				return results;
			}
		} catch (WrongMoveException e) {}
		try {
			if(m2+1<boardSize.getSize()) {
				results = new BoardPoint(m2+1,boardSize.getSize()-1- m1);
				nextMove(results, botColor);
				historyOfMoves.add(results);
				return results;
			}
		} catch (WrongMoveException e) {}
		try {
			if(m2-1>=0) {
				results = new BoardPoint(m2-1,boardSize.getSize()-1- m1);
				nextMove(results, botColor);
				historyOfMoves.add(results);
				return results;
			}
		} catch (WrongMoveException e) {}
		try {
			if(m1-1>=0) {
				results = new BoardPoint(m2, boardSize.getSize()-1-(m1-1));
				nextMove(results, botColor);
				historyOfMoves.add(results);
				return results;
			}
		} catch (WrongMoveException e) {}
		return null;
	}

	/**
	 * In this method decision is made where Bot should make his next move.
	 * @return move made by bot
	 */
	public BoardPoint nextBotMove(){
		if(historyOfMoves.size()==0) {
			return getFirstMove();
		}else{
			if(ThreadLocalRandom.current().nextInt(0,4)==3){
				return getRandomPoint();
			}
			int checkPoints=6;
			if(historyOfMoves.size()<checkPoints){
				ListIterator<BoardPoint> iterator=historyOfMoves.listIterator();
				while(iterator.hasNext()){
					BoardPoint point=iterator.next();
					BoardPoint results=checkSurrounding(point);
					if(results!=null) {
						historyOfMoves.add(results);
						return results;
					}
				}
			}else{
				for(int i=0;i<checkPoints;i++){
					int index=ThreadLocalRandom.current().nextInt(0,historyOfMoves.size());
					BoardPoint results=checkSurrounding(historyOfMoves.get(index));
					if( results != null){
						historyOfMoves.add(results);
						return results;
					}
				}
			}
			BoardPoint last=historyOfMoves.get(historyOfMoves.size()-1);
			int m1 = boardSize.getSize() - last.getHorizontal();
			int m2 = last.getIntegerVertical() - 1;
			BoardPoint toBeReturned=moveNextToPoint(m1,m2);
			if(toBeReturned==null) {
				return getRandomPoint();
			}
			else {
				return toBeReturned;
			}
		}
	}
}
