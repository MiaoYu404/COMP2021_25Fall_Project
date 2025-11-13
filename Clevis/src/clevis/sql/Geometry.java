package clevis.sql;

import clevis.util.*;

/**
 * functions about Computing Geometry
 */
public class Geometry {
    /**
     * Machine Epsilon
     */
    public static final double EPS = 1e-9;

    /**
     * minimum distance from outline of the shape to be covered.
     */
    public static final double COVERED = 0.05;

    /**
     * @param a value
     * @return considering epsilon, 1 if `a` is greater than 0; 0 if equals to 0; -1 if less than 0;
     */
    public static int sign(double a) {
        return a < -EPS ? -1 : ( a > EPS ? 1 : 0);
    }

    /**
     * @param s1 shape 1
     * @param s2 shape 2
     * @return whether two shapes intersects with their bounding box.
     */
    public static boolean intersects(Shape s1, Shape s2) {
        if (s1 instanceof Rectangle r1 && s2 instanceof Rectangle r2) {
            return intersects(r1, r2);
        }
        return intersects(s1.boundingBox(), s2.boundingBox());
    }

    /**
     * @param r1 rectangle 1
     * @param r2 rectangle 2
     * @return whether rectangles intersects
     */
    public static boolean intersects(Rectangle r1, Rectangle r2) {
        for (Point p : r1.points()) {
            if (p.inside(r2)) return true;
        }
        for (Point p : r2.points()) {
            if (p.inside(r1)) return true;
        }
        return false;
    }
}
