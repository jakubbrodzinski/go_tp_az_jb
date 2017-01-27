package models.Commands;

import models.GameLogic.BoardPoint;

/**
 * Created by jakub on 1/27/17.
 */
public class Territory {
	private BoardPoint[] bTerritory,wTerritory;
	private BoardPoint[] blackP,whiteP;

	public Territory(BoardPoint[] bTerritory,BoardPoint[] wTerritory,BoardPoint[] blackP,BoardPoint[] whiteP){
		this.bTerritory=bTerritory;
		this.wTerritory=wTerritory;
		this.blackP=blackP;
		this.whiteP=whiteP;
	}
	public String toString(){
		StringBuilder builder=new StringBuilder("BLACK");
		for (BoardPoint territory3 : bTerritory) {
			builder.append("-").append(territory3.toString());
		}
		builder.append("-WHITE");
		for (BoardPoint territory2 : wTerritory) {
			builder.append("-").append(territory2.toString());
		}
		builder.append("-BLACKP");
		for (BoardPoint territory1 : blackP) {
			builder.append("-").append(territory1.toString());
		}
		builder.append("-WHITEP");
		for (BoardPoint territory : whiteP) {
			builder.append("-").append(territory.toString());
		}
		return builder.toString();
	}

}
