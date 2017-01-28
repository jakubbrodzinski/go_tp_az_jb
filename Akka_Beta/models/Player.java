package models;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import models.Commands.*;
import models.GameLogic.Enums.BoardPoint;
import play.Logger;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.WebSocket;

/**
 * Created by jakub on 1/27/17.
 */
public class Player extends UntypedActor {

	public final ActorRef GM;
	protected WebSocket.In<String> in;
	protected WebSocket.Out<String> out;

	public Player(WebSocket.In<String> _in,WebSocket.Out<String> _out,ActorRef _GM){
		in=_in;
		out=_out;
		GM=_GM;

		in.onMessage(new Callback<String>() {
			@Override
			public void invoke(String event) {
				Object message;
				String[] command_splited=event.split("-");
				switch (command_splited[0]){
					case "MOVE":
						int row = 0;
						try {
							row = Integer.parseInt(command_splited[2]);
						} catch (NumberFormatException e) {
							System.out.println("Something wrong with parsing to Integer");
						}
						message=new Move(new BoardPoint(command_splited[1].charAt(0),row));
						break;
					case "CONCEDE":
						message=new Concede();
						break;
					case "PASS":
						message=new Pass();
						break;
					case "PROPOSITION":
						message=new Proposition(event);
						break;
					case "ENDPROPOSITION":
						message=new EndProposition(event);
						break;
					case "DENY":
						message=new Deny();
						break;
					case "QUIT":
						message=new Quit();
						break;
					default:
						System.out.println("Bledny komunikat od clineta!");
						return;
				}
				try {
					GM.tell(message, getSelf());
					//out.write(s);
				}catch(Exception e){
					Logger.error("invokeError");
				}
			}
		});

		in.onClose(new Callback0() {
			@Override
			public void invoke() {
				try{
					GM.tell(new Quit(),getSelf());
					getSelf().tell(new Quit(),getSelf());
				}catch(Exception e){
					Logger.error("invokeError");
				}

			}
		});
	}

	@Override
	public void onReceive(Object message) throws Exception {
			out.write(message.toString());
	}
}
