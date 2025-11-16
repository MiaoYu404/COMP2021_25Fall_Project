package clevis.system;

import clevis.sql.*;
import clevis.util.shape.*;
import clevis.util.operation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * class of Console
 */
public class Console {
    // TODO: Question: how to deal with different shapes with the same name
    private final Data data;

    /**
     * construct with no para
     */
    public Console() {
        data = new Data(this);
    }

    /**
     * read the operation throw a string
     * @param op        operation string
     */
    public void readOperation(String op) {
        String[] operation = op.split(" ");
        String operationType = operation[0];
        switch (operationType) {
            // operations
            case "rectangle", "line", "circle", "square" -> add(operation);
            case "group" -> group(operation);
            case "ungroup" -> ungroup(operation);
            case "delete" -> delete(operation);
            case "move" -> move(operation);
            // query
            case "boundingbox" -> boundingBox(operation);
            case "shapeAt" -> shapeAt(operation);
            case "intersect" -> intersects(operation);
            case "list" -> list(operation);
            case "listAll" -> listAll();
            case "quit" -> quit();
        }
    }

    /**
     * add a new shape
     * @param args arguments
     */
    public void add(String[] args) {
        Operation op = new OpAdd(args, data);
        op.call();
    }

    /**
     * group operation call with argument input in line.
     * @param args          arguemnts
     */
    public void group(String[] args) {
        String groupName = args[1];
        List<String> members = new ArrayList<>();
        for (int i = 2; i < args.length; i++) members.add(args[i]);
        group(groupName, members);
    }

    /**
     * @param args arguments.
     * @return a Line.
     */
    public Shape addLine(String[] args) {
        String name = args[1];
        double x1 =  Double.parseDouble(args[2]), y1 = Double.parseDouble(args[3]);
        double x2 =  Double.parseDouble(args[4]), y2 = Double.parseDouble(args[5]);
        Point from = new Point(x1, y1);
        Point to = new Point(x2, y2);
        return new Line(name, from, to);
    }

    /**
     * @param args arguments.
     * @return a Circle.
     */
    public Shape addCircle(String[] args) {
        String name = args[1];
        double x = Double.parseDouble(args[2]), y = Double.parseDouble(args[3]);
        double r = Double.parseDouble(args[4]);
        Point center = new Point(x, y);
        return new Circle(name, center, r);
    }

    /**
     * @param args arguments.
     * @return a Square.
     */
    public Shape addSquare(String[] args) {
        String name = args[1];
        double x = Double.parseDouble(args[2]), y = Double.parseDouble(args[3]);
        double side = Double.parseDouble(args[4]);
        Point midPoint = new Point(x, y);
        return new Square(name, midPoint, side);
    }

    /**
     * @param name name of the shape
     * @param shape the shape object
     */
    public void push(String name, Shape shape) {
        shapes.add(name);
        name2Shape.put(name, shape);
        shape2Name.put(shape, name);
    }

    /**
     * group a list of shapes
     * @param groupName     name of the shape
     * @param members       mamber shapes
     */
    public void group(String groupName, List<String> members) {
        Operation op = new OpGroup(groupName, members, data);
        op.call();
    }

    /**
     * ungroup the group
     * @param args      arguments
     */
    public void removeFather(Shape shape) {
        if (shape == null)
            throw new IllegalArgumentException("Null shape is not allowed.");

        Shape newFather = shape.getFather();
        if (newFather == null)
            throw new IllegalArgumentException("The Shape does not have father.");

        shape.setFather(newFather.getFather());
    }

    /**
     * @param name name of the shape
     * @return whether the shape have father
     */
    public boolean haveFather(String name) {
        if (!exists(name))
            throw new IllegalArgumentException(name + " not found");

        return name2Shape.get(name).haveFather();
    }

    /**
     * ungroup a group
     * @param name name of the group.
     */
    public void ungroup(String name) {
        if (!exists(name))
            throw new IllegalArgumentException(name + " not found.");

        Shape s = name2Shape.get(name);
        if (s instanceof Group g) {
            for (Shape shape : g.shapes()) {
                removeFather(shape);
            }
            g.shapes().clear();
            delete(name);
        } else {
            throw new IllegalArgumentException(name + " is not a Group.");
        }
    }

    /**
     * delete a shape
     * @param name name of the shape
     */
    public void delete(String name) {
        if (!exists(name))
            throw new IllegalArgumentException(name + " does not exist.");

        if (haveFather(name))
            throw new IllegalArgumentException(name + " is inside an existing group.");

        Shape s = name2Shape.get(name);
        if (s instanceof Group g) {
            for (Shape shape : g.shapes()) delete(shape.name());
        }
        shape2Name.remove(s);
        name2Shape.remove(name);
        shapes.remove(name);
    }

    /**
     * @param name name of the shape
     */
    public void getBoundingBox(String name) {
        if (!exists(name))
            throw new IllegalArgumentException(name + " does not exist.");

        Rectangle r = (Rectangle) name2Shape.get(name).boundingBox();
        System.out.println("Bounding box: " + r.points()[0] +  ", " + r.width() + ", " + r.height() + ".");
    }

    /**
     * @param x x coordinate
     * @param y y coordinate
     * @return the front shape at this point, or null means no shape at this point.
     */
    public Shape shapeAt (double x, double y) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape s = name2Shape.get(shapes.get(i));
            if (s.haveFather())
                continue;
            Point p = new Point(x, y);
            if (p.coveredBy(s))
                return s;
        }
        return null;
        // TODO: question for haveFather() use
    }

    /**
     * @param s1 shape 1
     * @param s2 shape 2
     * @return whether two shapes intersects
     */
    public boolean intersects(String s1, String s2) {
        if (!exists(s1) || !exists(s2))
            throw new  IllegalArgumentException(s1 + " or " + s2 + " does not exist.");

        if (haveFather(s1) || haveFather(s2))
            throw new IllegalArgumentException(s1 + " or " + s2 + " is inside an existing group.");

        return Geometry.intersects(name2Shape.get(s1), name2Shape.get(s2));
    }

    /**
     * list the information of the shape
     * @param name name of the shape
     * @return the information of the shape.
     */
    public String list(String name) {
        if (!exists(name))
            throw new IllegalArgumentException(name + " does not exist.");

        if (haveFather(name))
            throw new IllegalArgumentException(name + " is inside an existing group.");
        System.out.println(name2Shape.get(name).toString());
        return name2Shape.get(name).toString();
    }

    /**
     * @return info about all shapes in Z decreasing order
     */
    public String listAll() {
        if (shapes.isEmpty())
            return "";

        String ret = list(shapes.get(shapes.size() - 1));
        for (int i = shapes.size() - 2; i >= 0; i--)
            ret += "\n" + list(shapes.get(i));
        return ret;
    }

    /**
     * move by a vector
     * @param name name of the shape
     * @param dx move along x
     * @param dy move along y
     */
    public void move(String name, double dx, double dy) {
        // TODO: move by a vector;
        if (!exists(name))
            throw new IllegalArgumentException(name + " does not exist.");

        if (haveFather(name))
            throw new IllegalArgumentException(name + " is inside an existing group.");

        Shape s = name2Shape.get(name);
        s.move(dx, dy);
    }

    /**
     * move by a vector;
     * @param name name of the shape
     * @param v vector
     */
    public void move(String name, Point v) { move(name, v.x(), v.y()); }

    /**
     * quit anytime;
     */
    public void quit() {
        System.exit(0);
    }

    /**
     * undo an operation
     */
    public void undo() {
        // TODO: undo method.

    }

    /**
     * redo an operation.
     */
    public void redo() {
        // TODO: redo method.
    }

    /**
     * @param name name of the shape
     * @return whether the shape exists.
     */
    public boolean exists(String name) {
        // TODO: check the shape whether exists.
        return shapes.contains(name);
    }

    /**
     * helper method. Store the operation.
     * @param op operation
     */
    public void storeOperation(String[] op) {
        if (op == null) throw new IllegalArgumentException("Null operator.");
        ops.push(op);
    }
}
