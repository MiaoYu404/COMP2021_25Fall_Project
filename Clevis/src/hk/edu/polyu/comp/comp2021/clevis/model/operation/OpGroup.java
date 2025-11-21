package hk.edu.polyu.comp.comp2021.clevis.model.operation;

import hk.edu.polyu.comp.comp2021.clevis.controller.Data;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Group;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Shape;

import java.util.ArrayList;
import java.util.List;
/**
 * group some Shape
 */
public class OpGroup extends Operation{
    private final String groupName;
    private final List<String> names;

    private int index;

    /**
     * Construction
     * @param groupName     name of the Group
     * @param names         name of the member shapes
     * @param data          data storage
     */
    public OpGroup(String groupName, List<String> names, Data data) {
        this.groupName = groupName;
        this.names = names;
        setData(data);
        setConsole(data.console());
    }

    /**
     * Construct with given index
     * @param groupName     name of the Group
     * @param names         name of the member shapes
     * @param data          data storage
     * @param index         index of this Group among all shapes.
     */
    public OpGroup(String groupName, List<String> names, Data data, int index) {
        this(groupName, names, data);
        setIndex(index);
    }

    /**
     * @param index         new index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void call() {
        if (data().exists(groupName)) throw new IllegalArgumentException(groupName + " already exists");

        ArrayList<Shape> shapes = new ArrayList<>();
        for (String name : names) {
            if (!data().exists(name)) throw new IllegalArgumentException(name + " does not exist");
            if (data().get(name).father() != null) throw new IllegalArgumentException(name + " already have father");
            shapes.add(data().get(name));
        }
        Group group = new Group(groupName, shapes);
        data().add(groupName, group);

        for (String name : names)
            setFather(data().get(name), group);
    }

    @Override
    public void undo() {
        // TODO: test this method
        Operation opUngroup = new OpUngroup(groupName, data());
        opUngroup.call();
    }

    @Override
    public void redo() {
        // TODO: test this method
        call();
    }

    // helper functions

    /**
     * set father to shape
     * @param shape         the shape
     * @param father        the father of the shape
     */
    public void setFather(Shape shape, Shape father) {
        shape.setFather(father);
    }
}
