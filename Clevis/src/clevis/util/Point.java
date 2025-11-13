package clevis.util;

import clevis.sql.*;

import static clevis.sql.Geometry.*;
import static java.lang.Math.min;

/**
 * class of Point
 */
public class Point {
    private double x, y;

    /**
     * construct with no parameter
     */
    public Point() { }

    /**
     * @param _x x coordinate
     * @param _y y coordinate
     */
    public Point(double _x, double _y) {
        this.x = _x;
        this.y = _y;
    }

    /**
     * @param _point Point need to be copied.
     */
    public Point(Point _point) {
        this.x = _point.x();
        this.y = _point.y();
    }

    /**
     * return the value of x
     * @return x coordinate of this point.
     */
    public double x() {return x; }

    /**
     * return the value of y
     * @return y coordinate of this point.
     */
    public double y() { return y; }

    /**
     * add this point by a vector.
     * @param p vector
     */
    public void add(Point p) { add(p.x(), p.y()); }

    /**
     * add this point by a vector (represented by two values)
     * @param dx change in x
     * @param dy change in y
     */
    public void add(double dx, double dy) { x += dx; y += dy; }

    /**
     * reduce this point by a vector.
     * @param p vector
     */
    public void minus(Point p) { minus(p.x(), p.y()); }

    /**
     * reduce this point by a vector (represented by two values)
     * @param dx change in x
     * @param dy change in y
     */
    public void minus(double dx, double dy) { x -= dx; y -= dy; }

    /**
     * multiply this point by a constant.
     * @param c constant
     */
    public void multiply(double c) { x *= c; y *= c; }

    /**
     * divide this point by a constant.
     * @param c constant
     */
    public void divide(double c) {
        if (c == 0.0) throw new ArithmeticException("Division by zero.");
        x /= c; y /= c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof Point p) {
            return Math.abs(x - p.x()) < EPS && Math.abs(y - p.y()) < EPS;
        }
        return false;
    }

    /**
     * calculate dot product with other vector
     * @param p other vector
     * @return the dot product
     */
    public double dot(Point p) { return x * p.x() + y * p.y(); }

    /**
     * calculate det product with other vector
     * @param p other product
     * @return the det product
     */
    public double det(Point p) { return x * p.y() - y * p.x(); }

    /**
     * distance between two points.
     * @param p other point
     * @return the distance
     */
    public double distance(Point p) { return Points.minus(this, p).abs(); }

    /**
     * @return absolute value of the distance of the vector pointing origin.
     */
    public double abs() { return Math.sqrt(abs2()); }

    /**
     * @return square of value of the distance of the vector pointing origin.
     */
    public double abs2() { return x * x + y * y; }

    /**
     * @param l the line
     * @return whether the point is on the given line.
     */
    public boolean onSegment(Line l) {
        Point p1 = l.from(), p2 = l.to();
        return sign(Points.minus(p1, this).det(Points.minus(p2, this))) == 0
                && sign((p1.x() - x) * (p2.x() - x)) <= 0
                && sign((p1.y() - y) * (p2.y() - y)) <= 0;
    }

    /**
     * @param s shape
     * @return whether the point is inside the shape.
     */
    public boolean inside(Shape s) {
        Rectangle bb = (Rectangle) s.boundingBox();
        double minX = bb.minX(), minY = bb.minY();
        double maxX = bb.maxX(), maxY = bb.maxY();
        return sign(x - minX) >= 0 && sign(maxX - x) >= 0 && sign(y - minY) >= 0 && sign(maxY - y) >= 0;
    }

    /**
     * @param s the shape
     * @return whether the min distance from the "outline" of the shape is less than 0.05;
     */
    public boolean coveredBy(Shape s) {
        return switch (s.getClass().getSimpleName()) {
            case "Rectangle" -> coveredBy((Rectangle) s);
            case "Circle" -> coveredBy((Circle) s);
            case "Line" -> coveredBy((Line) s);
            case "Group" -> coveredBy((Group) s);
            default -> throw new IllegalArgumentException("Not implemented yet.");
        };
    }

    /**
     * @param c the circle.
     * @return whether this point covered by a circle.
     */
    public boolean coveredBy(Circle c) {
        return sign(distance(c.center()) - c.r() - COVERED) <= 0;
    }

    /**
     * @param l the line.
     * @return whether this point covered by a line.
     */
    public boolean coveredBy(Line l) {
        Point u = l.from(), v = l.to();

        Point uC = Points.minus(this, u), uv = l.direction();
        Point vC = Points.minus(this, v), vu = l.reverse().direction();

        double distance = Double.MAX_VALUE;
        if (sign(uC.dot(uv) * vC.dot(vu)) > 0) {
            distance = min(distance, Math.abs(uC.det(uv)) / uv.abs());
        }
        distance = min(distance, uC.abs());
        distance = min(distance, vC.abs());

        return sign(distance - COVERED) <= 0;
    }

    /**
     * @param r the rectangle.
     * @return whether this point covered by a rectangle.
     */
    public boolean coveredBy(Rectangle r) {
        boolean flag = inside(r);
        for (Line l : r.lines())
            flag |=  coveredBy(l);
        return flag;
    }

    /**
     * @param g the group.
     * @return whether this point covered by a group.
     */
    public boolean coveredBy(Group g) {
        boolean ret = false;
        for (Shape member : g.shapes())
            ret |= coveredBy(member);
        return ret;
    }

    @Override
    public String toString() {
        return "(" +  x + "," + y + ")";
    }

    /**
     * @return return a point which can be got from rotate this point by 90 degree anticlockwise.
     */
    public Point rot90() { return new Point(-y, x); }

    /**
     * @return unit vector of the this.
     */
    public Point unit() {
        if (Geometry.sign(this.abs()) == 0) throw new IllegalArgumentException("This is a zero vector.");
        Point p = new Point(this);
        p.divide(p.abs());
        return p;
    }
}