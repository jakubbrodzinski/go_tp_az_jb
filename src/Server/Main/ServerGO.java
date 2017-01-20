package Server.Main;

import Server.Enums.BoardSize;
import Server.Enums.stoneColor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;

import static java.lang.System.exit;

/**
 * Created by jakub on 12/2/16.
 * gameSet - storing every game, by it's ID, the same ID that allows player to connect to game.
 * initialWords from Client are JOIN-xx, where xx is ID of the game wanted to join
 * or CREATE-xx where xx is the size of the board.
 * Games are only for 2 people, we're preventing scenario where 3rd person tries to join to full lobby
 */
public class ServerGO {
	/**
	 *
	 */
	private static final int PORT = 8901;
	private static AbstractMap<String, GameGO> gameSet;
	private static int gameID = 0;
	private static ServerSocket serverListener;

	private static ServerSocket createSocket(int PORT){
		try{
			return new ServerSocket(PORT);
		} catch (IOException e){
			System.out.println("Cannot create Server!");
			exit(1);
		}
		return null;
	}
	//mozna sprobowac uzyc klonowania jezeli to nie zabangla
	public static void main(String[] agrs) throws Exception {
		gameSet = new HashMap<>();
		serverListener = createSocket(PORT);
		System.out.println("Server has been launched.");
		try {
			while (true) {
				GameGO newGame=new GameGO();
				Socket newConnection=serverListener.accept();
				GameGO.Player player=newGame.new Player(newConnection);
				newGame.setCurrentPlayer(player);
				try {
					String line = player.setBoard();
					String[] commands = line.split("-");
					//small test
					System.out.println("Initial words:" + Arrays.toString(commands));
					if (commands.length >= 2) {
						if (commands[0].equals("JOIN")) {
							GameGO ToBeJoined=gameSet.get(commands[1]);
							if(ToBeJoined.getCurrentPlayer().getOpponent()==null){
								GameGO.Player JoiningPlayer=ToBeJoined.new Player(player);
								//GameGO.Player JoiningPlayer=ToBeJoined.new Player(newConnection,player.getInput(),player.getOutput());
								JoiningPlayer.setColor(stoneColor.WHITE);
								JoiningPlayer.setOpponent(ToBeJoined.getCurrentPlayer());
								JoiningPlayer.getOpponent().setOpponent(JoiningPlayer);
								Thread player1=new Thread(JoiningPlayer);
								player1.start();
								Thread player2=new Thread(JoiningPlayer.getOpponent());
								player2.start();
							}else{
								player.signalServer("FULL");
							}
						} else if(commands[0].equals("CREATE")){
							newGame.getCurrentPlayer().setColor(stoneColor.BLACK);
							if (commands[1].equals("13")) {
								newGame.setSize(BoardSize.MEDIUM);
							} else if (commands[1].equals("19")) {
								newGame.setSize(BoardSize.LARGE);
							} else {
								newGame.setSize(BoardSize.SMALL);
							}
							newGame.setGameID(gameID);
							if(commands.length>2 && commands[2].equals("BOT")){
								GameGO.BotGO botGO=newGame.new BotGO(stoneColor.WHITE);
								Thread player1=new Thread(newGame.getCurrentPlayer());
								newGame.getCurrentPlayer().setOpponent(botGO);
								botGO.setOpponent(newGame.getCurrentPlayer());
								System.out.println("Start");
								player1.start();
							}else {
								//player.signalServer(new Integer(gameID).toString());
								gameSet.put(new Integer(gameID).toString(), newGame);
							}
							gameID++;
						}
					} else {
						System.out.println("Problem with arguments during adding new Player");
					}
				} catch (WrongPlayerInitiation e) {
					System.out.println("Issue during adding new Player" + e.getStackTrace());
					continue;
				}
			}
		} finally {
			serverListener.close();
		}
	}
}