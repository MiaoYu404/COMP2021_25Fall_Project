package clevis.model;

import java.util.Arrays;

import static clevis.model.ComputingGeometry.eps;
import static clevis.model.ComputingGeometry.sign;
import static java.lang.Math.abs;

public class Rectangle extends Shape{
    double width, height;

    Rectangle() { this(new Point(-0.5, 0.5), 1.0, 1.0); }
    Rectangle(Point p, double _w, double _h) {
        width = _w; height = _h;

        points = new Point[4];
        points[0] = p;
        points[1] = Points.add(p, new Point(0.0, _h * -1.0));
        points[2] = Points.add(p, new Point(_w, _h * -1.0));
        points[3] = Points.add(p, new Point(_w, 0.0));

        lines = new Line[4];
        lines[0] = new Line(points[0], points[1]);
        lines[1] = new Line(points[1], points[2]);
        lines[2] = new Line(points[2], points[3]);
        lines[3] = new Line(points[3], points[0]);
    }
    Rectangle(String _name, Point p, double _w, double _h) { this(p,  _w, _h); name = _name; }
    Rectangle(Rectangle o) {
        name = o.name;
        width = o.width;
        height = o.height;
        points = Arrays.copyOf(o.points, 4);
        lines = Arrays.copyOf(o.lines, 4);
    }
    Rectangle(Rectangle o, String _name) { this(o); name = _name; }

    boolean intersects(Line l) { return ComputingGeometry.intersects(l, this); }

    boolean intersects(Rectangle o) { return ComputingGeometry.intersects(this, o); }

    boolean intersects(Circle c) {
        return  ComputingGeometry.intersects(this, c);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof Rectangle r) {
            return abs(width - r.width) < eps && abs(height - r.height) < eps && points[0].equals(r.points[0]);
        }
        return false;
    }

    // override toString function
    @Override
    public String toString() {
        String ret = "\"" + name + "\"<Rectangle>:";
        ret += "\nPoints:";
        for (int i = 0; i < 4; i++) {
            ret = ret + "\n" + points[i].toString();
        }
        ret = ret + "\nLines:";
        for (int i = 0; i < 4; i++) {
            ret = ret + "\n" + lines[i].toString();
        }
        return ret;
    }

    @Override
    public Rectangle boundingBox() {
        String bbName = "Bounding Box of '" + name + "'";
        return new Rectangle(this, bbName);
    }
}
