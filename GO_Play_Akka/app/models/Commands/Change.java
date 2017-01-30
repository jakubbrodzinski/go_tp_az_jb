package models.Commands;

import models.GameLogic.Enums.BoardPoint;

/**
 * Created by jakub on 1/27/17.
 */
public class Change {
	private double bPoints;
	private double wPoints;
	private BoardPoint[] changes;
	public Change(BoardPoint[] changes,double b,double w){
		this.changes=changes;
		bPoints=b;
		wPoints=w;
	}
	public Change(Change change){
		this.bPoints=change.bPoints;
		this.wPoints=change.wPoints;
		this.changes=change.changes;
	}
	protected String changeString(){
		StringBuilder builder=new StringBuilder("");
		for (BoardPoint point: changes) {
			builder.append("-");
			builder.append(point.toString());
		}
		return "POINTS-" + wPoints + "-" + bPoints + "-CHANGE" + builder.toString();
	}

	protected String correctString(){
		StringBuilder builder=new StringBuilder("");
		for (BoardPoint point: changes) {
			builder.append("-");
			builder.append(point.toString());
		}
		return "POINTS-" + wPoints + "-" + bPoints + "-CORRECT" + builder.toString();
	}
}
