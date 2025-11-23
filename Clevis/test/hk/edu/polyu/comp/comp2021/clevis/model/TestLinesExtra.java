package hk.edu.polyu.comp.comp2021.clevis.model;

import hk.edu.polyu.comp.comp2021.clevis.model.shape.Line;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Point;
import hk.edu.polyu.comp.comp2021.clevis.model.util.Lines;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class TestLinesExtra {
    @Test
    public void testIntersectsFalseByBoundingBox() {
        Line l1 = new Line(new Point(0,0), new Point(1,1));
        Line l2 = new Line(new Point(10,10), new Point(11,11));
        assertThat(Lines.intersects(l1, l2)).isFalse();
    }

    @Test
    public void testCopyOfAndRangeAndErrors() {
        Line[] arr = new Line[] { new Line(new Point(0,0), new Point(1,1)), new Line(new Point(1,1), new Point(2,2)) };
        Line[] copy = Lines.copyOf(arr, 2);
        assertThat(copy.length).isEqualTo(2);
        Line[] range = Lines.copyOfRange(arr, 0, 2);
        assertThat(range.length).isEqualTo(2);
    }

    @Test(expected = NullPointerException.class)
    public void testCopyOfNull() {
        Lines.copyOf(null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopyOfTooSmall() {
        Line[] arr = new Line[] { new Line(new Point(0,0), new Point(1,1)) };
        Lines.copyOf(arr, 2);
    }
}
