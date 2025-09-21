package clevis.model;

import static clevis.model.ComputingGeometry.eps;

public class Point {
    double x, y;

    Point() { this(0.0, 0.0); }
    Point(double _x, double _y) { this.x = _x; this.y = _y; }
    Point(Point _point) { this.x = _point.x; this.y = _point.y; }

    // add
    void add(Point p) { add(p.x, p.y); }
    void add(double _x, double _y) { x += _x; y += _y; }

    // minus
    void minus(Point p) { minus(p.x, p.y); }
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
            return Math.abs(x - p.x) < eps && Math.abs(y - p.y) < eps;
        }
        return false;
    }

    // dot product
    double dot(Point p) { return 0.0; }

    // det product
    double det(Point p) { return 0.0; }

    // return the distance from this point to point p;
    double distance(Point p) { return 0.0; }

    // abs from origin
    double abs() { return Math.sqrt(abs2()); }
    double abs2() { return x * x + y * y; }

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
