package model;
import util.BoundingBox;

/**
 * Circle shape class.
 */
public class Circle extends Shape {
    private double x;
    private double y;
    private double radius;

    /**
     * Constructor for Circle.
     */
    public Circle(String name, double x, double y, double radius) {
        super(name);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    /**
     * Move the circle by dx and dy.
     */
    @Override
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * Check if the circle contains the given point.
     */
    @Override
    public boolean contains(double px, double py) {
        double dx = px - x;
        double dy = py - y;
        return dx * dx + dy * dy <= radius * radius;
    }

    /**
     * Get the bounding box of the circle.
     */
    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(x - radius, y - radius, x + radius, y + radius);
    }

    /**
     * Get string representation of the circle.
     */
    @Override
    public String toString() {
        return String.format("Circle '%s': center=(%.2f,%.2f), radius=%.2f",
                getName(), x, y, radius);
    }
}