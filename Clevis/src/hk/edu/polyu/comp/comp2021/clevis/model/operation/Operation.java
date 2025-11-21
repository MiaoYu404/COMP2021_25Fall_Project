package hk.edu.polyu.comp.comp2021.clevis.model.operation;

import hk.edu.polyu.comp.comp2021.clevis.controller.Console;
import hk.edu.polyu.comp.comp2021.clevis.controller.Data;

/**
 * basic Operation class
 */
public class Operation implements Undoable{
    private Data data;
    private Console console;

    /**
     * basic Construction
     */
    public Operation() {}

    /**
     * when the method is called
     */
    public void call() {}

    /**
     * print out infomation on the console.
     */
    public void print() {}

    @Override
    public void undo() {}

    @Override
    public void redo() {}

    /**
     * data storage
     * @return          the data storage
     */
    public Data data() {
        return data;
    }

    /**
     * @param data      new data storage
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * console
     * @return          the console
     */
    public Console console() {
        return console;
    }

    /**
     * @param console   the console
     */
    public void setConsole(Console console) {
        this.console = console;
    }
}
