package Klient;

/**
 * Created by arek on 12/7/16.
 */
public class StoneLocationParser {
    public static StoneLocation parseStoneLocation(int x, int y) {
        StoneLocation location;
        String helperX;
        int helperY = y;

        switch(x) {
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
                helperX = "U";
                break;
            case 15:
                helperX = "P";
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
        location = new StoneLocation(helperX, helperY);
        return location;
    }
}
