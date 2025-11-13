package clevis.util;

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
    Shape getFather();

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
     * print infomation about this shape.
     */
    default void printInfo() {
        System.out.println(this);
    }
}