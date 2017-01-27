package models;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import models.Commands.StringM;
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
				try {
					getSelf().tell(new StringM(event), getSelf());
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
					getSelf().tell(new StringM("quit"),getSelf());
				}catch(Exception e){
					Logger.error("invokeError");
				}

			}
		});
	}


	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof StringM){
			out.write(((StringM) message).getValue());
		}
	}

	public void addOpp(ActorRef op){

	}
}
