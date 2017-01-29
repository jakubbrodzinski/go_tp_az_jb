package models.Commands;

import models.GameLogic.BoardPoint;

/**
 * Created by jakub on 1/27/17.
 */
public class Move {
	private BoardPoint point;
	public Move(BoardPoint p){
		point=p;
	}
	public BoardPoint getPoint(){
		return point;
	}
	public String toString(){
		return "MOVE-"+point.toString();
	}
}
