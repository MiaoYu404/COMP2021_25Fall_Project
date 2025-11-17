package clevis.util.shape;

import java.util.ArrayList;
import java.util.List;

/**
 * class for Group
 */
public class Group implements Shape{
    private String name;
    private List<Shape> members;

    private Shape father;

    /**
     * @param _name name
     * @param _shapes shapes
     */
    public Group(String _name, ArrayList<Shape> _shapes) {
        name = _name;
        members = new ArrayList<>(_shapes);
    }

    /**
     * @return copy of shapes;
     */
    public List<Shape> shapes() {
        return members;
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
        for (Shape s : members) {
            s.move(dx, dy);
        }
    }

    @Override
    public Shape boundingBox() {
        Rectangle r = null;
        for (Shape s : members) {
            Rectangle tmp = (Rectangle) s.boundingBox();
            if (tmp == null) continue;
            if (r == null) r = tmp;

            double minX = Math.min(r.minX(), tmp.minX());
            double minY = Math.min(r.minY(), tmp.minY());
            double maxX = Math.max(r.maxX(), tmp.maxX());
            double maxY = Math.max(r.maxY(), tmp.maxY());
            r = new Rectangle(new Point(minX, maxY), Math.abs(maxX -  minX), Math.abs(maxY - minY));
            // TODO: why does abs being used here?
        }
        return new Rectangle(r, "Bounding Box of \"" + name + "\"<Group>");
    }

    @Override
    public String toString() {
        // TODO: Group toString()
        return "Group";
    }

    /**
     * @return      members
     */
    public List<Shape> members() {
        return members;
    }
}
