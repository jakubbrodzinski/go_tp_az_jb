package Server.Main;

import Server.BoardSize;
import Server.stoneColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by jakub on 12/2/16.
 * This class is a adapter between our class logic and server, this class is a interpreter of Client's commands.
 * Class decides what server should do. Usually send particular move to check if its correct. If it is correct move
 * after passing info 'bout it to the client it sends which points in the board changed.
 */
public class GameGO extends GameLogicGO {
	private Player currentPlayer;
	private int gameID;


	public GameGO(){
	}
	public GameGO(BoardSize size){
		this.setSize(size);
	}

	public void setGameID(int ID){
		gameID=ID;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	private void changeTurn(String statement){
		currentPlayer=currentPlayer.opponent;
		currentPlayer.otherPlayerMoved(statement);
	}

	class Player extends Thread {
		private Socket socket;
		private BufferedReader input;
		private PrintWriter output;

		private Player opponent;
		private stoneColor color;
		public Player() { }
		public Player(Socket socket){
			try{
				this.socket=socket;
				input=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				output=new PrintWriter(socket.getOutputStream(),true);
			} catch (IOException e){
				System.out.println(e.getStackTrace());
			}
		}
		public Player(Socket sock,BufferedReader bufferedReader,PrintWriter printWriter){
			socket=sock;
			input=bufferedReader;
			output=printWriter;
		}

		public String setBoard() throws WrongPlayerInitiation{
			try {
				String line = input.readLine();
				return line;
			} catch (IOException e){
				throw new WrongPlayerInitiation();
			}
		}
		//do i need it ?
		public PrintWriter getOutput() { return output; }
		public BufferedReader getInput() { return input; }
		//during setting up match two players have to be connected with eachother
		public Player getOpponent() {
			return opponent;
		}
		public void setOpponent(Player opponent) {
			this.opponent = opponent;
		}

		public stoneColor getColor() {
			return color;
		}
		public void setColor(stoneColor color) {
			this.color = color;
		}

		public void otherPlayerMoved(String command){
			output.println(command);
		}
		public void run(){
			try {
				System.out.println(color+" connected "+gameID);
				//if BLACK you make first move
				if (color == stoneColor.BLACK) {
					output.println("BLACK-"+gameID);
				} else {
					output.println("WHITE-"+gameID);
				}
				//examples of given lines : MOVE-X-Y,PASS,CONCEDE,QUIT
				//examples of sent lines : PASS,CONCEDE,QUIT,MOVE-X-Y,CHANGE-A-B-C-D-(...)
				while (true) {
					String command = input.readLine();
					System.out.println(command);
					String[] command_splited = command.split("-");
					if (command_splited[0].equals("MOVE")){
						BoardPoint moveToTest=new BoardPoint(command_splited[1].charAt(0),command_splited[2].charAt(0));
						BoardPoint[] changesArr=nextMove(moveToTest,this.color);
						if(changesArr!=null){
							StringBuilder Builder=new StringBuilder("");
							for(int i=0;i<changesArr.length;i++){
								Builder.append("-"+changesArr[i].toString());
							}
							String BuilderString=Builder.toString();
							output.println("CORRECT"+BuilderString);
							changeTurn("CHANGE-"+moveToTest.toString()+BuilderString);
						}else{
							output.println("WRONG");
						}
					}else if(command_splited[0].equals("CONCEDE")){
						changeTurn(command_splited[0]);
					}else if(command_splited[0].equals("PASS")){
						changeTurn(command_splited[0]);
					}else if(command_splited[0].equals("QUIT")){
						changeTurn(command_splited[0]);
						return ;
					}

				}
			}catch(IOException e){
				e.printStackTrace();
			}finally {
				try{
					socket.close();
				}catch (IOException e){	}
			}
		}
	}
}
