package hk.edu.polyu.comp.comp2021.clevis.model;

import hk.edu.polyu.comp.comp2021.clevis.controller.Console;
import hk.edu.polyu.comp.comp2021.clevis.controller.Data;
import hk.edu.polyu.comp.comp2021.clevis.model.operation.OpAdd;
import hk.edu.polyu.comp.comp2021.clevis.model.operation.OpDelete;
import hk.edu.polyu.comp.comp2021.clevis.model.operation.OpGroup;
import hk.edu.polyu.comp.comp2021.clevis.model.operation.OpMove;
import hk.edu.polyu.comp.comp2021.clevis.model.operation.OpUngroup;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Circle;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Point;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;



import static com.google.common.truth.Truth.assertThat;

public class TestOperationsFlow {

    @Test
    public void testDataAddRemoveAndIndex() {
        Data d = new Data();
        Circle c = new Circle("C", new Point(0,0), 1);
        d.add("C", c);
        assertThat(d.size()).isEqualTo(1);
        assertThat(d.exists("C")).isTrue();
        assertThat(d.getIndex("C")).isEqualTo(0);
        d.remove("C");
        assertThat(d.exists("C")).isFalse();
    }

    @Test
    public void testGroupAndUngroupOperations() {
        Console console = new Console();
        Data d = console.data();

        // add shapes
        OpAdd op1 = new OpAdd(new String[] {"circle","A","0","0","1"}, d);
        op1.call();
        OpAdd op2 = new OpAdd(new String[] {"rectangle","B","0","1","2","2"}, d);
        op2.call();

        List<String> names = new ArrayList<>(); names.add("A"); names.add("B");
        OpGroup group = new OpGroup("G", names, d);
        group.call();
        assertThat(d.exists("G")).isTrue();
        // ungroup
        OpUngroup ug = new OpUngroup("G", d);
        ug.call();
        assertThat(d.exists("G")).isFalse();
    }

    @Test
    public void testDeleteAndMoveOperations() {
        Console console = new Console();
        Data d = console.data();
        OpAdd op1 = new OpAdd(new String[] {"circle","X","0","0","1"}, d);
        op1.call();
        assertThat(d.exists("X")).isTrue();
        OpMove mv = new OpMove(new String[] {"move","X","2","3"}, d);
        mv.call();
        // move back
        mv.undo();

        OpDelete del = new OpDelete("X", d);
        del.call();
        assertThat(d.exists("X")).isFalse();
    }
}
