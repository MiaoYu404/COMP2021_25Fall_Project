package clevis.model;

import static clevis.model.ComputingGeometry.sign;

/**
 * class of Line
 */
public class Line implements Shape{
    private String name;
    private Point from, to;

    /**
     * construct with no parameter
     */
    Line() { }

    /**
     * @param _from point from
     * @param _to point to
     */
    Line(Point _from, Point _to) {
        from = new Point(_from);
        to = new Point(_to);
    }

    /**
     * @param _name name of the line
     * @param _from point from
     * @param _to point to
     */
    Line(String _name, Point _from, Point _to) {
        this(_from, _to);
        name = _name;
    }

    /**
     * @param o Line need to be copied
     */
    Line(Line o) {
        this(o.name(), o.from(), o.to());
    }

    /**
     * @return name.
     */
    String name() { return name; }

    /**
     * @return from point
     */
    Point from() { return new Point(from); }

    /**
     * @return to point
     */
    Point to() { return to; }

    /**
     * @return Direction vector of the line.
     */
    Point direction() { return Points.minus(to, from); }

    /**
     * @param l the other line.
     * @return det product
     */
    double det(Line l) { return direction().det(l.direction()); }

    /**
     * @return vector in reverse direction
     */
    Line reverse() { return new Line(to, from); }

    /**
     * @param s shape
     * @return whether line is inside the shape's bounding box.
     */
    boolean inside(Shape s) {
        // TODO line inside any Shape
        return from.inside(s) && to.inside(s);
    }

    /**
     * @param s other shape
     * @return whether intersects
     */
    public boolean intersects(Shape s) {
        return ComputingGeometry.intersects(this, s);
    }

    @Override
    public boolean equals(Object o) {
        // TODO: two identical line to be equal.
        return false;
    }

    @Override
    public String toString() {
        return from.toString() + " -> " + to.toString();
    }

    @Override
    public void move(double dx, double dy) {

    }

    @Override
    public Rectangle boundingBox() {
        Point topLeft = new Point(Math.min(from.x(), to.x()), Math.max(from.y(), to.y()));
        double width = Math.abs(from.x() - to.x());
        double height = Math.abs(from.y() - to.y());
        String bbName = "Bounding Box of '" + name + "'";
        return new Rectangle(bbName, topLeft, width, height);
    }
}
