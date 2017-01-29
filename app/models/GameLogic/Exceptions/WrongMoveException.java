package models.GameLogic.Exceptions;

/**
 * Created by jakub on 12/7/16.
 */
public class WrongMoveException extends Exception {
	public WrongMoveException(){

	}

	public WrongMoveException(String message){
		super(message);
	}
}
