package hk.edu.polyu.comp.comp2021.clevis.model.operation;

import hk.edu.polyu.comp.comp2021.clevis.controller.Data;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.*;

/**
 * add a Shape
 */
public class OpAdd extends Operation{
    private String[] args;

    private String type;
    private String name;
    private Shape shape;

    private int index;

    /**
     * Construction with data storage.
     * @param data      data storage
     */
    public OpAdd(Data data) {
        super();
        super.setData(data);
        super.setConsole(data.console());
    }

    /**
     * Construct with arguments.
     * @param args      arguements
     * @param data      data storage
     */
    public OpAdd(String[] args, Data data) {
        this(data);
        try {
            this.args = args;
            this.type = args[0];
            this.name = args[1];
            this.index = -1;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Construct with exist Shape.
     * @param name      name of the shape
     * @param shape     Shape Object
     * @param index     order
     * @param data      data storage
     */
    public OpAdd(String name, Shape shape, int index, Data data) {
        this(data);
        setIndex(index);
        this.name = name;
        this.shape = shape;
    }

    /**
     * @param index     set order
     */
    public void setIndex(int index) {
        if (index < 0) throw new IllegalArgumentException("Negative order");
        this.index = index;
    }

    /**
     * @return          order of the shape
     */
    public int index() {
        return this.index;
    }

    @Override
    public void call() {
        if (data().exists(name))
            throw new IllegalStateException("The Shape with name <" + name + "> already exists");
        try {
            if (shape == null) shape = switch (type) {              // in case of shape is not generated.
                case "rectangle" -> addRectangle(args);
                case "line" -> addLine(args);
                case "circle" -> addCircle(args);
                case "square" -> addSquare(args);
                default -> throw new IllegalArgumentException("Not implemented Shape type.");
            };
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException();
        }

        if (index == -1) {
            data().add(name, shape);
            setIndex(data().getIndex(name));
        }
        else
            data().insertShape(name, shape, index);
    }

    /**
     * @param args      arguments
     * @return          a Rectangle
     */
    public Shape addRectangle(String[] args) {
        double x, y, w, h;
        try {
            x = Double.parseDouble(args[2]); y = Double.parseDouble(args[3]);
            w = Double.parseDouble(args[4]); h = Double.parseDouble(args[5]);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        Point top_left = new Point(x, y);
        return new Rectangle(name, top_left, w, h);
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

    @Override
    public void undo() {
        // TODO: test this method
        Operation opDelete = new OpDelete(name, data());
        opDelete.call();
    }

    @Override
    public void redo() {
        // TODO: test this method
        call();
    }

    /**
     * @return      shape
     */
    public Shape shape() {
        return shape;
    }
}
