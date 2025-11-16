package clevis.util;

import clevis.sql.Lines;
import static clevis.sql.Geometry.intersects;

import clevis.util.shape.*;
import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

public class TestLine {
    @Test
    public void test2String() {
        Point p1 = new Point(-1.0, 0.0);
        Point p2 = new Point(1.5, 5);
        Line l1 = new Line(p1, p2);
        System.out.println(l1);
    }

    @Test
    public void testBoundingBox() {
        Point p1 = new Point(-1.0, 0.0);
        Point p2 = new Point(1.5, 5);
        Line l1 = new Line("goodLine", p1, p2);
        System.out.println(l1.boundingBox());
    }

    @Test
    public void testIntersection() {
        Point p1 = new Point(0.0, 0.0), p2 = new Point(3.0, 3.0);
        Point p3 = new Point(2.0, 2.0), p4 = new Point(4.0, 0.0);
        Point p5 = new Point(2.0, 1.0), p6 = new Point(3.9999999999, 0.0);
        Line l1 = new Line("A", p1, p2);
        Line l2 = new Line("B", p3, p4);
        Line l3 = new Line("C", p5, p4);
        Line l4 = new Line("D", p5, p6);
        assertThat(Lines.intersects(l1, l2)).isTrue();
        assertThat(Lines.intersects(l2, l3)).isTrue();
        assertThat(Lines.intersects(l3, l1)).isFalse();
        assertThat(Lines.intersects(l2, l4)).isTrue();

        Rectangle r1 = new Rectangle(p3, 1, 1);
        Rectangle r2 = new Rectangle(p3, 0.5, 0.5);
        assertThat(intersects(l1, r1)).isTrue();
        assertThat(intersects(l2, r1)).isTrue();
        assertThat(intersects(l3, r1)).isTrue();
        assertThat(intersects(l1, r2)).isTrue();
        assertThat(intersects(l2, r2)).isTrue();
        assertThat(intersects(l3, r2)).isFalse();

        Rectangle r3 = new Rectangle(p3, 0.999, 0.999);
        Rectangle r4 = new Rectangle(p3, 0.999999999999999999, 0.999999999999999999);
        assertThat(intersects(l3, r3)).isFalse();
        assertThat(intersects(l3, r4)).isTrue();

        Circle c1 = new Circle(p1, 1);
        assertThat(intersects(l1, c1)).isTrue();
        assertThat(intersects(l2, c1)).isFalse();
        assertThat(intersects(l3, c1)).isFalse();
        Circle c2 = new Circle(p1, Math.sqrt(5));
        assertThat(intersects(l1, c2)).isTrue();
        assertThat(intersects(l2, c2)).isTrue();
        assertThat(intersects(l3, c2)).isTrue();
    }
}