package Server.Main;

import java.net.ServerSocket;
import java.util.AbstractMap;
import java.util.HashMap;

/**
 * Created by jakub on 12/2/16.
 * gameSet - storing every game, by it's ID, the same ID that allows player to connect to game.
 * initialWords from Client are JOIN-xx, where xx is ID of the game wanted to join
 * or CREATE-xx where xx is the size of the board.
 * Games are only for 2 people, we're preventing scenario where 3rd person tries to join to full lobby
 */
public class ServerGO {
	private static final int PORT = 8901;
	private static AbstractMap<String, GameGO> gameSet;
	private static int gameID = 0;

	public static void main(String[] agrs) throws Exception {
		gameSet = new HashMap<>();
		ServerSocket serverListener = new ServerSocket(PORT);
		System.out.println("Server has been launched.");
		try {
			while (true) {
				GameGO newGame=new GameGO();
				GameGO.Player player=newGame.new Player(serverListener.accept());
				newGame.setCurrentPlayer(player);
				try {
					String line = player.setBoard();
					String[] commands = line.split("-");
					System.out.println("Initial words:" + commands);
					if (commands.length == 2) {
						if (commands[0] == "JOIN") {
							GameGO.Player ToBeJoined=gameSet.get(commands[1]).getCurrentPlayer();
							if(ToBeJoined.getOpponent()==null){
								ToBeJoined.setOpponent(player);
								ToBeJoined.start();
								ToBeJoined.getOpponent().start();
							}else{
								ToBeJoined.cannotJoin();
							}
						} else {
							if (commands[1] == "13") {
								newGame.setSize(13);
							} else if (commands[1] == "19") {
								newGame.setSize(19);
							} else {
								newGame.setSize(9);
							}
							gameSet.put(new Integer(gameID).toString(),newGame);
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