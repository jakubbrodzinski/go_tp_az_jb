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
        System.out.println("game room nnmber/size/command: " + roomNumber + " " + roomSize + " " + command);

        if(command == null) {
            flash("error", "Nie wprowadzono danych");
            return redirect(routes.Application.index());
        }
        else if(command.equals("JOIN")) {
            if(roomNumber == -1) {
                flash("error", "Nie wprowadzono danych");
                return redirect(routes.Application.index());
            }
            else {
                return ok(game.render(roomNumber, 9, command));
            }
        }
        else if(command.equals("CREATE")) {
            if(roomSize == -1 || (roomSize != 9 && roomSize != 13 && roomSize != 19)) {
                flash("error", "Nie wprowadzono danych");
                return redirect(routes.Application.index());
            }
            else {
                return ok(game.render(0, 9, command));
            }
        }
        else if(command.equals("CREATE-BOT")) {
            if(roomSize == -1 || (roomSize != 9 && roomSize != 13 && roomSize != 19)) {
                flash("error", "Nie wprowadzono danych");
                return redirect(routes.Application.index());
            }
            else {
                return ok(game.render(0, 9, command));
            }
        }
        else {
            flash("error", "Nie wprowadzono danych");
            return redirect(routes.Application.index());
        }
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
