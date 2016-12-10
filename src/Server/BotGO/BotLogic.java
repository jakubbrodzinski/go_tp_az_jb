package Server.BotGO;

import Server.Enums.BoardSize;
import Server.Enums.stoneColor;
import Server.Main.BoardPoint;

/**
 * Created by jakub on 12/9/16.
 */
public class BotLogic {
	private int size;
	private stoneColor botColor;
	private stoneColor[][] board;

	public BotLogic(){}
	public void setBotColor(stoneColor col){
		botColor=col;
	}
	public void setSize(BoardSize e){
		size=e.getSize();
		board=new stoneColor[size][size];
		for(stoneColor[] arr: board){
			for(stoneColor point : arr){
				point=stoneColor.UNDEFINED
			}
		}
	}
	public BotLogic(BoardSize e){
		size=e.getSize();
		board=new stoneColor[size][size];
	}

	public void insertChanges(BoardPoint point,BoardPoint[] points){
		board[point.getIntegerHorizontal()][point]
	}
}
