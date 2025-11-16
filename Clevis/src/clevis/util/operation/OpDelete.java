package clevis.util.operation;

import clevis.system.Data;
import clevis.util.shape.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Delete Operation
 */
public class OpDelete extends Operation{
    private final String name;
    private final Data data;
    private Operation undoOperation;

    /**
     * Construction
     * @param name      name of the Shape
     * @param data      data Storage
     */
    public OpDelete(String name, Data data) {
        this.name = name;
        this.data = data;
    }

    @Override
    public void call() {
        if (data == null) throw new NullPointerException();
        if (!data.exists(name)) throw new IllegalArgumentException();

        Shape shape = data.get(name);
        int index = data.getIndex(name);

        if (shape.haveFather())
            throw new IllegalArgumentException(name + " is inside an existing group.");
        if (shape instanceof Group g) {
            List<String> members = new ArrayList<>();
            for (Shape s : g.shapes()) {
                push(new OpDelete(s.name(), data));
                members.add(s.name());
            }
            undoOperation = new OpGroup(name, members, data);
        } else {
            undoOperation = new OpAdd(shape, index, data);
        }

        data.remove(name);
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
