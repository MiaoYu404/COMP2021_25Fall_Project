package clevis.sql;

import clevis.util.shape.*;

import static clevis.sql.Geometry.sign;

/**
 * functions between lines.
 */
public class Lines {

    /**
     * @param l1 line 1
     * @param l2 line 2
     * @return whether two lines intersects
     */
    public static boolean intersects (Line l1, Line l2) {
        double x1 = l1.from().x(), y1 = l1.from().y();
        double x2 = l1.to().x(), y2 = l1.to().y();
        double x3 = l2.from().x(), y3 = l2.from().y();
        double x4 = l2.to().x(), y4 = l2.to().y();
        if (Double.max(x1, x2) < Double.min(x3, x4)
                || Double.max(y1, y2) < Double.min(y3, y4)
                || Double.max(x3, x4) < Double.min(x1, x2)
                || Double.max(y3, y4) < Double.min(y1, y2)
        ) return false;

        Point v31 = new Point(x1 - x3, y1 - y3), v32 = new Point(x2 - x3, y2 - y3);
        Point v13 = new Point(x3 - x1, y3 - y1), v14 = new Point(x4 - x1, y4 - y1);
        // det (x) tells whether point is on vector's anticlockwise/ clockwise side, or even on the same line.
        if (sign(v31.det(l2.direction()) * v32.det(l2.direction())) > 0
                || sign(v13.det(l1.direction()) * v14.det(l1.direction())) > 0
        ) return false;

        return true;
    }

    /**
     * copy of
     * @param original original
     * @param size size
     * @return copy
     */
    public static Line[] copyOf(Line[] original, int size) {
        if (original == null) throw new NullPointerException();
        if (original.length < size) throw new IllegalArgumentException();
        Line[] ret = new Line[size];
        for (int i = 0; i < size; i++) ret[i] = new Line(original[i]);
        return ret;
    }

    /**
     * copy of range
     * @param original original
     * @param from from
     * @param to to
     * @return copy of range
     */
    public static Line[] copyOfRange(Line[] original, int from, int to) {
        if (original == null) throw new NullPointerException();
        if (original.length < to) throw new IllegalArgumentException();
        if (from > to) throw new IllegalArgumentException();
        Line[] ret = new Line[to - from];
        for (int i = from; i < to; i++) ret[i - from] = new Line(original[i]);
        return ret;
    }
}
