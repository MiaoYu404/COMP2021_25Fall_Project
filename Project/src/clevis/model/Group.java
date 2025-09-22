package clevis.model;

import org.w3c.dom.css.Rect;

/**
 * class for Group
 */
public class Group implements Shape{
    private String name;
    private Shape[] shapes;

    /**
     *
     */
    public void ungroup() {
        // TODO: ungroup
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
