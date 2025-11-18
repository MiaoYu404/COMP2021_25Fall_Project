package clevis.system;

import clevis.model.operation.Operation;
import clevis.model.shape.*;

import java.util.*;

/**
 * Data Storage
 */
public class Data {
    private Console console;

    private final List<String> shapes;
    private final HashMap<String, Shape> name2Shape;
    private final Stack<Operation> operationHistory, undoHistory;

    /**
     * Construction
     */
    public Data() {
        shapes = new ArrayList<>();
        name2Shape = new HashMap<>();
        operationHistory = new Stack<>();
        undoHistory = new Stack<>();
    }

    /**
     * construct with console stored.
     * @param console       console
     */
    public Data(Console console) {
        this();
        this.console = console;
    }

    /* --- functions about shapes --- */

    /**
     * push a new shape to the end of the list.
     * @param name      name of the shape
     * @param shape     the shape Object
     */
    public void add(String name, Shape shape) {
        insertShape(name, shape, size());
    }

    /**
     * insert to some position
     * @param name      name of the shape
     * @param shape     the shape Object
     * @param index  the position where it would love to be insert
     */
    public void insertShape(String name, Shape shape, int index) {
        // TODO: insert the Shape to particular position.
        shapes.add(index, name);
        name2Shape.put(name, shape);
    }

    /**
     * @return      the size of the list
     */
    public int size() { return shapes.size(); }

    /**
     * @return      whether the list is empty.
     */
    public boolean isEmpty() { return size() == 0; }

    /**
     * @param name      name of the Shape
     * @return          whether the Shape exists
     */
    public boolean exists(String name) { return shapes.contains(name); }

    /**
     * @param name      name of the Shape
     * @return          the Shape Object
     */
    public Shape get(String name) {
        if (name2Shape.containsKey(name)) { return name2Shape.get(name); }
        return null;
    }

    /**
     * @param index     index of the Shape
     * @return          the Shape Object
     */
    public Shape get(int index) {
        if (index >= size()) throw new IndexOutOfBoundsException();
        return name2Shape.get(shapes.get(index));
    }

    /**
     * get the index of a shape.
     * @param name      name of the shape
     * @return          index of the shape.
     */
    public int getIndex(String name) {
        if (!exists(name)) { return -1; }
        return shapes.indexOf(name);
    }

    /**
     * remove a shape
     * @param name      name of the shape.
     */
    public void remove(String name) {
        if (!exists(name)) { return ; }
        name2Shape.remove(name);
        shapes.remove(name);
    }

    /* --- functions about operations --- */

    /**
     * insert a new operation into the history
     * @param op        operation
     */
    public void insertOp(Operation op) {
        if (op == null) throw new NullPointerException();
        undoHistory.clear();            // first need to clear undo History.
        operationHistory.add(op);
    }

    /**
     * undo an operation.
     */
    public void undo() {
        if (isOperationHistoryEmpty()) {
            console.printInfo("Nothing to undo.");
            return ;
        }
        Operation op = operationHistory.pop();
        op.undo();
        undoHistory.add(op);
    }

    /**
     * redo an operation.
     */
    public void redo() {
        if (undoHistory.isEmpty()) {
            console.printInfo("Nothing to redo.");
            return;
        }
        Operation op = undoHistory.pop();
        op.redo();
        operationHistory.add(op);
    }


    /**
     *
     * @return      whether the operation stack is empty.
     */
    public boolean isOperationHistoryEmpty() {
        return operationHistory.isEmpty();
    }

    /**
     * @return      reference of shapes
     */
    public List<String> shapes() { return shapes; };

    /**
     * @return      reference of name2Shape
     */
    public HashMap<String, Shape> name2Shape() { return name2Shape; }

    /**
     * @return      reference of opHistory;
     */
    public Stack<Operation> operationHistory() { return operationHistory; }

    /**
     * @return      reference of redoStack;
     */
    public Stack<Operation> undoHistory() { return undoHistory; }
}
