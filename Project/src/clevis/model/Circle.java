package clevis.model;

public class Circle extends Shape{
    private double r;

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

    @Override
    public String toString() {
        String rsl = "\"" + name + "\"<Circle>:";
        rsl += "\nCenter:" + points[0] + "\nRadius:" + r;
        return rsl;
    }

    @Override
    public void printInfo() {
        System.out.println(this);
    }

    @Override
    public Rectangle boundingBox() {
        String bbName = "Bounding Box of '" + name + "'";
        return new Rectangle(bbName, Points.add(points[0], new Point(r * -1, r)), r * 2, r * 2);
    }
}
