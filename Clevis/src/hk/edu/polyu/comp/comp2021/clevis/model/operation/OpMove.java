package hk.edu.polyu.comp.comp2021.clevis.model.operation;

import hk.edu.polyu.comp.comp2021.clevis.controller.Data;

/**
 * move Operation
 */
public class OpMove extends Operation{
    private String[] args;

    private String name;
    private double dx, dy;

    /**
     * Construct with data storage
     * @param data      data storage
     */
    public OpMove(Data data) {
        setData(data);
        setConsole(data.console());
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
        if (!data().exists(name)) throw new IllegalArgumentException(name + " does not exist.");
        if (data().get(name).haveFather()) throw new IllegalArgumentException(name + " is inside an existing group.");

        data().get(name).move(dx, dy);
    }
    @Override
    public void undo() {
        if (!data().exists(name)) throw new IllegalArgumentException(name + " does not exist.");
        if (data().get(name).haveFather()) throw new IllegalArgumentException(name + " is inside an existing group.");

        data().get(name).move(-dx, -dy);
    }

    @Override
    public void redo() {
        call();
    }
}
