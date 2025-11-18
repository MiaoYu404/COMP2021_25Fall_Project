package clevis.model.operation;

import clevis.system.Data;

/**
 * move Operation
 */
public class OpMove extends Operation{
    private final Data data;
    private String[] args;

    private String name;
    private double dx, dy;

    /**
     * Construct with data storage
     * @param data      data storage
     */
    public OpMove(Data data) {
        this.data = data;
    }

    /**
     * Construct with arguments
     * @param args      arguments
     * @param data      data storage
     */
    public OpMove(String[] args, Data data) {
        this(data);
        this.args = args;

        this.name = args[1]; this.dx = Double.parseDouble(args[2]); this.dy = Double.parseDouble(args[3]);
    }

    @Override
    public void call() {
        if (!data.exists(name)) throw new IllegalArgumentException(name + " does not exist.");
        if (data.get(name).haveFather()) throw new IllegalArgumentException(name + " is inside an existing group.");

        data.get(name).move(dx, dy);
    }
    @Override
    public void undo() {
        if (!data.exists(name)) throw new IllegalArgumentException(name + " does not exist.");
        if (data.get(name).haveFather()) throw new IllegalArgumentException(name + " is inside an existing group.");

        data.get(name).move(-dx, -dy);
    }

    @Override
    public void redo() {
        call();
    }
}
