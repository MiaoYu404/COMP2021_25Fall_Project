package hk.edu.polyu.comp.comp2021.clevis.model.operation;

/**
 * basic Operation class
 */
public class Operation implements Undoable{
    /**
     * basic Construction
     * @param args      arguments
     */
    public Operation() {}

    /**
     * when the method is called
     *
     * @param data data storage
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
}
