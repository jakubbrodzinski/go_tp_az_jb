package Server.Main;

/**
 * Created by jakub on 12/3/16.
 */
public class BoardPoint {
	private char horizontal;
	private int vertical;
	public BoardPoint(char h,int v){
		horizontal=h;
		vertical=v;
	}
	public char getHorizontal(){
		return horizontal;
	}
	public int getVertical(){
		return vertical;
	}
}
