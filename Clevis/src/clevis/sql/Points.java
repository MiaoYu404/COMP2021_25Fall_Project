package clevis.sql;

import clevis.model.shape.Point;

/**
 * functions between points
 */
public class Points {
    /**
     * @param a vector a
     * @param b vector b
     * @return a new vector which is the result of adding two vector together.
     */
    public static Point add(Point a, Point b){ return new Point(a.x() + b.x(), a.y() + b.y()); }

    /**
     * @param a vector a
     * @param b vector b
     * @return a new vector which is the result of minus the first vector by the second.
     */
    public static Point minus(Point a, Point b){  return new Point(a.x() - b.x(), a.y() - b.y()); }

    /**
     * @param p vector
     * @param c constant
     * @return a new vector which is the vector p times by a constant c.
     */
    public static Point multiply(Point p, double c) { return new  Point(p.x() * c, p.y() * c); }

    /**
     * @param p vector
     * @param c constant
     * @return a new vector which is the vector p divided by a constant c.
     */
    public static Point divide(Point p, double c) {
        if (c == 0.0) throw new ArithmeticException("Division by zero.");
        return new  Point(p.x() / c, p.y() / c);
    }

    /**
     * copy of a size
     * @param original original array
     * @param size size
     * @return copy
     */
    public static Point[] copyOf(Point[] original, int size) {
        if (original == null) throw new NullPointerException();
        if (original.length < size) throw new IllegalArgumentException();
        Point[] ret = new Point[size];
        for (int i = 0; i < size; i++) ret[i] = new Point(original[i]);
        return ret;
    }

    /**
     * copy of a range
     * @param original the array
     * @param from from index
     * @param to to index
     * @return copy of range
     */
    public static Point[] copyOfRange(Point[] original, int from, int to) {
        if (original == null) throw new NullPointerException();
        if (original.length < to || original.length < from) throw new IllegalArgumentException();
        if (from > to) throw new IllegalArgumentException();
        Point[] ret = new Point[to - from];
        for (int i = from; i < to; i++) ret[i - from] = new Point(original[i]);
        return ret;
    }
}