package models.Commands;

/**
 * Created by jakub on 1/28/17.
 */
public class CorrectMessage extends Change {
	public CorrectMessage(Change change){
		super(change);
	}
	public String toString(){
		return correctString();
	}
}
