package models.Commands;

import models.GameLogic.Enums.stoneColor;

/**
 * Created by jakub on 1/27/17.
 */
public class Win {
	private stoneColor color;
	private double bPoints,wPoints;

	public Win(stoneColor color,double bPoints,double wPoints){
		this.color=color;
		this.bPoints=bPoints;
		this.wPoints=wPoints;
	}
	public String toString(){
		return "ENDPROPOSITION/"+color+"/"+Double.toString(bPoints)+"/"+Double.toString(wPoints);
	}
}