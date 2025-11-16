package clevis.util;

import clevis.Application;
import clevis.system.Console;

import java.util.List;

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
        Console console = new Console();

        String op;
        op = "circle A 1 7 3";
        console.add(op.split(" "));
        console.list("A");

        op = "line B 2 3 5 6";
        console.add(op.split(" "));
        assertThat(console.intersects("A", "B")).isTrue();

        op = "rectangle C 3 10 2 3";
        console.add(op.split(" "));
        assertThat(console.intersects("B", "C")).isFalse();
        console.group("G1", List.of("A", "C"));
        assertThat(console.intersects("B", "G1")).isTrue();
        try {
            console.move("C", 1.5, -0.5);
        } catch (Exception e) {
            // success
            System.out.println("illegal move catched.");
        }
        console.ungroup("G1");
        assertThat(console.intersects("B", "C")).isFalse();

        op = "line D -2 9 5 9";
        console.add(op.split(" "));
        assertThat(console.shapeAt(3.2, 9.0).name()).isEqualTo("D");
        assertThat(console.shapeAt(3.2, 8.0).name()).isEqualTo("C");
        assertThat(console.shapeAt(2, 8).name()).isEqualTo("A");

        console.move("B", -0.8, 0);
        assertThat(console.shapeAt(3, 4.8).name()).isEqualTo("B");
        console.group("G2", List.of("A", "C"));
        assertThat(console.shapeAt(3, 4.8).name()).isEqualTo("G2");
        console.getBoundingBox("G2");
        console.move("G2", -1, 0);
        console.getBoundingBox("G2");
    }

    /**
     *  test undo & redo
     */
    @Test
    public void testUndo() {
        Application app = new Application();
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
}
