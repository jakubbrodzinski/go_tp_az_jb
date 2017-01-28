package models.Commands;

import models.GameLogic.BoardPoint;

/**
 * Created by jakub on 1/27/17.
 */
public class Change {
	private double bPoints;
	private double wPoints;
	private BoardPoint[] changes;
	public Change(BoardPoint[] changes){
		this.changes=changes;
	}
	public Change(BoardPoint[] changes,double b,double w){
		this.changes=changes;
		bPoints=b;
		wPoints=w;
	}
	public String changeString(){
		StringBuilder builder=new StringBuilder("");
		for (BoardPoint point: changes) {
			builder.append("-");
			builder.append(point.toString());
		}
		return "POINTS-" + wPoints + "-" + bPoints + "-CHANGE" + builder.toString();
	}

	public String correctString(){
		StringBuilder builder=new StringBuilder("");
		for (BoardPoint point: changes) {
			builder.append("-");
			builder.append(point.toString());
		}
		return "POINTS-" + wPoints + "-" + bPoints + "-CORRECT" + builder.toString();
	}
}
