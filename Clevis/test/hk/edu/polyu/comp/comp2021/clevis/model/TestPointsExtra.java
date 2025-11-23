package hk.edu.polyu.comp.comp2021.clevis.model;

import hk.edu.polyu.comp.comp2021.clevis.model.shape.Point;
import hk.edu.polyu.comp.comp2021.clevis.model.util.Points;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class TestPointsExtra {

    @Test
    public void testCopyOfAndRange() {
        Point[] arr = new Point[] { new Point(0,0), new Point(1,1), new Point(2,2) };
        Point[] copy = Points.copyOf(arr, 3);
        assertThat(copy).isNotNull();
        assertThat(copy.length).isEqualTo(3);
        Point[] range = Points.copyOfRange(arr, 1, 3);
        assertThat(range.length).isEqualTo(2);
        assertThat(range[0]).isEqualTo(new Point(1,1));
    }

    @Test(expected = NullPointerException.class)
    public void testCopyOfNull() {
        Points.copyOf(null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopyOfTooLarge() {
        Point[] arr = new Point[] { new Point(0,0) };
        Points.copyOf(arr, 2);
    }

    @Test(expected = ArithmeticException.class)
    public void testDivideByZero() {
        Points.divide(new Point(1,1), 0.0);
    }
}
