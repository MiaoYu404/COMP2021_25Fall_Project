package clevis.util;

import clevis.model.Point;

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
}