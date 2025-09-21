package clevis.model;

public class Shape {
    String name = "(untitled)";
    Point[] points;
    Line[] lines;

    public void move(double dx, double dy) {
        for (Point p : points) { p.add(dx, dy); }
        for (Line l : lines) { l.move(dx, dy); }
    }

    // print the info of this shape
    public void printInfo() {
        System.out.println(this);
    }

    // output a boundingBox
    public Rectangle boundingBox() {
        return null;
    }

    @Override
    public String toString() {
        String ret = "\"" + name + "\"<NO TYPE>";
        return ret;
    }
}
