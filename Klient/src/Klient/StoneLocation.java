package Klient;

/**
 * Created by arek on 12/7/16.
 */
public class StoneLocation
{
    private String x;
    private int y;

    public StoneLocation(String x, int y) {
        this.x = x;
        this.y = y;
    }
    public String getX() {
        return x + "-";
    }
    public int getY() {
        return y;
    }
}
