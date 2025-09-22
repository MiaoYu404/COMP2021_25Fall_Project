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

    double det(Line l) { return direction().det(l.direction()); }

    Line reverse() { return new Line(points[1],  points[0]); }

    boolean inside(Shape s) {
        // TODO line inside any Shape
        if (s instanceof Circle c) {
            return points[0].inside(c) &&  points[1].inside(c);
        }
        for (Point p : points) {
            if (!p.inside(s)) return false;
        }
        return true;
    }

    boolean intersects(Line l) { return Lines.intersects(this, l); }
    boolean intersects(Rectangle r) { return ComputingGeometry.intersects(this, r); }
    boolean intersects(Circle c) { return ComputingGeometry.intersects(this, c); }

    @Override
    public boolean equals(Object o) {
        // TODO: two identical line to be equal.
        return false;
    }

    @Override
    public String toString() {
        return points[0].toString() + " -> " + points[1].toString();
    }

    @Override
    public Rectangle boundingBox() {
        Point topLeft = new Point(Math.min(points[0].x(), points[1].x()), Math.max(points[0].y(), points[1].y()));
        double width = Math.abs(points[0].x() - points[1].x());
        double height = Math.abs(points[0].y() - points[1].y());
        String bbName = "Bounding Box of '" + name + "'";
        return new Rectangle(bbName, topLeft, width, height);
    }
}
