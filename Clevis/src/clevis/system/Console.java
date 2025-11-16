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
    public void ungroup(String[] args) {
        String groupName = args[1];
        if (data.get(groupName) instanceof Group) {
            ungroup(args[1]);
        } else throw new IllegalArgumentException("It is not a Group.");
    }

    /**
     * ungroup a group
     * @param name name of the group.
     */
    public void ungroup(String name) {
        Operation op = new OpUngroup(name, data);
        op.call();
    }

    /**
     * delete a shape
     * @param args      arguments
     */
    public void delete(String[] args) {
        String name = args[1];
        if (!data.exists(name)) throw new IllegalArgumentException(name + " not exists");
        delete(name);
    }

    /**
     * delete a shape
     * @param name name of the shape
     */
    public void delete(String name) {
        Operation op = new OpDelete(name, data);
        op.call();
    }

    /**
     * query the bounding box
     * @param args      arguements
     */
    public void boundingBox(String[] args) {
        String shapeName = args[1];

        String info = boundingBox(shapeName);
        if (info == null) {
            printInfo("bounding box query failed.");
        } else {
            printInfo(info);
        }
    }

    /**
     * @param name name of the shape
     * @return the information of the boudning box.
     */
    public String boundingBox(String name) {
        if (!exists(name))
            throw new IllegalArgumentException(name + " does not exist.");

        Rectangle r = (Rectangle) data.get(name).boundingBox();
        if (r == null) return null;
        return ("Bounding box: " + r.points()[0] +  ", " + r.width() + ", " + r.height() + ".");
    }

    /**
     * list the shape at some coordinate
     * @param args      arguments
     */
    public void shapeAt (String[] args) {
        double x = Double.parseDouble(args[1]);
        double y = Double.parseDouble(args[2]);
        Shape res = shapeAt(x, y);
        if (res == null) System.out.println("Shape at " + x + ", " + y + " not found.");
        else list(res);
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
    public void quit() { System.exit(0); }

    /* --- helper methods --- */

    /**
     * @param name name of the shape
     * @return whether the shape exists.
     */
    public boolean exists(String name) { return data.exists(name); }

    /**
     * @param name name of the shape
     * @return whether the shape have father
     */
    public boolean haveFather(String name) {
        if (!exists(name))
            throw new IllegalArgumentException(name + " not found");

        return data.name2Shape().get(name).haveFather();
    }

    /**
     * output information
     * @param content       the content
     */
    public void printInfo(String content) {
        // TODO: maybe logger or output to some terminal.
        System.out.println(content);
    }
}
