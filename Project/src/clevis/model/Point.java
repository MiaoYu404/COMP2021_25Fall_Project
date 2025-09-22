package clevis.model;

import static clevis.model.ComputingGeometry.EPS;
import static clevis.model.ComputingGeometry.sign;

public class Point {
    private double x, y;

    Point() { this(0.0, 0.0); }
    Point(double _x, double _y) { this.x = _x; this.y = _y; }
    Point(Point _point) { this.x = _point.x(); this.y = _point.y(); }

    public double x() { return x; }
    public double y() { return y; }
    // add
    void add(Point p) { add(p.x(), p.y()); }
    void add(double _x, double _y) { x += _x; y += _y; }

    // minus
    void minus(Point p) { minus(p.x(), p.y()); }
    void minus(double _x, double _y) { x -= _x; y -= _y; }

    // multiply by #
    void multiply(double c) { x *= c; y *= c; }

    // divide by #
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

    // dot product
    double dot(Point p) { return x * p.x() + y * p.y(); }

    // det product
    double det(Point p) { return x * p.y() - y * p.x(); }

    // return the distance from this point to point p;
    double distance(Point p) { return Points.minus(this, p).abs(); }

    // abs from origin
    double abs() { return Math.sqrt(abs2()); }
    double abs2() { return x * x + y * y; }

    boolean onSegment(Line l) {
        Point p1 = l.points[0], p2 = l.points[1];
        return sign(Points.minus(p1, this).det(Points.minus(p2, this))) == 0
                && sign((p1.x() - x) * (p2.x() - x)) <= 0
                && sign((p1.y() - y) * (p2.y() - y)) <= 0;
    }

    boolean inside(Shape s) {
        if (s instanceof Circle c) {
            return sign(Points.minus(c.points[0], this).abs() - c.r()) <= 0;
        }
        double angle = 0;
        Point p1, p2; Point v1, v2;
        for (int i = 0, j = s.points.length - 1; i < s.points.length; j = i++) {
            p1 = s.points[i]; p2 = s.points[j];
            if (onSegment(new Line(p1, p2))) return true;

            v1 = Points.minus(p1, this);
            v2 = Points.minus(p2, this);
            double res = Math.atan2(v2.y(), v2.x()) - Math.atan2(v1.y(), v1.x());
            res = Math.abs(res);
            if (sign(res - Math.PI) > 0) res = 2 * Math.PI - res;
            angle += res;
        }
        return sign(2 * Math.PI - angle) == 0;
    }

    @Override
    public String toString() {
        return "(" +  x + "," + y + ")";
    }

    // rotate 90 degree anticlockwise
    Point rot90() { return new Point(-y, x); }

    // unit vector
    Point unit() {
        Point p = new Point();
        p.divide(p.abs());
        return p;
    }
}
