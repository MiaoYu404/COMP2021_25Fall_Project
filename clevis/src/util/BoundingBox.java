package util;
/**
 * Represents a bounding box with min and max coordinates.
 */
public class BoundingBox {
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    /**
     * Constructor for BoundingBox.
     */
    public BoundingBox(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     * Get the minimum x-coordinate.
     */
    public double getMinX() {
        return minX;
    }

    /**
     * Get the minimum y-coordinate.
     */
    public double getMinY() {
        return minY;
    }

    /**
     * Get the maximum x-coordinate.
     */
    public double getMaxX() {
        return maxX;
    }

    /**
     * Get the maximum y-coordinate.
     */
    public double getMaxY() {
        return maxY;
    }

    /**
     * Check if this bounding box intersects with another bounding box.
     */
    public boolean intersects(BoundingBox other) {
        return !(this.maxX < other.minX ||
                this.minX > other.maxX ||
                this.maxY < other.minY ||
                this.minY > other.maxY);
    }

    /**
     * Get string representation of the bounding box.
     */
    @Override
    public String toString() {
        return String.format("(%.2f,%.2f) to (%.2f,%.2f)", minX, minY, maxX, maxY);
    }
}