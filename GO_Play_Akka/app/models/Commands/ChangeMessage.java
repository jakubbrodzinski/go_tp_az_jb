package models.Commands;

/**
 * Created by jakub on 1/28/17.
 */
public class ChangeMessage extends Change {
	public ChangeMessage(Change change){
		super(change);
	}

	public String toString(){
		return changeString();
	}
}
