package clevis.util.shape;

import java.awt.*;

/**
 * class of Shape
 */
public interface Shape {
    /**
     * @return name of the shape
     */
    public String name();

    /**
     * @return father pointer
     */
    Shape father();

    /**
     * @param _shape new father
     */
    void setFather(Shape _shape);

    /**
     * @return whether this shape have father.
     */
    boolean haveFather();

    /**
     * this method is not supported by Shape
     * @param dx change in x
     * @param dy change in y
     */
    void move(double dx, double dy);

    /**
     * this method is not supported by Shape
     * @return the bounding box of the shape.
     */
    Shape boundingBox();

    /**
     * draw the graph
     * @param graph     graph
     */
    void draw(Graphics2D graph);

    /**
     * print infomation about this shape.
     */
    default void printInfo() {
        System.out.println(this);
    }

    /**
     * @return      short name of the Shape
     */
    String shortName();
}