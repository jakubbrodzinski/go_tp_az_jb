package Klient;


/**
 * Created by arek on 12/7/16.
 * Class that stores the location of a single stone
 */
public class StoneLocation {
    /**
     * The X location of the stone in GO-format
     */
    private String x;
    /**
     * The X location of the stone in integer format
     */
    private int xInt;
    /**
     * The Y location of the stone in integer=GO format
     */
    private int y;

    /**
     * Constructor for GO format of StoneLocation
     * @param x String with X location
     * @param y int with Y location
     */
    public StoneLocation(String x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor for integer format
     * @param xInt int with X location
     * @param y int with Y location
     */
    public StoneLocation(int xInt, int y) {

        this.xInt = xInt;
        this.y = y;
    }

    /**
     * Accessor for X location in Go format
     * @return
     */
    public String getX() {
        return x + "-";
    }

    /**
     * Accessor for X location in integer format
     * @return
     */
    public int getxInt() {
        return xInt;
    }

    /**
     * Accessor for Y location in both integer and Go format
     * @return
     */
    public int getY() {
        return y;
    }
}
