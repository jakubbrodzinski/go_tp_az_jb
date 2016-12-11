package Server.Main;

import Server.BotGO.Bot;
import Server.BotGO.PlayerAbstract;
import Server.Enums.BoardSize;
import Server.Enums.stoneColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
	private double BLACKscore=0;
	private double WHITEscore=6.5;

	private boolean statusQUIT=false;

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

	private synchronized void changeTurn(String statement) {
		currentPlayer = currentPlayer.getOpponent();
		currentPlayer.otherPlayerMoved(statement);
	}

	private boolean pass = false;

	class BotGO extends PlayerAbstract{
		private Bot ourBot;
		public BotGO(stoneColor color){
			System.out.println("Bot has been created succesfully!");
			this.color=color;
			ourBot=new Bot(boardSize,this.color);
		}

		public void run() {}


		public void otherPlayerMoved(String command) {
			int p1,p2;
			boolean status=false;
			BoardPoint[] changes=null;
			System.out.println("otherPlayerMovedBOT:"+command);
			if(!command.startsWith("QUIT")) {
				if(command.startsWith("POINTS")){
					String[] commands=command.split("-");
					if(commands.length>3 && commands[3].equals("CHANGE")) {
						int i = 1;
						try {
							i = Integer.parseInt(commands[5]);
							BoardPoint opponentMove = new BoardPoint(commands[4].charAt(0), i);
							ourBot.insertChanges(opponentMove);
						} catch (NumberFormatException e) {
							System.out.println("issue with parsing in bot");
						}
					}
				}
				try{
					BoardPoint temp=ourBot.nextBotMove();
					System.out.println("our bot move:"+temp);
					changes=nextMove(temp,this.color);
				}catch(WrongMoveException e){
					System.out.println("Wrong move from BOT!");
				}
				if(this.color==stoneColor.BLACK){
					BLACKscore+=(changes.length-1);
				}else if(this.color==stoneColor.WHITE){
					WHITEscore+=(changes.length-1);
				}
				StringBuilder Builder = new StringBuilder("");
				for (int i = 0; i < changes.length; i++) {
					Builder.append("-" + changes[i].toString());
				}
				String BuilderString = Builder.toString();
				System.out.println("CORRECT_bot_builder_string" + BuilderString);
				changeTurn("POINTS-"+WHITEscore+"-"+BLACKscore+"-"+"CHANGE" + BuilderString);
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
					output.println("POINTS-"+WHITEscore+"-"+BLACKscore+"-"+"BLACK-"+boardSize.getSize()+"-"+gameID);
				} else {
					output.println("POINTS-"+WHITEscore+"-"+BLACKscore+"-"+"WHITE-" +boardSize.getSize()+"-"+gameID);
				}
				//examples of given lines : MOVE-X-Y,PASS,CONCEDE,QUIT
				//examples of sent lines : PASS,CONCEDE,QUIT,MOVE-X-Y,CHANGE-A-B-C-D-(...)
				while (!statusQUIT) {
					String command = input.readLine();
					//Test
					if (command == null)
						continue;
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
							//Points counting
							if(currentPlayer.getColor()==stoneColor.BLACK){
								BLACKscore+=(changesArr.length-1);
							}else if(currentPlayer.getColor()==stoneColor.WHITE){
								WHITEscore+=(changesArr.length-1);
							}
							for (int i = 0; i < changesArr.length; i++) {
								Builder.append("-" + changesArr[i].toString());
							}
							String BuilderString = Builder.toString();
							output.println("POINTS-"+WHITEscore+"-"+BLACKscore+"-"+"CORRECT" + BuilderString);
							System.out.println("CORRECT" + BuilderString);
							changeTurn("POINTS-"+WHITEscore+"-"+BLACKscore+"-"+"CHANGE" + BuilderString);
						} catch (WrongMoveException ex) {
							output.println("WRONG");
						}
					} else if (command_splited[0].equals("CONCEDE")) {
						changeTurn(command_splited[0]);
						statusQUIT = true;
					} else if (command_splited[0].equals("PASS")) {
						if(pass){
							BoardPoint[] territories = countTerritories(stoneColor.BLACK);
							StringBuilder Builder = new StringBuilder("BLACK");
							for (int i = 0; i < territories.length; i++) {
								Builder.append("-" + territories[i].toString());
							}
							territories = countTerritories(stoneColor.WHITE);
							Builder.append("-WHITE");
							for (int i = 0; i < territories.length; i++) {
								Builder.append("-" + territories[i].toString());
							}
							String BuilderString = Builder.toString();
							output.println(BuilderString);
							changeTurn(BuilderString);
						}
						pass=true;
						output.println("PASS");
						changeTurn("PASS");
					}else if (command_splited[0].equals("QUIT")) {
						changeTurn(command_splited[0]);
						statusQUIT = true;
					} else {
						System.out.println("Unknown command(change turn works)!");
						changeTurn(command_splited[0]);
					}

					if (command_splited[0]!="PASS") {
						pass = false;
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
