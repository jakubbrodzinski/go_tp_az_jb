package Server.Main;

/**
 * Created by jakub on 12/3/16.
 */
public class BoardPoint {
	private char horizontal;
	private int vertical;
	public BoardPoint(char h,int v){
		horizontal=Character.toUpperCase(h);
		vertical=v;
	}
	public BoardPoint(int h,int v){
		vertical=v;
		switch (h){
			case 0:
				horizontal='A';
				break;
			case 1:
				horizontal='B';
				break;
			case 2:
				horizontal='C';
				break;
			case 3:
				horizontal='D';
				break;
			case 4:
				horizontal='E';
				break;
			case 5:
				horizontal='F';
				break;
			case 6:
				horizontal='G';
				break;
			case 7:
				horizontal='H';
				break;
			case 8:
				horizontal='J';
				break;
			case 9:
				horizontal='K';
				break;
			case 10:
				horizontal='L';
				break;
			case 11:
				horizontal='M';
				break;
			case 12:
				horizontal='N';
				break;
			case 13:
				horizontal='O';
				break;
			case 14:
				horizontal='P';
				break;
			case 15:
				horizontal='Q';
				break;
			case 16:
				horizontal='R';
				break;
			case 17:
				horizontal='S';
				break;
			case 18:
				horizontal='T';
				break;
			default:
				System.out.println("Something went wrong!");
				horizontal='X';
		}
	}
	public char getHorizontal(){
		return horizontal;
	}
	public int getIntegerHorizontal(){
		switch (horizontal){
			case 'A':
				return 1;
			case 'B':
				return 2;
			case 'C':
				return 3;
			case 'D':
				return 4;
			case 'E':
				return 5;
			case 'F':
				return 6;
			case 'G':
				return 7;
			case 'H':
				return 8;
			case 'J':
				return 9;
			case 'K':
				return 10;
			case 'L':
				return 11;
			case 'M':
				return 12;
			case 'N':
				return 13;
			case 'O':
				return 14;
			case 'P':
				return 15;
			case 'Q':
				return 16;
			case 'R':
				return 17;
			case 'S':
				return 18;
			case 'T':
				return 19;
			default:
					return -1;
		}
	}
	public int getVertical(){
		return vertical;
	}
	public String toString(){
		return horizontal+"-"+new Integer(vertical).toString();
	}
}
