package models;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import models.Commands.*;
import models.GameLogic.BotGO;
import models.GameLogic.Enums.BoardPoint;
import models.GameLogic.Enums.BoardSize;
import models.GameLogic.Enums.stoneColor;

/**
 * Created by jakub on 1/28/17.
 */
public class BotPlayer extends UntypedActor {
	public final ActorRef GM;
	private BotGO botGO;

	public BotPlayer(BoardSize size,stoneColor color,ActorRef _GM){
		botGO=new BotGO(size,color);
		GM=_GM;
	}


	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof Wrong){
			GM.tell(new Concede(),getSelf());
			return;
		}else if(message instanceof Quit || message instanceof Concede){
			return;
		}else if(message instanceof ChangeMessage){
			String[] commands=message.toString().split("-");
			if(commands.length>3 && commands[3].equals("CHANGE")) {
				int i = 1;
				try {
					i = Integer.parseInt(commands[5]);
					BoardPoint opponentMove = new BoardPoint(commands[4].charAt(0), i);
					botGO.insertChanges(opponentMove);
				} catch (NumberFormatException e) {
					System.out.println("issue with parsing in bot");
					return;
				}
			}
		}
		BoardPoint temp=botGO.nextBotMove();
		GM.tell(new Move(temp),getSelf());
	}
}
