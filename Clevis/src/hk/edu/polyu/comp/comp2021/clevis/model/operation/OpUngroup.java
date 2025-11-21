package hk.edu.polyu.comp.comp2021.clevis.model.operation;

import hk.edu.polyu.comp.comp2021.clevis.controller.Data;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Group;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * ungroup Operation
 */
public class OpUngroup extends Operation{
    private String name;
    private Shape shape;
    private List<String> members;

    /**
     * Construction with data storage
     * @param data      data storage
     */
    public OpUngroup(Data data) {
        this.members = new ArrayList<>();
        setData(data);
        setConsole(console());
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
        if (!data().exists(name)) throw new IllegalArgumentException(name + " does not exist.");

        if (shape instanceof Group group) {
            for (Shape s : group.shapes()) {
                removeFather(s);
                if (members.isEmpty()) members.add(s.name());
            }

            group.shapes().clear();
            data().remove(name);
        } else throw new IllegalArgumentException(name + " is not a Group.");
    }

    @Override
    public void undo() {
        if (members.isEmpty()) return;
        Operation opGroup = new OpGroup(name, members, data());
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

        Shape newFather = shape.father();
        if (newFather == null)
            throw new IllegalArgumentException("The Shape does not have father.");

        shape.setFather(newFather.father());
    }
}
