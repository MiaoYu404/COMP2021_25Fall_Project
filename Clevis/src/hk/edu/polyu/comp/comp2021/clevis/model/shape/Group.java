package hk.edu.polyu.comp.comp2021.clevis.model.shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class for Group
 */
public class Group implements Shape{
    private final String name;
    private final List<Shape> members;

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
    public Shape father() {
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
    public void draw(Graphics2D graph) {
        for (Shape s : members) {
            s.draw(graph);
        }
    }

    @Override
    public String shortName() {
        return toString();
    }

    @Override
    public String toString() {
        // TODO: Group toString()
        StringBuilder ret = new StringBuilder();
        if (name != null) ret.append(name).append(" ");
        ret.append("<Group>:\n");
        List<String> memberNames = new ArrayList<>(this.members.size());
        for (Shape s : members) {
            memberNames.add(s.shortName());
        }
        for (String nameBlock : memberNames) {
            // since group contains multiple lines.
            String[] lines = nameBlock.split("\n");
            for (String line : lines) ret.append("\t").append(line).append("\n");
        }
        return ret.toString();
    }

    /**
     * @return      members
     */
    public List<Shape> members() {
        return members;
    }
}
