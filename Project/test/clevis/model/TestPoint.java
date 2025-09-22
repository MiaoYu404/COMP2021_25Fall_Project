package clevis.model;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

public class TestPoint {
    @Test
    public void testOperation () {
        Point p1 = new Point(-1.0, 0.0);
        Point p2 = new Point(1.5, 5);
        assertThat(Points.add(p1, p2)).isEqualTo(new Point(0.5, 5.0));

        assertThat(Points.minus(p1, p2)).isEqualTo(new Point(-2.5, -5.0));

        assertThat(Points.multiply(p1, 0)).isEqualTo(new Point(0.0, 0.0));
        assertThat(Points.multiply(p1, 2)).isEqualTo(new Point(-2, 0.0));

        assertThat(Points.divide(p1, 2)).isEqualTo(new Point(-0.5, 0.0));
        try {
            assertThat(Points.divide(p1, 0));
        } catch (ArithmeticException e) {

        }
    }

    @Test
    public void testEps() {
        Point p1 = new Point(-1.0, 0.0);
        System.out.println("Test 1 cmp to " + new Point(-0.49999999999, 0.0).toString() + ".");
        Point rsl = Points.minus( Points.divide(p1, 2), new Point(-0.49999999999, 0.0));
        System.out.println("Calculation result is x: " + rsl.x() + ", y: " + rsl.y() + ".");
        assertThat(Points.divide(p1, 2)).isEqualTo(new Point(-0.49999999999, 0.0));
    }

    @Test
    public void testInside() {
        Point tlp = new Point(-5, 3);
        Rectangle r = new Rectangle(tlp, 9.5, 3.5);

        Point p1 = new Point(-5, 1);
        Point p2 = new Point(Math.PI, Math.PI);
        Point p3 = new Point(Math.E, Math.E);
        assertThat(p1.inside(r)).isTrue();
        assertThat(p2.inside(r)).isFalse();
        assertThat(p3.inside(r)).isTrue();
    }
}
