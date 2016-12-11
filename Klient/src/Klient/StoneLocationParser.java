package Klient;

import Klient.View.Stone;

import java.util.ArrayList;

/**
 * Created by arek on 12/7/16.
 */
public class StoneLocationParser {
    public static StoneLocation parseStoneLocation(int x, int y) {
        StoneLocation location;
        String helperX;
        int helperY;

        switch(x/35) {
            case 0:
                helperX = "A";
                break;
            case 1:
                helperX = "B";
                break;
            case 2:
                helperX = "C";
                break;
            case 3:
                helperX = "D";
                break;
            case 4:
                helperX = "E";
                break;
            case 5:
                helperX = "F";
                break;
            case 6:
                helperX = "G";
                break;
            case 7:
                helperX = "H";
                break;
            case 8:
                helperX = "J";
                break;
            case 9:
                helperX = "K";
                break;
            case 10:
                helperX = "L";
                break;
            case 11:
                helperX = "M";
                break;
            case 12:
                helperX = "N";
                break;
            case 13:
                helperX = "O";
                break;
            case 14:
                helperX = "P";
                break;
            case 15:
                helperX = "Q";
                break;
            case 16:
                helperX = "R";
                break;
            case 17:
                helperX = "S";
                break;
            case 18:
                helperX = "T";
                break;
            default:
                helperX = "ZLE";
                break;
        }
        switch(y/35) {
            case 0:
                helperY = ClientState.getInstance().getSize() - 0;
                break;
            case 1:
                helperY = ClientState.getInstance().getSize() - 1;
                break;
            case 2:
                helperY = ClientState.getInstance().getSize() - 2;
                break;
            case 3:
                helperY = ClientState.getInstance().getSize() - 3;
                break;
            case 4:
                helperY = ClientState.getInstance().getSize() - 4;
                break;
            case 5:
                helperY = ClientState.getInstance().getSize() - 5;
                break;
            case 6:
                helperY = ClientState.getInstance().getSize() - 6;
                break;
            case 7:
                helperY = ClientState.getInstance().getSize() - 7;
                break;
            case 8:
                helperY = ClientState.getInstance().getSize() - 8;
                break;
            case 9:
                helperY = ClientState.getInstance().getSize() - 9;
                break;
            case 10:
                helperY = ClientState.getInstance().getSize() - 10;
                break;
            case 11:
                helperY = ClientState.getInstance().getSize() - 11;
                break;
            case 12:
                helperY = ClientState.getInstance().getSize() - 12;
                break;
            case 13:
                helperY = ClientState.getInstance().getSize() - 13;
                break;
            case 14:
                helperY = ClientState.getInstance().getSize() - 14;
                break;
            case 15:
                helperY = ClientState.getInstance().getSize() - 15;
                break;
            case 16:
                helperY = ClientState.getInstance().getSize() - 16;
                break;
            case 17:
                helperY = ClientState.getInstance().getSize() - 17;
                break;
            case 18:
                helperY = ClientState.getInstance().getSize() - 18;
                break;
            default:
                helperY = -3;
                break;
        }
        location = new StoneLocation(helperX, helperY);
        return location;
    }
    public static StoneLocation parsetoStoneLocation(String x, String y) {
        StoneLocation location;
        int helperX;
        int helperY;

        switch(x) {
            case "A":
                helperX = 0;
                break;
            case "B":
                helperX = 1;
                break;
            case "C":
                helperX = 2;
                break;
            case "D":
                helperX = 3;
                break;
            case "E":
                helperX = 4;
                break;
            case "F":
                helperX = 5;
                break;
            case "G":
                helperX = 6;
                break;
            case "H":
                helperX = 7;
                break;
            case "J":
                helperX = 8;
                break;
            case "K":
                helperX = 9;
                break;
            case "L":
                helperX = 10;
                break;
            case "M":
                helperX = 11;
                break;
            case "N":
                helperX = 12;
                break;
            case "O":
                helperX = 13;
                break;
            case "P":
                helperX = 14;
                break;
            case "Q":
                helperX = 15;
                break;
            case "R":
                helperX = 16;
                break;
            case "S":
                helperX = 17;
                break;
            case "T":
                helperX = 18;
                break;
            default:
                helperX = -1;
                break;
        }
        switch(y) {
            case "1":
                helperY = ClientState.getInstance().getSize() - 1;
                break;
            case "2":
                helperY = ClientState.getInstance().getSize() - 2;
                break;
            case "3":
                helperY = ClientState.getInstance().getSize() - 3;
                break;
            case "4":
                helperY = ClientState.getInstance().getSize() - 4;
                break;
            case "5":
                helperY = ClientState.getInstance().getSize() - 5;
                break;
            case "6":
                helperY = ClientState.getInstance().getSize() - 6;
                break;
            case "7":
                helperY = ClientState.getInstance().getSize() - 7;
                break;
            case "8":
                helperY = ClientState.getInstance().getSize() - 8;
                break;
            case "9":
                helperY = ClientState.getInstance().getSize() - 9;
                break;
            case "10":
                helperY = ClientState.getInstance().getSize() - 10;
                break;
            case "11":
                helperY = ClientState.getInstance().getSize() - 11;
                break;
            case "12":
                helperY = ClientState.getInstance().getSize() - 12;
                break;
            case "13":
                helperY = ClientState.getInstance().getSize() - 13;
                break;
            case "14":
                helperY = ClientState.getInstance().getSize() - 14;
                break;
            case "15":
                helperY = ClientState.getInstance().getSize() - 15;
                break;
            case "16":
                helperY = ClientState.getInstance().getSize() - 16;
                break;
            case "17":
                helperY = ClientState.getInstance().getSize() - 17;
                break;
            case "18":
                helperY = ClientState.getInstance().getSize() - 18;
                break;
            case "19":
                helperY = ClientState.getInstance().getSize() - 19;
                break;
            default:
                helperY = -3;
                break;

        }
        location = new StoneLocation(helperX, helperY);

        return location;
    }

}
