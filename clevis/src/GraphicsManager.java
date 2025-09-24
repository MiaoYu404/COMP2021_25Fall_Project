

import model.*;
import util.BoundingBox;
import java.util.*;

/**
 * Manages all graphics operations and shape storage.
 */
public class GraphicsManager {
    private Map<String, Shape> shapes = new HashMap<>();
    private List<String> shapeOrder = new ArrayList<>(); // To maintain rendering order

    /**
     * Create a rectangle shape.
     */
    public void createRectangle(String name, double x, double y, double width, double height) {
        validateName(name);
        Rectangle rectangle = new Rectangle(name, x, y, width, height);
        addShape(rectangle);
    }

    /**
     * Create a line shape.
     */
    public void createLine(String name, double x1, double y1, double x2, double y2) {
        validateName(name);
        Line line = new Line(name, x1, y1, x2, y2);
        addShape(line);
    }

    /**
     * Create a circle shape.
     */
    public void createCircle(String name, double x, double y, double radius) {
        validateName(name);
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
        Circle circle = new Circle(name, x, y, radius);
        addShape(circle);
    }

    /**
     * Create a square shape.
     */
    public void createSquare(String name, double x, double y, double length) {
        validateName(name);
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }
        Square square = new Square(name, x, y, length);
        addShape(square);
    }

    /**
     * Create a group of shapes.
     */
    public void createGroup(String groupName, List<String> memberNames) {
        validateName(groupName);
        List<Shape> members = new ArrayList<>();

        for (String memberName : memberNames) {
            Shape shape = shapes.get(memberName);
            if (shape == null) {
                throw new IllegalArgumentException("Shape not found: " + memberName);
            }
            if (shape.isInGroup()) {
                throw new IllegalArgumentException("Shape is already in a group: " + memberName);
            }
            members.add(shape);
            shape.setInGroup(true);
        }

        Group group = new Group(groupName, members);
        addShape(group);
    }

    /**
     * Ungroup a group shape.
     */
    public void ungroup(String name) {
        Shape shape = getShape(name);
        if (!(shape instanceof Group)) {
            throw new IllegalArgumentException("Shape is not a group: " + name);
        }

        Group group = (Group) shape;
        List<Shape> members = group.getMembers();

        // Remove the group
        shapes.remove(name);
        shapeOrder.remove(name);

        // Ungroup members
        for (Shape member : members) {
            member.setInGroup(false);
        }
    }

    /**
     * Delete a shape.
     */
    public void deleteShape(String name) {
        Shape shape = getShape(name);

        // If deleting a group, also ungroup its members
        if (shape instanceof Group) {
            Group group = (Group) shape;
            for (Shape member : group.getMembers()) {
                member.setInGroup(false);
            }
        }

        shapes.remove(name);
        shapeOrder.remove(name);
    }

    /**
     * Get bounding box of a shape.
     */
    public BoundingBox getBoundingBox(String name) {
        Shape shape = getShape(name);
        return shape.getBoundingBox();
    }

    /**
     * Move a shape.
     */
    public void moveShape(String name, double dx, double dy) {
        Shape shape = getShape(name);
        shape.move(dx, dy);
    }

    /**
     * Find the top shape at given coordinates.
     */
    public String getTopShapeAt(double x, double y) {
        // Iterate in reverse order (top to bottom)
        for (int i = shapeOrder.size() - 1; i >= 0; i--) {
            String shapeName = shapeOrder.get(i);
            Shape shape = shapes.get(shapeName);
            if (shape.contains(x, y)) {
                return shapeName;
            }
        }
        return null;
    }

    /**
     * Check if two shapes intersect.
     */
    public boolean doShapesIntersect(String name1, String name2) {
        Shape shape1 = getShape(name1);
        Shape shape2 = getShape(name2);
        return shape1.getBoundingBox().intersects(shape2.getBoundingBox());
    }

    /**
     * Get information about a shape.
     */
    public String getShapeInfo(String name) {
        Shape shape = getShape(name);
        return shape.toString();
    }

    /**
     * Get information about all shapes.
     */
    public List<String> getAllShapesInfo() {
        List<String> infoList = new ArrayList<>();
        for (String name : shapeOrder) {
            infoList.add(shapes.get(name).toString());
        }
        return infoList;
    }

    /**
     * Helper method to add a shape.
     */
    private void addShape(Shape shape) {
        shapes.put(shape.getName(), shape);
        shapeOrder.add(shape.getName());
    }

    /**
     * Helper method to get a shape by name.
     */
    private Shape getShape(String name) {
        Shape shape = shapes.get(name);
        if (shape == null) {
            throw new IllegalArgumentException("Shape not found: " + name);
        }
        return shape;
    }

    /**
     * Validate shape name.
     */
    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Shape name cannot be empty");
        }
        if (shapes.containsKey(name)) {
            throw new IllegalArgumentException("Shape with name '" + name + "' already exists");
        }
    }
}