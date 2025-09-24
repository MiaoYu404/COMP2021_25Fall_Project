package clevis.model;

import org.w3c.dom.css.Rect;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * class for Group
 */
public class Group implements Shape{
    private String name;
    private List<Shape> shapes;

    private Shape father;

    /**
     * @param _name name
     * @param _shapes shapes
     */
    public Group(String _name, ArrayList<Shape> _shapes) {
        name = _name;
        shapes = new ArrayList<Shape>(_shapes);
    }

    /**
     * @return copy of shapes;
     */
    public List<Shape> shapes() {
        return shapes;
    }

    @Override
    public String name() { return name; }

    @Override
    public Shape getFather() {
        return father;
    }

    @Override
    public void setFather(Shape _shape) {
        father = _shape;
    }

    @Override
    public boolean haveFather() {
        return father != null;
    }

    @Override
    public void move(double dx, double dy) {
        for (Shape s : shapes) {
            s.move(dx, dy);
        }
    }

    @Override
    public Shape boundingBox() {
        Rectangle r = null;
        for (Shape s : shapes) {
            Rectangle tmp = (Rectangle) s.boundingBox();
            if (tmp == null) continue;
            if (r == null) r = tmp;

            double minX = Math.min(r.minX(), tmp.minX());
            double minY = Math.min(r.minY(), tmp.minY());
            double maxX = Math.max(r.maxX(), tmp.maxX());
            double maxY = Math.max(r.maxY(), tmp.maxY());
            r = new Rectangle(new Point(minX, maxY), Math.abs(maxX -  minX), Math.abs(maxY - minY));
        }
        return new Rectangle(r, "Bounding Box of \"" + name + "\"<Group>");
    }

    @Override
    public String toString() {
        return "Group";
    }
}
