package models.GameLogic;

import akka.actor.ActorRef;
import models.Commands.*;
import models.GameLogic.Enums.BoardPoint;
import models.GameLogic.Enums.BoardSize;
import models.GameLogic.Enums.stoneColor;
import models.GameLogic.Exceptions.WrongMoveException;

/**
 * Created by jakub on 1/28/17.
 */
public class GameGo{
	private GameLogicGO GameLogic;

	private ActorRef black_player=null;
	private ActorRef white_player=null;

	public BoardSize getBsize() {
		return bsize;
	}

	private BoardSize bsize;
	private stoneColor currentPlayer;

	private double BLACKscore=0;
	private double WHITEscore=6.5;

	private boolean pass = false;
	private boolean proposition=false;

	public GameGo(BoardSize siz){
		bsize=siz;
		currentPlayer=stoneColor.BLACK;
		GameLogic=new GameLogicGO(bsize);
	}

	public boolean setBlackPlayer(ActorRef black_player){
		if(black_player==null){
			return false;
		}
		this.black_player=black_player;
		return true;
	}

	public boolean setWhitePlayer(ActorRef white_player){
		if(white_player==null){
			return false;
		}
		this.white_player=white_player;
		return true;
	}

	public boolean contains(ActorRef x){
		if(black_player.equals(x) || white_player.equals(x)){
			return true;
		}
		return false;
	}

	public Object MoveOnBoard(Move move){
		pass=proposition=false;
		try{
			BoardPoint[] changes=GameLogic.nextMove(move.getPoint(),currentPlayer);
			if(currentPlayer==stoneColor.WHITE){
				WHITEscore+= changes.length-1;
			}else{
				BLACKscore+= changes.length-1;
			}
			currentPlayer=currentPlayer.opposite();
			return new Change(changes,BLACKscore,WHITEscore);
		}catch(WrongMoveException ex){
			ex.printStackTrace();
		}
		return new Wrong();
	}

	public Object Pass(){
		currentPlayer=currentPlayer.opposite();
		proposition=false;
		if(!pass){
			pass=true;
			return new Pass();
		}else{
			BoardPoint[][] territory=new BoardPoint[4][];
			territory[0]=GameLogic.countTerritories(stoneColor.BLACK);
			territory[1]=GameLogic.countTerritories(stoneColor.WHITE);
			territory[2]=GameLogic.getDeadStones(stoneColor.BLACK);
			territory[3]=GameLogic.getDeadStones(stoneColor.WHITE);
			pass=false;
			return new Territory(territory[0],territory[1],territory[2],territory[3]);
		}
	}

	public Object Proposition(Proposition prop){
		currentPlayer=currentPlayer.opposite();
		pass=false;
		if(proposition){
			proposition=false;
			return new EndProposition("END"+prop.getProp());
		}else{
			proposition=true;
			return prop;
		}
	}

	public Object EndProposition(EndProposition prop){
		currentPlayer=currentPlayer.opposite();
		pass=proposition=false;
		this.finalScore(prop);
		if(BLACKscore>WHITEscore){
			return new Win(stoneColor.BLACK,BLACKscore,WHITEscore);
		}else{
			return new Win(stoneColor.WHITE,BLACKscore,WHITEscore);
		}
	}

	private void finalScore(EndProposition prop){
		int black=0;
		int white=0;
		String[] command_splited=prop.getEndprop().split("-");
		int i=2;
		while(i<command_splited.length && !command_splited[i].equals("WHITE")){
			black++;
			i+=2;
		}
		i++;
		while(i<command_splited.length && !command_splited[i].equals("BLACKP")){
			white++;
			i+=2;
		}
		i++;
		while(i<command_splited.length && !command_splited[i].equals("WHITEP")){
			white++;
			i+=2;
		}
		i++;
		while(i<command_splited.length){
			black++;
			i+=2;
		}
		double oldWhitePoints=WHITEscore;
		WHITEscore=white-BLACKscore;
		BLACKscore=black-oldWhitePoints;
		//PROPOSITION-BLACK-N-2-M-2-N-1-M-1-WHITE-A-1-A-2-BLACKP-D-7-G-5-J-5-WHITEP-H-10-F-8-J-7
	}

	public void changeProp(){
		proposition=true;
	}
	public void changePass(){
		pass=true;
	}
	public void changeTurn() {
		currentPlayer=currentPlayer.opposite();
	}

	public ActorRef getCurrentPlayer(){
		if(currentPlayer.equals(stoneColor.BLACK)){
			return black_player;
		}else{
			return white_player;
		}
	}
}
