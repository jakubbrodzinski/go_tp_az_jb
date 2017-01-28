package models;

import akka.actor.ActorRef;
import models.GameLogic.Enums.BoardSize;
import models.GameLogic.Enums.stoneColor;
import models.GameLogic.GameLogicGO;

/**
 * Created by jakub on 1/28/17.
 */
public class GameGo {
	private GameLogicGO GameLogic;

	private ActorRef black_player=null;
	private ActorRef white_player=null;
	private BoardSize bsize;
	private stoneColor currentPlayer;

	public GameGo(BoardSize siz){
		bsize=siz;
		currentPlayer=stoneColor.BLACK;
		GameLogic=new GameLogicGO(bsize);
	}

	public boolean setBlackPlayer(ActorRef black_player){
		if(black_player!=null){
			return false;
		}
		this.black_player=black_player;
		return true;
	}

	public boolean setWhitePlayer(ActorRef white_player){
		if(white_player!=null){
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

	public ActorRef getOpponent(){
		if(currentPlayer==stoneColor.BLACK){
			currentPlayer=stoneColor.WHITE
			return black_player;

		}else{
			currentPlayer==stoneColor.WHITE
			return white_player;
		}
	}
}
