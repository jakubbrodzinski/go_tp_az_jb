package models;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import models.Commands.StringM;
import models.GameLogic.Enums.BoardSize;
import models.GameLogic.WrongPlayerInitiation;
import play.libs.Akka;
import play.mvc.WebSocket;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jakub on 1/27/17.
 */
public class GameManager extends UntypedActor {

	private static int gameID=0;

	static ActorRef defaultGM=Akka.system().actorOf(Props.create(GameManager.class));
	
	public static Map<String,GameGo> games=new HashMap<>();


	public static void joinGame(String command,WebSocket.In<String> _in,WebSocket.Out<String> _out) throws Exception {
		String[] splitted= command.split("-");
		ActorRef player=Akka.system().actorOf(Props.create(Player.class,_in,_out,defaultGM));
		if(splitted[0].equals("CREATE")){
			BoardSize size;
			if(splitted[1].equals("9")){
				size=BoardSize.SMALL;
			}else if(splitted[1].equals("19")){
				size=BoardSize.LARGE;
			}else{
				size=BoardSize.MEDIUM;
			}
			GameGo newGame=new GameGo(size);
			newGame.setBlackPlayer(player);
			games.put(Integer.toString(gameID++),newGame);
		}else if(splitted[0].equals("JOIN")) {
			if (games.containsKey(splitted[1])) {
				GameGo join=games.get(splitted[1]);
				if(!join.setWhitePlayer(player)){
					throw new WrongPlayerInitiation("Full Room");
				}
			} else {
				throw new WrongPlayerInitiation("Wrong GameID");
			}
		}
	}

	@Override
	public void onReceive(Object message) throws Exception {
		notifyOpponent(message,getSender());

		if(message instanceof StringM){
			String str=((StringM) message).getValue();
			notifyOpponent(message,getSender());

		}

	}

	public static void notifyOpponent(Object msg,ActorRef op){
		GameGo ourGame;
		for(Map.Entry<String,GameGo> entry: games.entrySet()){
			ourGame=entry.getValue();
			if(ourGame.contains(op)){
				ourGame.getOpponent().tell(msg,defaultGM);
			}
		}
	}
}
