package Server.BotGO;

import Server.Enums.stoneColor;

/**
 * Created by jakub on 12/8/16.
 */
public abstract class PlayerAbstract implements Runnable {
	protected stoneColor color;
	protected PlayerAbstract opponent;

	public PlayerAbstract(){

	}
	public PlayerAbstract getOpponent(){
		return opponent;
	}
	public void setColor(stoneColor color){
		this.color=color;
	}
	public stoneColor getColor(){
		return color;
	}
	public abstract void otherPlayerMoved(String command);
	public void setOpponent(PlayerAbstract opponent){
		this.opponent=opponent;
	}
	public abstract void run();
}
