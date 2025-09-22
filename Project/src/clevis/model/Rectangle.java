package clevis.model;

import java.util.Arrays;

import static clevis.model.ComputingGeometry.EPS;
import static java.lang.Math.abs;

/**
 * class of Rectangle
 */
public class Rectangle implements Shape{
    private String name;
    private double width, height;
    private Point[] points;
    private Line[] lines;

    private Shape father;

    /**
     * construct with no parameter;
     */
    Rectangle() { }

    /**
     * @param p top-left point
     * @param _w width
     * @param _h height
     */
    Rectangle(Point p, double _w, double _h) {
        width = _w; height = _h;

        points = new Point[4];
        points[0] = p;
        points[1] = Points.add(p, new Point(0.0, _h * -1.0));
        points[2] = Points.add(p, new Point(_w, _h * -1.0));
        points[3] = Points.add(p, new Point(_w, 0.0));

        lines = new Line[4];
        lines[0] = new Line(points[0], points[1]);
        lines[1] = new Line(points[1], points[2]);
        lines[2] = new Line(points[2], points[3]);
        lines[3] = new Line(points[3], points[0]);
    }

    /**
     * @param _name name of the rectangle
     * @param p top-left point
     * @param _w width
     * @param _h height
     */
    Rectangle(String _name, Point p, double _w, double _h) { this(p,  _w, _h); name = _name; }

    /**
     * @param o rectangle need to be copied.
     */
    Rectangle(Rectangle o) {
        name = o.name();
        width = o.width();
        height = o.height();
        points = o.points();
        lines = o.lines();
    }

    /**
     * rename the rectangle
     * @param o rectangle need to be copied
     * @param _name new name
     */
    Rectangle(Rectangle o, String _name) { this(o); name = _name; }

    /**
     * @return name
     */
    public String name() { return name; }

    /**
     * @return width of the rectangle
     */
    public double width() { return width; }

    /**
     * @return height of the rectangle
     */
    public double height() { return height; }

    /**
     * @return copy of points array.
     */
    public Point[] points() { return Arrays.copyOf(points, 4); }

    /**
     * @return copy of lines array.
     */
    public Line[] lines() { return Arrays.copyOf(lines, 4); }

    /**
     * @return father shape
     */
    public Shape father() { return father; }

    /**
     * set father shape
     * @param _father new father
     */
    public void setFather(Shape _father) { father = _father; }

    /**
     * @return minX
     */
    public double minX() { return points[1].x(); }

    /**
     * @return minY
     */
    public double minY() { return points[1].y(); }

    /**
     * @return maxX
     */
    public double maxX() { return points[3].x(); }

    /**
     * @return maxY
     */
    public double maxY() { return points[3].y(); }

    /**
     * @param s other shape
     * @return whether intersects
     */
    public boolean intersects(Shape s) {
        return ComputingGeometry.intersects(this, s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof Rectangle r) {
            return abs(width - r.width()) < EPS
                    && abs(height - r.height()) < EPS
                    && points[0].equals(r.points()[0])
                    && name.equals(r.name());
        }
        return false;
    }

    // override toString function
    @Override
    public String toString() {
        String ret = "\"" + name + "\"<Rectangle>:";
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

    @Override
    public void move(double dx, double dy) {
        for (Point p : points) {
            p.add(dx, dy);
        }
        for (Line l : lines) {
            l.move(dx, dy);
        }
    }

    @Override
    public Rectangle boundingBox() {
        String bbName = "Bounding Box of '" + name + "'";
        return new Rectangle(this, bbName);
    }
}
