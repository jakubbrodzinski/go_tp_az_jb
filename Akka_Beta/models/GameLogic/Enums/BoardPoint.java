package models.GameLogic.Enums;

/**
 * Created by jakub on 12/3/16.
 */
public class BoardPoint {
	private int horizontal;
	private char vertical;
	public BoardPoint(char v,int h){
		vertical=Character.toUpperCase(v);
		horizontal=h;
	}
	public BoardPoint(int v,int h){
		horizontal=h+1;
		switch (v){
			case 0:
				vertical='A';
				break;
			case 1:
				vertical='B';
				break;
			case 2:
				vertical='C';
				break;
			case 3:
				vertical='D';
				break;
			case 4:
				vertical='E';
				break;
			case 5:
				vertical='F';
				break;
			case 6:
				vertical='G';
				break;
			case 7:
				vertical='H';
				break;
			case 8:
				vertical='J';
				break;
			case 9:
				vertical='K';
				break;
			case 10:
				vertical='L';
				break;
			case 11:
				vertical='M';
				break;
			case 12:
				vertical='N';
				break;
			case 13:
				vertical='O';
				break;
			case 14:
				vertical='P';
				break;
			case 15:
				vertical='Q';
				break;
			case 16:
				vertical='R';
				break;
			case 17:
				vertical='S';
				break;
			case 18:
				vertical='T';
				break;
			default:
				System.out.println("Something went wrong!");
				vertical='X';
		}
	}
	public int getHorizontal(){
		return horizontal;
	}
	public int getIntegerVertical(){
		switch (vertical){
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
	public char getVertical(){
		return vertical;
	}
	public String toString(){
		return vertical+"-"+new Integer(horizontal).toString();
	}

	@Override
	public boolean equals(Object obj) {
		BoardPoint obj1;
		if (obj.getClass() == BoardPoint.class) {
			obj1=(BoardPoint) obj;
		}else{
			return false;
		}
		if(this.horizontal==obj1.horizontal && this.vertical==obj1.vertical){
			return true;
		}
		return false;
	}
}
