package Server.Main;

import Server.stoneColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by jakub on 12/2/16.
 */
public class GameGO {

	private stoneColor[] board;

	private Player currentPlayer;
	public GameGO(){

	}
	//zrboic tak samo jak w przykladach?
	public GameGO(int size){
		board=new stoneColor[size];
		for(stoneColor e : board)
			e=stoneColor.UNDEFINED;
	}

	public void setSize(int size){
		board=new stoneColor[size];
		for(stoneColor e : board)
			e=stoneColor.UNDEFINED;
	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}


	class Player extends Thread {
		private Socket socket;
		private BufferedReader input;
		private PrintWriter output;

		private Player opponent;
		private stoneColor color;

		public Player(Socket socket){
			try{
				this.socket=socket;
				input=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				output=new PrintWriter(socket.getOutputStream(),true);
			} catch (IOException e){
				System.out.println(e.getStackTrace());
			}
		}

		public String setBoard() throws WrongPlayerInitiation{
			try {
				String line = input.readLine();
				return line;
			} catch (IOException e){
				throw new WrongPlayerInitiation();
			}
		}
		public void cannotJoin(){
			output.println("FULL");
		}
		public Player getOpponent() {
			return opponent;
		}

		public void setOpponent(Player opponent) {
			this.opponent = opponent;
		}
	}
}
