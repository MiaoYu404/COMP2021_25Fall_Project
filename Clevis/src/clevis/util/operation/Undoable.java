package clevis.util.operation;

import java.util.Stack;

/**
 * interface of undo can be done.
 */
public interface Undoable {
    /**
     * stack of undoable
     */
    public final Stack<Undoable> undoStack = new Stack<>();

    /**
     * undo this Operation
     */
    public void undo();

    /**
     * redo this Operation
     */
    public void redo();

    /**
     * @return      whether the stack is empty.
     */
    default boolean isUndoStackEmpty() {
        return undoStack.isEmpty();
    }

    /**
     * @param op        push the operation into the stack.
     */
    default void push(Undoable op) { undoStack.push(op); }

    /**
     * pop
     * @return      the top element in the stack
     */
    default Undoable pop() {
        if (!isUndoStackEmpty()) return undoStack.pop();
        return null;
    }
}
