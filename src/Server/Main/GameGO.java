package Server.Main;

import Server.BotGO.PlayerAbstract;
import Server.Enums.BoardSize;
import Server.Enums.stoneColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jakub on 12/2/16.
 * This class is a 'adapter' between our class logic and server, this class is a interpreter of Client's commands.
 * Class decides what server should do. Usually send particular move to check if its correct. If it is correct move
 * after passing info 'bout it to the client it sends which points in the board changed.
 */
/*
TO DO LIST:
1.Zmiana stron!
 */
public class GameGO extends GameLogicGO {
	private PlayerAbstract currentPlayer;
	private int gameID;

	public GameGO() {
	}

	public GameGO(BoardSize size) {
		this.setSize(size);
	}

	public void setGameID(int ID) {
		gameID = ID;
	}

	public PlayerAbstract getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	private void changeTurn(String statement) {
		currentPlayer = currentPlayer.getOpponent();
		currentPlayer.otherPlayerMoved(statement);
	}

	private boolean changeColors = false;
	private boolean pass = false;

	private void changeSites() {
		if (currentPlayer.getColor() == stoneColor.BLACK) {
			currentPlayer.setColor(stoneColor.WHITE);
			currentPlayer.getOpponent().setColor(stoneColor.BLACK);
			currentPlayer = currentPlayer.getOpponent();
		} else {
			currentPlayer.setColor(stoneColor.BLACK);
			currentPlayer.getOpponent().setColor(stoneColor.WHITE);
		}
		FreshStart();
	}

	class BotGO extends PlayerAbstract{
		private stoneColor[][] boardBOT;
		public BotGO() {
			boardBOT=new stoneColor[boardSize.getSize()][boardSize.getSize()];
		}
		public BotGO(stoneColor color){
			System.out.println("Bot has been created succesfully!");
			this.color=color;
			boardBOT=new stoneColor[boardSize.getSize()][boardSize.getSize()];
		}

		public void run() {}


		public void otherPlayerMoved(String command) {
			int p1,p2;
			boolean status=false;
			BoardPoint[] changes=null;
			System.out.println("otherPlayerMovedBOT:"+command);
			if(!command.startsWith("QUIT")) {
				do {
					p1 = ThreadLocalRandom.current().nextInt(0, boardSize.getSize());
					p2 = ThreadLocalRandom.current().nextInt(0, boardSize.getSize());
					try {
						changes = nextMove(new BoardPoint(p1, p2), color);
						status = true;
					} catch (WrongMoveException e) {
					}
				} while (!status);
				StringBuilder Builder = new StringBuilder("");
				for (int i = 0; i < changes.length; i++) {
					Builder.append("-" + changes[i].toString());
				}
				String BuilderString = Builder.toString();
				System.out.println("CORRECT_bot_builder_string" + BuilderString);
				changeTurn("CHANGE" + BuilderString);
			}
		}
	}

	class Player extends PlayerAbstract{
		private Socket socket;
		private BufferedReader input;
		private PrintWriter output;


		public Player() {
		}

		public Player(Socket socket) {
			try {
				this.socket = socket;
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				output = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e) {
				System.out.println(e.getStackTrace());
			}
		}

		public Player(GameGO.Player playa) {
			this.socket = playa.socket;
			this.input = playa.input;
			this.output = playa.output;
		}

		public void signalServer(String message){
			output.println(message);
		}

		public String setBoard() throws WrongPlayerInitiation {
			try {
				String line = input.readLine();
				return line;
			} catch (IOException e) {
				throw new WrongPlayerInitiation();
			}
		}

		public void otherPlayerMoved(String command) {
			output.println(command);
		}

		public void run() {
			System.out.println(color);
			try {
				System.out.println(color + " connected " + gameID);
				//if BLACK you make first move
				if (color == stoneColor.BLACK) {
					output.println("BLACK-"+boardSize.getSize()+"-"+gameID);
				} else {
					output.println("WHITE-" +boardSize.getSize()+"-"+gameID);
				}
				//examples of given lines : MOVE-X-Y,PASS,CONCEDE,QUIT
				//examples of sent lines : PASS,CONCEDE,QUIT,MOVE-X-Y,CHANGE-A-B-C-D-(...)
				while (true) {
					String command = input.readLine();
					//Test
					System.out.println(this.color + "command: " + command);
					String[] command_splited = command.split("-");
					//switch case ??
					if (command_splited[0].equals("MOVE")) {
						int row = 0;
						try {
							row = Integer.parseInt(command_splited[2]);
						} catch (NumberFormatException e) {
							System.out.println("Something wrong with parsing to Integer");
						}
						BoardPoint moveToTest = new BoardPoint(command_splited[1].charAt(0), row);
						try {
							BoardPoint[] changesArr = nextMove(moveToTest, this.color);
							StringBuilder Builder = new StringBuilder("");
							for (int i = 0; i < changesArr.length; i++) {
								Builder.append("-" + changesArr[i].toString());
							}
							String BuilderString = Builder.toString();
							output.println("CORRECT" + BuilderString);
							System.out.println("CORRECT" + BuilderString);
							changeTurn("CHANGE" + BuilderString);
						} catch (WrongMoveException ex) {
							output.println("WRONG");
						}
					} else if (command_splited[0].equals("CONCEDE")) {
						changeTurn(command_splited[0]);
					} else if (command_splited[0].equals("PASS")) {
						//do zrobienia
						//jakas zmienna, która powie nam, że oba gracze spassowali->terytorium
						if(pass==true){
							//terytorium-koniec gry.
						}
						pass=true;
						changeTurn(command_splited[0]);
					} else if (command_splited[0].equals("QUIT")) {
						changeTurn(command_splited[0]);
						break;
					} else if (command_splited[0].equals("CHANGESITES")) {
						changeColors = true;
						changeTurn(command_splited[0]);
					} else if (command_splited[0].equals("AGREE")) {
						changeSites();
						if(color==stoneColor.WHITE){
							output.println("FRESHSTART");
							changeTurn("FRESHSTART");
						}else{
							output.println("FRESHSTART");
							changeTurn("FRESHSTART-W");
						}
						changeColors=false;
					} else if (command_splited[0].equals("DISAGREE")) {
						changeColors=false;
						changeTurn(command_splited[0]);
					} else {
						System.out.println("Unknown command(change turn works)!");
						changeTurn(command_splited[0]);
					}

					if(pass==true){
						pass=false;
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					
					output.println("QUIT");
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
