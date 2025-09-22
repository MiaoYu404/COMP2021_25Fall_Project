package clevis.system;

import clevis.model.*;
import org.w3c.dom.css.Rect;

import java.util.HashMap;
import java.util.List;

public class Console {
    private List<String> shapes;
    private HashMap<String, Shape> name2Shape;
    private HashMap<Shape, String> shape2Name;

    public void add(String[] args) {

    }

    public void group(List<String> names) {
        // TODO: group shapes;
    }

    public void ungroup(String name) {
        // TODO: ungroup a group
    }

    public void delete(String name) {
        // TODO: delete a shape;
    }

    public Rectangle getBoundingBox(String name) {
        // TODO: get bb of a shape
        return null;
    }

    public Shape shapeAt (double x, double y) {
        //TODO: get the shape at an index;
        return null;
    }

    public String list(String name) {
        // TODO: list info about a shape
        return null;
    }

    /**
     * @return info about all shapes
     */
    public String listAll() {
        // TODO: list all shapes' info
        return null;
    }

    /**
     * move by a vector
     * @param dx move along x
     * @param dy move along y
     */
    public void move(double dx, double dy) {
        // TODO: move by a vector;
    }

    /**
     * move by a vector;
     * @param v vector
     */
    public void move(Point v) { move(v.x(), v.y()); }

    /**
     * quit anytime;
     */
    public void quit() {
        System.exit(0);
    }

    /**
     * @param name name of the shape
     * @return whether the shape exists.
     */
    public boolean exists(String name) {
        // TODO: check the shape whether exists.
        return shapes.contains(name2Shape.get(name));
    }
}
