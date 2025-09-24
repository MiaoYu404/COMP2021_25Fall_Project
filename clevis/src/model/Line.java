package model;
import util.BoundingBox;

/**
 * Line shape class.
 */
public class Line extends Shape {
    private double x1;
    private double y1;
    private double x2;
    private double y2;

    /**
     * Constructor for Line.
     */
    public Line(String name, double x1, double y1, double x2, double y2) {
        super(name);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Move the line by dx and dy.
     */
    @Override
    public void move(double dx, double dy) {
        this.x1 += dx;
        this.y1 += dy;
        this.x2 += dx;
        this.y2 += dy;
    }

    /**
     * Check if the line contains the given point.
     * Uses a simple distance check with a small epsilon for tolerance.
     */
    @Override
    public boolean contains(double px, double py) {
        // Calculate distance from point to line
        double A = px - x1;
        double B = py - y1;
        double C = x2 - x1;
        double D = y2 - y1;

        double dot = A * C + B * D;
        double lenSq = C * C + D * D;
        double param = -1;

        if (lenSq != 0) param = dot / lenSq;

        double xx, yy;

        if (param < 0) {
            xx = x1;
            yy = y1;
        } else if (param > 1) {
            xx = x2;
            yy = y2;
        } else {
            xx = x1 + param * C;
            yy = y1 + param * D;
        }

        double dx = px - xx;
        double dy = py - yy;
        return Math.sqrt(dx * dx + dy * dy) < 0.01; // Small epsilon
    }

    /**
     * Get the bounding box of the line.
     */
    @Override
    public BoundingBox getBoundingBox() {
        double minX = Math.min(x1, x2);
        double minY = Math.min(y1, y2);
        double maxX = Math.max(x1, x2);
        double maxY = Math.max(y1, y2);
        return new BoundingBox(minX, minY, maxX, maxY);
    }

    /**
     * Get string representation of the line.
     */
    @Override
    public String toString() {
        return String.format("Line '%s': from (%.2f,%.2f) to (%.2f,%.2f)",
                getName(), x1, y1, x2, y2);
    }
}