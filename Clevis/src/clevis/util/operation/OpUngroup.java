package clevis.util.operation;

import clevis.system.Data;
import clevis.util.shape.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ungroup Operation
 */
public class OpUngroup extends Operation{
    private final Data data;

    private String name;
    private Shape shape;
    private List<String> members;

    /**
     * Construction with data storage
     * @param data      data storage
     */
    public OpUngroup(Data data) {
        this.data = data;
        this.members = new ArrayList<>();
    }

    /**
     * Construction
     * @param groupName     name of the Group
     * @param data          data storage
     */
    public OpUngroup(String groupName, Data data) {
        this(data);
        this.name = groupName;
        this.shape = data.get(groupName);
    }

    @Override
    public void call() {
        if (!data.exists(name)) throw new IllegalArgumentException(name + " does not exist.");

        if (shape instanceof Group group) {
            for (Shape s : group.shapes()) {
                removeFather(s);
                members.add(s.name());
            }

            group.shapes().clear();
            Operation opDelete = new OpDelete(this.name, data);
            opDelete.call();

        } else throw new IllegalArgumentException(name + " is not a Group.");
    }

    @Override
    public void undo() {
        if (members.isEmpty()) return;
        Operation opGroup = new OpGroup(name, members, data);
        opGroup.call();
    }

    @Override
    public void redo() {
        this.call();
    }

    /*
        helper functions
     */

    /**
     * remove `shape`'s father
     * @param shape shape
     */
    public void removeFather(Shape shape) {
        if (shape == null)
            throw new IllegalArgumentException("Null shape is not allowed.");

        Shape newFather = shape.getFather();
        if (newFather == null)
            throw new IllegalArgumentException("The Shape does not have father.");

        shape.setFather(newFather.getFather());
    }
}
