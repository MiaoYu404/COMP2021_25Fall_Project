package hk.edu.polyu.comp.comp2021.clevis.model;

import hk.edu.polyu.comp.comp2021.clevis.controller.Data;
import hk.edu.polyu.comp.comp2021.clevis.model.operation.OpAdd;
import hk.edu.polyu.comp.comp2021.clevis.model.operation.OpDelete;
import hk.edu.polyu.comp.comp2021.clevis.model.operation.OpGroup;
import hk.edu.polyu.comp.comp2021.clevis.model.operation.OpUngroup;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Circle;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Group;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Line;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Point;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Rectangle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;



import static com.google.common.truth.Truth.assertThat;

public class TestGroupLineOpExtra {

    @Test
    public void testLineBasicsAndBoundingBox() {
        Line l = new Line("L", new Point(0,0), new Point(3,4));
        assertThat(l.from()).isEqualTo(new Point(0,0));
        assertThat(l.to()).isEqualTo(new Point(3,4));
        assertThat(l.direction()).isEqualTo(new Point(3,4));

        Line other = new Line("M", new Point(0,1), new Point(1,2));
        double det = l.det(other);
        // just ensure det returns a double
        assertThat(Double.isFinite(det)).isTrue();

        Line rev = l.reverse();
        assertThat(rev.from()).isEqualTo(new Point(3,4));

        // inside with rectangle that contains both points
        Rectangle r = new Rectangle(new Point(0,4), 4, 5); // top-left (0,4), width 4, height 5 -> covers (0,0)-(3,4)
        assertThat(l.inside(r)).isTrue();

        // bounding box
        Rectangle bb = l.boundingBox();
        assertThat(bb.name()).contains("L");

        // draw
        BufferedImage img = new BufferedImage(20,20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) img.getGraphics();
        l.draw(g2);
        g2.dispose();
    }

    @Test
    public void testGroupBasicsAndBoundingBoxAndMove() {
        Rectangle r1 = new Rectangle("R1", new Point(0,5), 2, 2);
        Circle c1 = new Circle("C1", new Point(1,3), 1);
        ArrayList<hk.edu.polyu.comp.comp2021.clevis.model.shape.Shape> members = new ArrayList<>();
        members.add(r1); members.add(c1);
        Group g = new Group("G1", members);
        assertThat(g.name()).isEqualTo("G1");
        assertThat(g.shapes().size()).isEqualTo(2);
        assertThat(g.shortName()).contains("<Group>");

        // bounding box of group
        Rectangle gbb = (Rectangle) g.boundingBox();
        assertThat(gbb.name()).contains("G1");

        // move group should move members
        g.move(1,1);
        // r1 top-left moved by (1,1)
        assertThat(r1.points()[0]).isEqualTo(new Point(1,6));
    }

    @Test
    public void testOpGroupErrorsAndSuccess() {
        Data d = new Data();
        // add two shapes via OpAdd
        OpAdd a1 = new OpAdd(new String[]{"circle","A","0","0","1"}, d);
        a1.call();
        OpAdd a2 = new OpAdd(new String[]{"rectangle","B","0","5","2","2"}, d);
        a2.call();

        // missing member leads to exception
        List<String> names = new ArrayList<>(); names.add("A"); names.add("NOPE");
        try {
            OpGroup bad = new OpGroup("Gbad", names, d);
            bad.call();
            throw new AssertionError("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // successful group
        List<String> ok = new ArrayList<>(); ok.add("A"); ok.add("B");
        OpGroup gop = new OpGroup("G", ok, d);
        gop.call();
        assertThat(d.exists("G")).isTrue();

        // trying to group again with same name should throw
        try {
            OpGroup gop2 = new OpGroup("G", ok, d);
            gop2.call();
            throw new AssertionError("Expected IllegalArgumentException for existing group");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // trying to group a member that already has father
        try {
            List<String> single = new ArrayList<>(); single.add("A");
            OpGroup g2 = new OpGroup("G2", single, d);
            g2.call();
            throw new AssertionError("Expected IllegalArgumentException for member with father");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testOpDeleteGroupFlowAndUndo() {
        Data d = new Data();
        OpAdd a1 = new OpAdd(new String[]{"circle","A","0","0","1"}, d);
        a1.call();
        OpAdd a2 = new OpAdd(new String[]{"rectangle","B","0","5","2","2"}, d);
        a2.call();
        List<String> ok = new ArrayList<>(); ok.add("A"); ok.add("B");
        OpGroup gop = new OpGroup("G", ok, d);
        gop.call();
        assertThat(d.exists("G")).isTrue();

        OpDelete delG = new OpDelete("G", d);
        delG.call();
        // G and members should be removed
        assertThat(d.exists("G")).isFalse();
        assertThat(d.exists("A")).isFalse();
        assertThat(d.exists("B")).isFalse();

        // undo should restore
        delG.undo();
        assertThat(d.exists("G")).isTrue();
        assertThat(d.exists("A")).isTrue();
        assertThat(d.exists("B")).isTrue();
    }

    @Test
    public void testOpUngroupErrors() {
        Data d = new Data();
        // ungroup non-existing
        try {
            OpUngroup u = new OpUngroup("No", d);
            u.call();
            throw new AssertionError("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }

        // add a circle and ungroup should fail (not a Group)
        OpAdd a = new OpAdd(new String[]{"circle","C","0","0","1"}, d);
        a.call();
        try {
            OpUngroup u2 = new OpUngroup("C", d);
            u2.call();
            throw new AssertionError("Expected IllegalArgumentException for not a Group");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
