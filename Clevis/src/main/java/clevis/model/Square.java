package clevis.model;

import clevis.util.Points;

import java.util.Arrays;

/**
 * class of Square
 */
public class Square extends Rectangle {
    private String name;
    private double side;
    private Point[] points;
    private Line[] lines;

    /**
     * construct without parameter
     */
    public Square() { this(new Point(0, 0), 1); }

    /**
     * @param p mid-point
     * @param _side side length
     */
    public Square(Point p, double _side) {
        super(Points.add(p, new Point(_side / -2, _side / 2)), _side, _side);
    }

    /**
     * @param _name name
     * @param p mid-point
     * @param _side side length
     */
    public Square(String _name, Point p, double _side) {
        this(p, _side);
        name = _name;
    }

    /**
     * make a copy
     * @param o square need to be copied.
     */
    public Square(Square o) {
        name = o.name();
        side = o.side();

        points = Arrays.copyOf(o.points(), 4);
        lines = Arrays.copyOf(o.lines(), 4);
    }

    /**
     * @param o square need to be copied
     * @param _name name
     */
    public Square(Square o, String _name) { this(o); name = _name; }

    /**
     * @return side;
     */
    public double side() { return side; }

    @Override
    public String toString() {
        String ret = "\"" + name + "\"<Square>:";
        ret += "\nPoints:";
        for (int i = 0; i < 4; i++) {
            ret = ret + "\n" + points[i].toString();
        }
        ret = ret + "\nLines:";
        for (int i = 0; i < 4; i++) {
            ret = ret + "\n" + lines[i].toString();
        }
        return ret;
    }
}
