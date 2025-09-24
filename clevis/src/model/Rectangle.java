package model;
import util.BoundingBox;

/**
 * Rectangle shape class.
 */
public class Rectangle extends Shape {
    private double x;
    private double y;
    private double width;
    private double height;

    /**
     * Constructor for Rectangle.
     */
    public Rectangle(String name, double x, double y, double width, double height) {
        super(name);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Move the rectangle by dx and dy.
     */
    @Override
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * Check if the rectangle contains the given point.
     */
    @Override
    public boolean contains(double px, double py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }

    /**
     * Get the bounding box of the rectangle.
     */
    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(x, y, x + width, y + height);
    }

    /**
     * Get string representation of the rectangle.
     */
    @Override
    public String toString() {
        return String.format("Rectangle '%s': top-left=(%.2f,%.2f), width=%.2f, height=%.2f",
                getName(), x, y, width, height);
    }
}