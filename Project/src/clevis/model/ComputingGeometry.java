package clevis.model;

import static java.lang.Math.min;

public class ComputingGeometry {
    /**
     * Machine epsilon, the smallest positive floating-point number that represents the gap between 1 and the next
     * representable floating-point number.
     */
    public static final double EPS = 1e-9;

    /**
     * @param val the number need to be sign.
     * @return -1 if the number is smaller than 0.0; 1 if greater than 0.0; 0 if equals to 0.0.
     */
    public static int sign(double val) { return val < -EPS ? -1 : ( val > EPS ? 1 : 0); }

    public static boolean intersects(Line l1, Line l2) {
        return Lines.intersects(l1, l2);
    }

    public static boolean intersects(Line l, Rectangle r) {
        if (l.inside(r)) return true;
        for (Line side : r.lines) {
            if (Lines.intersects(l, side)) return true;
        }
        return false;
    }

    public static boolean intersects(Line l, Circle c) {
        for (Point p : l.points) { if (p.inside(c)) return true; }
        Point u = l.points[0], v = l.points[1];
        Point cCenter = c.points[0];

        Point uC = Points.minus(cCenter, u), uv = l.direction();
        Point vC = Points.minus(cCenter, v), vu = l.reverse().direction();

        double distance = Double.MAX_VALUE;
        if (sign(uC.dot(uv) * vC.dot(vu)) > 0) {
            distance = min(distance, vu.det(c.points[0]) + v.det(u) / uv.abs());
        }
        distance = min(distance, uC.abs());
        distance = min(distance, vC.abs());

        return sign(distance - c.r()) <= 0;
    }

    public static boolean intersects(Rectangle r1, Rectangle r2) {
        for (Point p : r1.points) {
            if (p.inside(r2)) return true;
        }
        return false;
    }

    public static boolean intersects(Rectangle r, Circle c) {
        if (c.points[0].inside(r)) return true;
        for (Line l : r.lines) {
            if (l.intersects(c)) return true;
        }
        return false;
    }

    public static boolean intersects(Circle c1, Circle c2) {
        return sign(Points.minus(c1.points[0], c2.points[0]).abs() - c1.r() - c2.r()) <= 0;
    }
}
