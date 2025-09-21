package clevis.model;

import static clevis.model.ComputingGeometry.sign;

public class Line extends Shape{

    Line() { }
    Line(Point from, Point to) {
        points = new Point[]{from, to};
    }
    Line(String _name, Point from, Point to) { this(from, to); name = _name; }
    Line(Line o) { this(o.name, o.points[0], o.points[1]); }

    // return the direction of the vector
    Point direction() { return Points.minus(points[1], points[0]); }

    // point is on the line ?
    boolean include (Point p) {
        return sign(direction().det(
                Points.minus(p, points[0])
        )) > 0;
    }

    @Override
    public String toString() {
        return points[0].toString() + " -> " + points[1].toString();
    }

    @Override
    public void printInfo() {
        System.out.println(this);
    }

    @Override
    public Rectangle boundingBox() {
        Point topLeft = new Point(Math.min(points[0].x, points[1].x), Math.max(points[0].y, points[1].y));
        double width = Math.abs(points[0].x - points[1].x);
        double height = Math.abs(points[0].y - points[1].y);
        String bbName = "Bounding Box of '" + name + "'";
        return new Rectangle(bbName, topLeft, width, height);
    }
}
