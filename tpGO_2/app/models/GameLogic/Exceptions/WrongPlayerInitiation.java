package models.GameLogic.Exceptions;

/**
 * Created by jakub on 12/2/16.
 */
public class WrongPlayerInitiation extends Exception {
	public WrongPlayerInitiation() {
	}

	public WrongPlayerInitiation(String message){
		super(message);
	}
}
