package model;

import util.BoundingBox;

/**
 * Square shape class.
 */
public class Square extends Shape {
    private double centerX;
    private double centerY;
    private double length;

    /**
     * Constructor for Square.
     */
    public Square(String name, double centerX, double centerY, double length) {
        super(name);
        this.centerX = centerX;
        this.centerY = centerY;
        this.length = length;
    }

    /**
     * Move the square by dx and dy.
     */
    @Override
    public void move(double dx, double dy) {
        this.centerX += dx;
        this.centerY += dy;
    }

    /**
     * Check if the square contains the given point.
     */
    @Override
    public boolean contains(double px, double py) {
        double halfLength = length / 2;
        return px >= centerX - halfLength && px <= centerX + halfLength &&
                py >= centerY - halfLength && py <= centerY + halfLength;
    }

    /**
     * Get the bounding box of the square.
     */
    @Override
    public BoundingBox getBoundingBox() {
        double halfLength = length / 2;
        return new BoundingBox(centerX - halfLength, centerY - halfLength,
                centerX + halfLength, centerY + halfLength);
    }

    /**
     * Get string representation of the square.
     */
    @Override
    public String toString() {
        return String.format("Square '%s': center=(%.2f,%.2f), length=%.2f",
                getName(), centerX, centerY, length);
    }
}