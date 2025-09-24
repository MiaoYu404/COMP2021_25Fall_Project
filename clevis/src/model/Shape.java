package model;
import util.BoundingBox;

/**
 * Abstract base class for all shapes.
 */
public abstract class Shape {
    private String name;
    private boolean inGroup = false;

    /**
     * Constructor for Shape.
     */
    public Shape(String name) {
        this.name = name;
    }

    /**
     * Get the name of the shape.
     */
    public String getName() {
        return name;
    }

    /**
     * Check if the shape is in a group.
     */
    public boolean isInGroup() {
        return inGroup;
    }

    /**
     * Set whether the shape is in a group.
     */
    public void setInGroup(boolean inGroup) {
        this.inGroup = inGroup;
    }

    /**
     * Move the shape by dx and dy.
     */
    public abstract void move(double dx, double dy);

    /**
     * Check if the shape contains the given point.
     */
    public abstract boolean contains(double x, double y);

    /**
     * Get the bounding box of the shape.
     */
    public abstract BoundingBox getBoundingBox();

    /**
     * Get string representation of the shape.
     */
    @Override
    public abstract String toString();
}