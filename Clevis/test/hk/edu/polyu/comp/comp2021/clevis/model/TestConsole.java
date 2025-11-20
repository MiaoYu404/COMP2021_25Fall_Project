package hk.edu.polyu.comp.comp2021.clevis.model;

import hk.edu.polyu.comp.comp2021.clevis.Application;
import hk.edu.polyu.comp.comp2021.clevis.controller.Console;

import hk.edu.polyu.comp.comp2021.clevis.controller.Data;
import hk.edu.polyu.comp.comp2021.clevis.model.operation.OpAdd;
import hk.edu.polyu.comp.comp2021.clevis.model.operation.Operation;
import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

/**
 * test the console
 */
public class TestConsole {
    /**
     * basic test
     */
    @Test
    public void test() {
        Application app = new Application();
        app.Boost(false, null);
        Console console = app.console();

        String op;
        op = "circle A 1 7 3";
        console.readOperation(op);

        op = "list A";
        console.readOperation(op);

        op = "line B 2 3 5 6";
        console.readOperation(op);
        assertThat(console.intersects("A", "B")).isTrue();

        op = "rectangle C 3 10 2 3";
        console.readOperation(op);
        assertThat(console.intersects("B", "C")).isFalse();

        op = "group G1 A C";
        console.readOperation(op);
        assertThat(console.intersects("B", "G1")).isTrue();
        try {
            op = "move C 1.5 -0.5";
            console.readOperation(op);
        } catch (Exception e) {
            // success
            System.out.println("illegal move catched.");
        }
        op = "ungroup G1";
        console.readOperation(op);
        assertThat(console.intersects("B", "C")).isFalse();

        op = "line D -2 9 5 9";
        console.readOperation(op);
        assertThat(console.shapeAt(3.2, 9.0).name()).isEqualTo("D");
        assertThat(console.shapeAt(3.2, 8.0).name()).isEqualTo("C");
        assertThat(console.shapeAt(2, 8).name()).isEqualTo("A");

        op = "move B -0.8 0";
        console.readOperation(op);
        assertThat(console.shapeAt(3, 4.8).name()).isEqualTo("B");

        op = "group G2 A C";
        console.readOperation(op);
        assertThat(console.shapeAt(3, 4.8).name()).isEqualTo("G2");

        op = "boundingbox G2";
        console.readOperation(op);

        op = "move G2 -1 0";
        console.readOperation(op);

        op = "boundingbox G2";
        console.readOperation(op);

    }

    /**
     *  test undo & redo
     */
    @Test
    public void testUndo() {
        Application app = new Application();
        app.Boost(false, null);
        Console console = app.console();
        String op;

        op = "listAll";
        console.readOperation(op);

        op = "rectangle A -1 1 2 2";
        console.readOperation(op);
        op = "circle C 0 0 1";
        console.readOperation(op);

        op = "listAll";
        console.readOperation(op);

        op = "undo";
        console.readOperation(op);

        try {
            op = "list C";
            console.readOperation(op);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        };

        op = "redo";
        console.readOperation(op);
        op = "list C";
        console.readOperation(op);
    }


    /**
     * test for Operation
     */
    @Test
    public void testOperation() {
        Operation o1 = new Operation();
        o1.call();
        o1.print();
        o1.redo();o1.undo();
    }

    /**
     * test for opAdd
     */
    @Test
    public void testOpAdd() {
        Data dat = new Data();
        String[] arg = "square Joey 0 0 5".split(" ");
        OpAdd oa = new OpAdd(arg, dat);
        oa.call();
        assertThat(oa.index()).isEqualTo(0);
        System.out.println(oa.shape());
    }
}
