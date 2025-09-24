package model;
import util.BoundingBox;
import java.util.*;

/**
 * Group of shapes class.
 */
public class Group extends Shape {
    private List<Shape> members;

    /**
     * Constructor for Group.
     */
    public Group(String name, List<Shape> members) {
        super(name);
        this.members = new ArrayList<>(members);
    }

    /**
     * Get the members of the group.
     */
    public List<Shape> getMembers() {
        return new ArrayList<>(members);
    }

    /**
     * Move the group by dx and dy.
     */
    @Override
    public void move(double dx, double dy) {
        for (Shape member : members) {
            member.move(dx, dy);
        }
    }

    /**
     * Check if the group contains the given point.
     * A group contains a point if any of its members contains the point.
     */
    @Override
    public boolean contains(double x, double y) {
        for (Shape member : members) {
            if (member.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the bounding box of the group.
     * The bounding box is the union of all member bounding boxes.
     */
    @Override
    public BoundingBox getBoundingBox() {
        if (members.isEmpty()) {
            return new BoundingBox(0, 0, 0, 0);
        }

        BoundingBox firstBox = members.get(0).getBoundingBox();
        double minX = firstBox.getMinX();
        double minY = firstBox.getMinY();
        double maxX = firstBox.getMaxX();
        double maxY = firstBox.getMaxY();

        for (int i = 1; i < members.size(); i++) {
            BoundingBox box = members.get(i).getBoundingBox();
            minX = Math.min(minX, box.getMinX());
            minY = Math.min(minY, box.getMinY());
            maxX = Math.max(maxX, box.getMaxX());
            maxY = Math.max(maxY, box.getMaxY());
        }

        return new BoundingBox(minX, minY, maxX, maxY);
    }

    /**
     * Get string representation of the group.
     */
    @Override
    public String toString() {
        StringBuilder memberNames = new StringBuilder();
        for (int i = 0; i < members.size(); i++) {
            memberNames.append(members.get(i).getName());
            if (i < members.size() - 1) {
                memberNames.append(", ");
            }
        }
        return String.format("Group '%s': members=[%s]", getName(), memberNames.toString());
    }
}