package Klient;

/**
 * Created by arek on 12/7/16.
 */
public class StoneLocationParser {
    public static StoneLocation parseStoneLocation(int x, int y) {
        StoneLocation location;
        String helperX;
        int helperY = y/35;

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
        location = new StoneLocation(helperX, helperY+1);
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
        helperY = Integer.parseInt(y)-1;
        location = new StoneLocation(helperX, helperY);

        return location;
    }
}
