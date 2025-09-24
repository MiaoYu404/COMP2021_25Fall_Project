package clevis.model;

import static clevis.model.ComputingGeometry.*;
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
    void add(double dx, double dy) { x += dx; y += dy; }

    /**
     * reduce this point by a vector.
     * @param p vector
     */
    void minus(Point p) { minus(p.x(), p.y()); }

    /**
     * reduce this point by a vector (represented by two values)
     * @param dx change in x
     * @param dy change in y
     */
    void minus(double dx, double dy) { x -= dx; y -= dy; }

    /**
     * multiply this point by a constant.
     * @param c constant
     */
    void multiply(double c) { x *= c; y *= c; }

    /**
     * divide this point by a constant.
     * @param c constant
     */
    void divide(double c) {
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
    double dot(Point p) { return x * p.x() + y * p.y(); }

    /**
     * calculate det product with other vector
     * @param p other product
     * @return the det product
     */
    double det(Point p) { return x * p.y() - y * p.x(); }

    /**
     * distance between two points.
     * @param p other point
     * @return the distance
     */
    double distance(Point p) { return Points.minus(this, p).abs(); }

    /**
     * @return absolute value of the distance of the vector pointing origin.
     */
    double abs() { return Math.sqrt(abs2()); }

    /**
     * @return square of value of the distance of the vector pointing origin.
     */
    double abs2() { return x * x + y * y; }

    /**
     * @param l the line
     * @return whether the point is on the given line.
     */
    boolean onSegment(Line l) {
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
        // TODO: rewrite inside method.
        Rectangle bb = (Rectangle) s.boundingBox();
        double minX = bb.points()[1].x(), minY = bb.points()[1].y();
        double maxX = bb.points()[3].x(), maxY = bb.points()[3].y();
        return sign(x - minX) >= 0 && sign(maxX - x) >= 0 && sign(y - minY) >= 0 && sign(maxY - y) >= 0;
    }

    /**
     * @param s the shape
     * @return whether the min distance from the "outline" of the shape is less than 0.05;
     */
    public boolean coveredBy(Shape s) {
        // TODO: complete this method
        boolean flag = false;
        switch (s.getClass().getSimpleName()) {
            case "Rectangle":
                Rectangle r = (Rectangle) s;
                flag = inside(new Rectangle(Points.add(r.points()[0], new Point(COVERED * -1, COVERED)), r.width() + 2 * COVERED, r.height() + 2 * COVERED));
                break;
            case "Circle":
                Circle c = (Circle) s;
                flag = sign(distance(c.center()) - c.r() - COVERED) <= 0;
                break;
            case "Line":
                Line l = (Line) s;
                Point u = l.from(), v = l.to();

                Point uC = Points.minus(this, u), uv = l.direction();
                Point vC = Points.minus(this, v), vu = l.reverse().direction();

                double distance = Double.MAX_VALUE;
                if (sign(uC.dot(uv) * vC.dot(vu)) > 0) {
//                    distance = min(distance, vu.det(this) + v.det(u) / uv.abs());
                    distance = min(distance, Math.abs(uC.det(uv)) / uv.abs());
                }
                distance = min(distance, uC.abs());
                distance = min(distance, vC.abs());

                flag |= sign(distance - COVERED) <= 0;
                break;
            case "Group":
                Group g = (Group) s;
                for (Shape member : g.shapes()) {
                    flag |= coveredBy(member);
                }
        }
        return flag;
    }

    @Override
    public String toString() {
        return "(" +  x + "," + y + ")";
    }

    /**
     * @return return a point which can be got from rotate this point by 90 degree anticlockwise.
     */
    Point rot90() { return new Point(-y, x); }

    /**
     * @return unit vector of the this.
     */
    Point unit() {
        Point p = new Point();
        p.divide(p.abs());
        return p;
    }
}
