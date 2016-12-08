package Klient;

/**
 * Created by arek on 12/7/16.
 */
public class StoneLocation
{
    private String x;
    private int xInt;
    private int y;

    public StoneLocation(String x, int y) {
        this.x = x;
        this.y = y;
    }
    public StoneLocation(int xInt, int y) {
        this.xInt = xInt;
        this.y = y;
    }
    public String getX() {
        return x + "-";
    }
    public int getxInt() {
        return xInt;
    }
    public int getY() {
        return y;
    }
}
