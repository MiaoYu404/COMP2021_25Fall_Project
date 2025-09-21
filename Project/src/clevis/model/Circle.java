package clevis.model;

import static clevis.model.ComputingGeometry.sign;

public class Circle extends Shape{
    public double r;

    Circle() { this(new Point(0, 0), 1); }
    Circle(Point _center, double _r) {
        points = new Point[]{_center};
        r = _r;
    }
    Circle(String _name, Point _center, double _r) {
        this(_center, _r);
        name = _name;
    }
    Circle(Circle o) {
        this(o.name, o.points[0], o.r);
    }

    boolean intersects(Line l) { return ComputingGeometry.intersects(l, this); }
    boolean intersects(Rectangle r) { return  ComputingGeometry.intersects(r, this); }
    boolean intersects(Circle o) { return ComputingGeometry.intersects(o, this); }

    @Override
    public boolean equals(Object o) {
        // TODO: two identical circle to be equal.
        return false;
    }

    @Override
    public String toString() {
        String rsl = "\"" + name + "\"<Circle>:";
        rsl += "\nCenter:" + points[0] + "\nRadius:" + r;
        return rsl;
    }

    @Override
    public Rectangle boundingBox() {
        String bbName = "Bounding Box of '" + name + "'";
        return new Rectangle(bbName, Points.add(points[0], new Point(r * -1, r)), r * 2, r * 2);
    }
}
