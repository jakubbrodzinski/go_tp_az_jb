package models;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import models.Commands.StringM;
import models.GameLogic.WrongPlayerInitiation;
import play.libs.Akka;
import play.mvc.WebSocket;

import java.util.ArrayList;

/**
 * Created by jakub on 1/27/17.
 */
public class GameManager extends UntypedActor {

	private static int gameID=0;

	static ActorRef defaultGM=Akka.system().actorOf(Props.create(GameManager.class));

	//public static Map<String,ActorRef> bPlayer=new HashMap<>();
	//public static Map<ActorRef,ActorRef> wPlayer=new HashMap<>();
	public static ArrayList<ActorRef> players=new ArrayList<>();


	public static void joinGame(String command,WebSocket.In<String> _in,WebSocket.Out<String> _out) throws Exception {
		String[] splitted= command.split("-");
		ActorRef player=Akka.system().actorOf(Props.create(Player.class,_in,_out,defaultGM));
		if(splitted[0].equals("CREATE")){
			//bPlayer.put(Integer.toString(gameID++),player);
			players.add(player);
		}else if(splitted[0].equals("JOIN")) {
			if (true) {//!wPlayer.containsKey(splitted[1])) {
				//wPlayer.put(bPlayer.get(splitted[1]), player);
				players.add(player);
			} else {
				throw new WrongPlayerInitiation("Full Room");
			}
		}
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof StringM){
            System.out.println(((StringM) message).getValue());
			String str=((StringM) message).getValue();
			notifyOpponent(message);

		}

	}

	public static void notifyOpponent(Object msg){
		for (ActorRef p: players) {
			p.tell(msg,defaultGM);
			
		}
	}
}
