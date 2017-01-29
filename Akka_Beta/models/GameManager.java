package models;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import models.Commands.*;
import models.GameLogic.Enums.BoardSize;
import models.GameLogic.Enums.stoneColor;
import models.GameLogic.Exceptions.WrongPlayerInitiation;
import models.GameLogic.GameGo;
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
			if (splitted[1].equals("9")) {
				size = BoardSize.SMALL;
			} else if (splitted[1].equals("19")) {
				size = BoardSize.LARGE;
			} else {
				size = BoardSize.MEDIUM;
			}
			GameGo newGame = new GameGo(size);
			newGame.setBlackPlayer(player);
			games.put(Integer.toString(gameID), newGame);
			_out.write("GAMEID-" + Integer.toString(gameID++));
			if(splitted.length>=3 && splitted[2].equals("BOT")){
				ActorRef Bot=Akka.system().actorOf(Props.create(BotPlayer.class,size,stoneColor.WHITE,defaultGM));
				newGame.setWhitePlayer(Bot);
				String bPlayer_M="POINTS-6.5-0-"+"BLACK-"+size.getSize();
				player.tell(bPlayer_M,defaultGM);
			}
		}else if(splitted[0].equals("JOIN")) {
			if (games.containsKey(splitted[1])) {
				GameGo gameGO=games.get(splitted[1]);
				if(!gameGO.setWhitePlayer(player)){
					throw new WrongPlayerInitiation("Full Room");
				}
				String bPlayer_M="POINTS-6.5-0-"+"BLACK-"+gameGO.getBsize().getSize();
				String wPlayer_M="POINTS-6.5-0-"+"WHITE-"+gameGO.getBsize().getSize();
				gameGO.getCurrentPlayer().tell(bPlayer_M,defaultGM);
				gameGO.changeTurn();
				gameGO.getCurrentPlayer().tell(wPlayer_M,defaultGM);
				gameGO.changeTurn();
			} else {
				throw new WrongPlayerInitiation("Wrong GameID");
			}
		}
	}

	@Override
	public void onReceive(Object message) throws Exception {
		GameGo ourGame=null;
		for(Map.Entry<String,GameGo> entry: games.entrySet()){
			GameGo temp=entry.getValue();
			if(temp.contains(getSender())){
				ourGame=temp;
				break;
			}
		}
		if(ourGame==null){
			throw new Exception("No such game");
		}
		Object response=null;
		//po prostu przekaz dalej: Quit,Concede,Deny
		//zrob cos:Pass,Move,Proposition,EndProposition
		//tylko do klienta:
		if(message instanceof Move){
			response=ourGame.MoveOnBoard((Move)message);
			if(response instanceof Change){
				Change change=(Change) response;
				ourGame.getCurrentPlayer().tell(new ChangeMessage(change),defaultGM);
				ourGame.changeTurn();
				ourGame.getCurrentPlayer().tell(new CorrectMessage(change),defaultGM);
				ourGame.changeTurn();
			}else{
				ourGame.getCurrentPlayer().tell(response,defaultGM);
			}
		}else if(message instanceof Pass){
			response=ourGame.Pass();
			notifyBothPlayers(response,ourGame);
			//ourGame.changePass();
		}else if(message instanceof Proposition){
			response=ourGame.Proposition((Proposition) message);
			ourGame.getCurrentPlayer().tell(response,defaultGM);

			//ourGame.changeProp();
		}else if(message instanceof EndProposition){
			response=ourGame.EndProposition((EndProposition) message);
			if( response instanceof EndProposition){
				ourGame.getCurrentPlayer().tell(response,defaultGM);
			}else {
				notifyBothPlayers(response,ourGame);
			}
		}else if(message instanceof SimpleM){ //Quit,Concede,Deny
			notifyBothPlayers(message,ourGame);
			ourGame.changeTurn();
			if(message instanceof Deny){
				ourGame.changeProps();
				return;
			}else{
				games.remove(ourGame);
			}
		}else{
			unhandled(message);
		}
	}

	public static void notifyBothPlayers(Object msg,GameGo gameGo){
		gameGo.getCurrentPlayer().tell(msg,defaultGM);
		gameGo.changeTurn();
		gameGo.getCurrentPlayer().tell(msg,defaultGM);
		gameGo.changeTurn();
	}
}
