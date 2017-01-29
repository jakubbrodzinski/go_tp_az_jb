package controllers;

import models.GameManager;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.index;

/**
 * Created by jakub on 1/27/17.
 */
public class AppSocket extends Controller {
	public static Result index(){
		return ok(index.render());
		//nazwa strony startowej?
	}

	public static WebSocket<String> GoToGame(final String command){
		return new WebSocket<String>() {
			@Override
			public void onReady(WebSocket.In<String> in,WebSocket.Out<String> out) {
				try{
					GameManager.joinGame(command,in,out);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		};
	}

}
