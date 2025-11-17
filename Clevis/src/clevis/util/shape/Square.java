package clevis.util.shape;

import clevis.sql.Points;

import java.util.Arrays;

/**
 * class of Square
 */
public class Square extends Rectangle {
    private String name;
    private double side;

    /**
     * construct without parameter
     */
    public Square() {
        this(new Point(0, 0), 1);
    }

    /**
     * @param p mid-point
     * @param _side side length
     */
    public Square(Point p, double _side) {
        super(Points.add(p, new Point(_side / -2, _side / 2)), _side, _side);
        this.side = _side;
    }

    /**
     * @param _name     name
     * @param p         middle
     * @param _side     side length
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

    /**
     * @return      center of the square
     */
    public Point center() {
        return new Point(points[0].x() + side / 2, points[0].y() - side / 2);
    }

    @Override
    public String toString() {
        // TODO: 加入边长
        StringBuilder ret = new StringBuilder();
        if (name != null) ret.append(name).append(" ");
        ret.append("<Rectangle>\n")
                .append("\t┌────┐\t").append("middle: ").append(center()).append("\n")
                .append("\t│    │\t").append("side = ").append(side).append("\n")
                .append("\t└────┘\t").append("\n");
        return ret.toString();
    }
}
