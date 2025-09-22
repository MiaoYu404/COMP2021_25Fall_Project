package clevis.model;

import static clevis.model.ComputingGeometry.sign;

/**
 * class of Circle.
 */
public class Circle implements Shape{
    private String name;
    private Point center;
    private double r;

    /**
     * construct with no parameters
     */
    Circle() {
        this(new Point(0, 0), 1);
    }

    /**
     * @param _center center point of the circle
     * @param _r radius of the circle
     * Create a circle with center `_center` and radius of `_r`.
     */
    Circle(Point _center, double _r) {
        center = new Point(_center);
        r = _r;
    }

    /**
     * @param _name name of the circle
     * @param _center center point of the circle
     * @param _r radius of the circle
     */
    Circle(String _name, Point _center, double _r) {
        this(_center, _r);
        name = _name;
    }

    /**
     * @param o circle need to be copied;
     * Construct with a given circle. Make a copy of the given circle.
     */
    Circle(Circle o) {
        this(o.name(), o.center(), o.r());
    }

    /**
     * @return name
     */
    public String name() { return name; }

    /**
     * @return center point.
     */
    public Point center() { return center; }

    /**
     * @return r, raidus
     */
    public double r() { return r; }

    /**
     * @param s other shape
     * @return whether intersects
     */
    public boolean intersects(Shape s) {
        return ComputingGeometry.intersects(this, s);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (o instanceof Circle c) {
            return sign(r - c.r()) == 0 && center.equals(c.center());
        }
        return false;
    }

    @Override
    public String toString() {
        String rsl = "\"" + name + "\"<Circle>:";
        rsl += "\nCenter:" + center + "\nRadius:" + r;
        return rsl;
    }

    @Override
    public void move(double dx, double dy) {
        center.add(dx, dy);
    }

    @Override
    public Rectangle boundingBox() {
        String bbName = "Bounding Box of '" + name + "'";
        return new Rectangle(bbName, Points.add(center, new Point(r * -1, r)), r * 2, r * 2);
    }
}
