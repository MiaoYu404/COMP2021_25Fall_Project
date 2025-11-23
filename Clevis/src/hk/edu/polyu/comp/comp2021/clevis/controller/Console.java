package hk.edu.polyu.comp.comp2021.clevis.controller;

import hk.edu.polyu.comp.comp2021.clevis.Application;
import hk.edu.polyu.comp.comp2021.clevis.model.operation.*;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Group;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Point;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Rectangle;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Shape;
import hk.edu.polyu.comp.comp2021.clevis.model.util.Geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * class of Console
 */
public class Console {
    private final Data data;
    private final Logger logger;

    /**
     * construct with no para
     */
    public Console() {
        data = new Data(this);
        logger = Application.logger();
    }

    /**
     * read the operation though a string
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
            case "undo" -> undo();
            case "redo" -> redo();
            case "quit" -> quit();
            default -> printError("Not a valid operation.");
        }
    }

    /**
     * add a new shape
     * @param args arguments
     */
    public void add(String[] args) {
        try {
            Operation op = new OpAdd(args, data());
            op.call();
            data().insertOp(op);
        } catch (IllegalArgumentException e) {
            // TODO: provide more information about the error.
            printError("Please input the correct operation.");
        } catch (Exception e) {
            printError("Not implemented Exception" + e.getMessage());
        }
    }

    /**
     * group operation call with argument input in line.
     * @param args          arguemnts
     */
    public void group(String[] args) {
        // TODO: implement Exception when argument is wrong.
        String groupName = args[1];
        List<String> members = new ArrayList<>(Arrays.asList(args).subList(2, args.length));
        group(groupName, members);
    }

    /**
     * group a list of shapes
     * @param groupName     name of the shape
     * @param members       mamber shapes
     */
    public void group(String groupName, List<String> members) {
        Operation op = new OpGroup(groupName, members, data());
        op.call();
        data().insertOp(op);
    }

    /**
     * ungroup the group
     * @param args      arguments
     */
    public void ungroup(String[] args) {
        // TODO: implement Exception when argument is wrong.
        String groupName = args[1];
        if (data().get(groupName) instanceof Group) {
            ungroup(args[1]);
        } else printError("It is not a Group.");
    }

    /**
     * ungroup a group
     * @param name name of the group.
     */
    public void ungroup(String name) {
        Operation op = new OpUngroup(name, data());
        op.call();
        data().insertOp(op);
    }

    /**
     * delete a shape
     * @param args      arguments
     */
    public void delete(String[] args) {
        // TODO: implement Exception when argument is wrong.
        String name = args[1];
        if (!data().exists(name)) {
            printError(name + " not exists.");
            return ;
        }
        delete(name);
    }

    /**
     * delete a shape
     * @param name name of the shape
     */
    public void delete(String name) {
        Operation op = new OpDelete(name, data());
        op.call();
        data().insertOp(op);
    }

    /**
     * query the bounding box
     * @param args      arguements
     */
    public void boundingBox(String[] args) {
        // TODO: implement Exception when argument is wrong.
        String shapeName = args[1];

        String info = boundingBox(shapeName);
        if (info == null)
            printError("Bounding box query failed.");
        else
            printInfo(info);
    }

    /**
     * @param name name of the shape
     * @return the information of the boudning box.
     */
    public String boundingBox(String name) {
        if (!exists(name)) {
            printError(name + " not exists.");
            return null;
        }

        Rectangle r = (Rectangle) data().get(name).boundingBox();
        if (r == null) return null;
        return ("Bounding box: " + r.points()[0].x() +  " " + r.points()[0].y() + " " + r.width() + " " + r.height());
    }

    /**
     * list the shape at some coordinate
     * @param args      arguments
     */
    public void shapeAt (String[] args) {
        // TODO: implement Exception when argument is wrong.
        double x = Double.parseDouble(args[1]);
        double y = Double.parseDouble(args[2]);
        Shape res = shapeAt(x, y);
        if (res == null) System.out.println("Shape at " + x + ", " + y + " not found.");
        else list(new String[] {"list", res.name()});              // here, use name.
    }

    /**
     * @param x x coordinate
     * @param y y coordinate
     * @return the front shape at this point, or null means no shape at this point.
     */
    public Shape shapeAt (double x, double y) {
        for (int i = data().size() - 1; i >= 0; i--) {
            Shape s = data().get(i);
            if (s.haveFather())  continue;          // if this is a group, it will not be count in shapeAt() method.

            Point p = new Point(x, y);
            if (p.coveredBy(s)) return s;
        }
        return null;                                // no shape at this point.
    }

    /**
     * @param args      arguments
     */
    public void intersects(String[] args) {
        // TODO: implement Exception when argument is wrong.
        String shape1 = args[1];
        String shape2 = args[2];
        if (intersects(shape1, shape2))
            printInfo(shape1 + " intersects " + shape2 + ".");
        else
            printInfo(shape1 + " does not intersects " + shape2 + ".");
    }

    /**
     * @param s1 shape 1
     * @param s2 shape 2
     * @return whether two shapes intersects
     */
    public boolean intersects(String s1, String s2) {
        if (!exists(s1) || !exists(s2)) {
            printError(s1 + " or " + s2 + " does not exist.");
            return false;
        }

        if (haveFather(s1) || haveFather(s2)) {
            printError(s1 + " or " + s2 + " is inside an existing group.");
            return false;
        }

        return Geometry.intersects(data().get(s1), data().get(s2));
    }

    /**
     * list the information
     * @param args      arguments
     */
    public void list(String[] args) {
        // TODO: implement Exception when argument is wrong.
        String shapeName = args[1];
        String res = list(shapeName);
        if (res == null)
            printError("list operation failed.");
        else
            printInfo(res);
    }

    /**
     * list the information of the shape
     * @param name name of the shape
     * @return the information of the shape.
     */
    public String list(String name) {
        if (!exists(name)) {
            printError(name + " does not exist.");
            return null;
        }

        if (haveFather(name)) {
            printError(name + " is inside an existing group.");
            return null;
        }

        return data().get(name).toString();
    }

    /**
     * list the information of the shape
     * @param shape     the shape Object
     * @return          the information of the shape.
     */
    public String list(Shape shape) {
        // TODO: make Group List more format.
        return shape.toString();
    }

    /**
     * list all shapes
     */
    public void listAll() {
        if (data().isEmpty()){
            printInfo("There is no shape.");
            return ;
        }

        StringBuilder ret = new StringBuilder();
        for (int i = data().size() - 1; i >= 0; i--) {
            if (i != data().size()) ret.append("\n");
            ret.append(list(data().get(i)));
        }

        printInfo(ret.toString());
    }

    /**
     * move by a vector
     * @param args      arguments
     */
    public void move(String[] args) {
        // TODO: implement Exception when argument is wrong.
        String name = args[1];
        double dx = Double.parseDouble(args[2]);
        double dy = Double.parseDouble(args[3]);
        move(name, dx, dy);
    }

    /**
     * move by a vector
     * @param name name of the shape
     * @param dx move along x
     * @param dy move along y
     */
    public void move(String name, double dx, double dy) {
        String[] args = new String[4];
        args[0] = "move"; args[1] = name; args[2] = String.valueOf(dx); args[3] = String.valueOf(dy);

        Operation op = new OpMove(args, data());
        op.call();
        data().insertOp(op);
    }

    /**
     * move by a vector;
     * @param name name of the shape
     * @param v vector
     */
    public void move(String name, Point v) {
        move(name, v.x(), v.y());
    }

    /**
     * undo an operation (if possible)
     */
    public void undo() {
        data().undo();
    }

    /**
     * redo an operation (is possible)
     */
    public void redo() {
        data().redo();
    }

    /**
     * quit anytime;
     */
    public void quit() {
        try {
            if (logger() != null) {
                logger().close();
            }
        } finally {
            System.exit(0);
        }
    }

    /* --- helper methods --- */

    /**
     * @param name name of the shape
     * @return whether the shape exists.
     */
    public boolean exists(String name) {
        return data().exists(name);
    }

    /**
     * @param name name of the shape
     * @return whether the shape have father
     */
    public boolean haveFather(String name) {
        if (!exists(name)) {
            printError(name + " not found");
            return false;
        }

        return data().name2Shape().get(name).haveFather();
    }

    /**
     * print information
     * @param content       the content
     */
    public void printInfo(String content) {
        // TODO: maybe logger or output to some terminal.
        System.out.println("Info: \n" + content);
    }

    /**
     * print Error
     * @param content       the content
     */
    public void printError(String content) {
        // TODO: implement this method.
        System.out.println("Error: " + content);
    }

    /**
     * data storage
     * @return      data
     */
    public Data data() {
        return data;
    }

    /**
     * logger
     * @return      logger
     */
    public Logger logger() {
        return logger;
    }
}
