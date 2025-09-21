package clevis.model;

public class Points {
    public static Point add(Point a, Point b){ return new Point(a.x + b.x, a.y + b.y); }
    public static Point minus(Point a, Point b){  return new Point(a.x - b.x, a.y - b.y); }
    public static Point multiply(Point p, double c) { return new  Point(p.x * c, p.y * c); }
    public static Point divide(Point p, double c) {
        if (c == 0.0) throw new ArithmeticException("Division by zero.");
        return new  Point(p.x / c, p.y / c);
    }
}