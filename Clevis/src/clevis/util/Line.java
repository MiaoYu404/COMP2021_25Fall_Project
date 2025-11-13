package clevis.util;

import clevis.sql.Points;
import clevis.sql.Geometry;

/**
 * class of Line
 */
public class Line implements Shape{
    private String name;
    private Point from, to;

    private Shape father;

    /**
     * construct with no parameter
     */
    public Line() { }

    /**
     * @param _from point from
     * @param _to point to
     */
    public Line(Point _from, Point _to) {
        from = new Point(_from);
        to = new Point(_to);
    }

    /**
     * @param _name name of the line
     * @param _from point from
     * @param _to point to
     */
    public Line(String _name, Point _from, Point _to) {
        this(_from, _to);
        name = _name;
    }

    /**
     * @param o Line need to be copied
     */
    public Line(Line o) {
        this(o.name(), o.from(), o.to());
    }

    /**
     * @return name.
     */
    @Override
    public String name() { return name; }

    /**
     * @return from point
     */
    public Point from() { return new Point(from); }

    /**
     * @return to point
     */
    public Point to() { return to; }

    @Override
    public Shape getFather() { return father; }

    /**
     * set father shape
     * @param _father new father
     */
    @Override
    public void setFather(Shape _father) { father = _father; }

    @Override
    public boolean haveFather() { return father != null; }

    /**
     * @return Direction vector of the line.
     */
    public Point direction() { return Points.minus(to, from); }

    /**
     * @param l the other line.
     * @return det product
     */
    public double det(Line l) { return direction().det(l.direction()); }

    /**
     * @return a line in reverse direction
     */
    public Line reverse() { return new Line(to, from); }

    /**
     * @param s shape
     * @return whether line is inside the shape's bounding box.
     */
    public boolean inside(Shape s) {
        // TODO line inside any Shape
        return from.inside(s) && to.inside(s);
    }

    /**
     * @param s other shape
     * @return whether intersects
     */
    public boolean intersects(Shape s) {
        return Geometry.intersects(this, s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof Line l) {
            return from.equals(l.from()) && to.equals(l.to()) && name.equals(l.name());
        }
        return false;
    }

    @Override
    public String toString() {
        return from.toString() + " -> " + to.toString();
    }

    @Override
    public void move(double dx, double dy) {
        from.add(dx, dy); to.add(dx, dy);
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
