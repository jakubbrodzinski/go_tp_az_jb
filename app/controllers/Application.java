package controllers;

import models.GameManager;
import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render());
    }

    public static Result gameRoom(int roomSize, int roomNumber, String command) {
        return ok(game.render(roomNumber, roomSize, command));
    }

    public static Result gameRoomJs(int roomNumber, int size, String command) {
        return ok(views.js.game.render(roomNumber, size, command));
    }

    public static WebSocket<String> initializeConnection(final int roomNumber, final int size, final String type) {
        return new WebSocket<String>() {
            @Override
            public void onReady(In<String> in, Out<String> out) {
                try{
                    if(type.equals("CREATE-BOT")) {
                        GameManager.joinGame("CREATE-"+Integer.toString(size)+"-BOT", in, out);
                    }
                    else if(type.equals("CREATE")){
                        GameManager.joinGame(type+"-"+Integer.toString(size), in, out);
                    }
                    else {
                        GameManager.joinGame(type+"-"+Integer.toString(roomNumber), in, out);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

        };
    }
}
