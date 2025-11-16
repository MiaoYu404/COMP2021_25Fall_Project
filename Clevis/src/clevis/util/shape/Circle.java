package clevis.util.shape;

import clevis.sql.*;

import static clevis.sql.Geometry.sign;

/**
 * class of Circle.
 */
public class Circle implements Shape{
    private String name;
    private final Point center;
    private final double r;

    /**
     * father (don't know if it can hold multiple fathers) pointer.
     */
    private Shape father;

    /**
     * construct with no parameters
     */
    public Circle() {
        this(new Point(0, 0), 1);
    }

    /**
     * @param _center center point of the circle
     * @param _r radius of the circle
     * Create a circle with center `_center` and radius of `_r`.
     */
    public Circle(Point _center, double _r) {
        center = new Point(_center);
        r = _r;
    }

    /**
     * @param _name name of the circle
     * @param _center center point of the circle
     * @param _r radius of the circle
     */
    public Circle(String _name, Point _center, double _r) {
        this(_center, _r);
        name = _name;
    }

    /**
     * @param o circle need to be copied;
     * Construct with a given circle. Make a copy of the given circle.
     */
    public Circle(Circle o) {
        this(o.name(), o.center(), o.r());
    }

    /**
     * @return name
     */
    @Override
    public String name() { return name; }

    /**
     * @return center point.
     */
    public Point center() { return center; }

    /**
     * @return r, raidus
     */
    public double r() { return r; }

    @Override
    public Shape getFather() { return father; }

    @Override
    public void setFather(Shape _father) { father = _father; }

    @Override
    public boolean haveFather() { return father != null; }

    /**
     * @param s other shape
     * @return whether intersects
     */
    public boolean intersects(Shape s) {
        return Geometry.intersects(this, s);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (o instanceof Circle c) {
            return sign(r - c.r()) == 0 && center.equals(c.center()) && name.equals(c.name());
        }
        return false;
    }

    @Override
    public String toString() {
        String ret = "";
        if (name != null) { ret += "\"" + name + "\"";}
        ret += "<" + this.getClass() + ">:";

        ret += "\nCenter:" + center + "\nRadius:" + r;
        return ret;
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
