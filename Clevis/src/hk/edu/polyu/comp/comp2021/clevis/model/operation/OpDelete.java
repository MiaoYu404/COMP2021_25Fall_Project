package hk.edu.polyu.comp.comp2021.clevis.model.operation;

import hk.edu.polyu.comp.comp2021.clevis.controller.Data;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Group;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * Delete Operation
 */
public class OpDelete extends Operation{
    private final String name;
    private Operation undoOperation;

    /**
     * Construction
     * @param name      name of the Shape
     * @param data      data Storage
     */
    public OpDelete(String name, Data data) {
        this.name = name;
        setData(data);
        setConsole(data.console());
    }

    @Override
    public void call() {
        if (data() == null) throw new NullPointerException();
        if (!data().exists(name)) throw new IllegalArgumentException();

        Shape shape = data().get(name);
        int index = data().getIndex(name);


        if (shape.haveFather())
            throw new IllegalArgumentException(name + " is inside an existing group.");
        if (shape instanceof Group g) {
            List<String> members = new ArrayList<>();
            for (Shape s : g.shapes()) members.add(s.name());

            Operation op = new OpUngroup(name, data());
            op.call();

            for (String s : members) {
                op = new OpDelete(s, data());
                op.call();
                push(op);
            }

            undoOperation = new OpGroup(name, members, data());
        } else {
            undoOperation = new OpAdd(name, shape, index, data());
        }

        data().remove(name);
    }

    @Override
    public void undo() {
        while (!isUndoStackEmpty()) {
            Undoable op = pop();
            op.undo();
        }
        undoOperation.call();
    }

    @Override
    public void redo() {
        if (!isUndoStackEmpty()) return;        // the Operation has been called.
        this.call();
    }
}
