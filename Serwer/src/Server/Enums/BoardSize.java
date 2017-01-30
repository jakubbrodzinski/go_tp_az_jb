
package Server.Enums;

/**
 * Created by jakub on 12/3/16.
 */
public enum BoardSize {
	SMALL(9),
	MEDIUM(13),
	LARGE(19);

	protected int size;
	private BoardSize(int s){
		this.size=s;
	}
	public int getSize(){
		return size;
	}
}
