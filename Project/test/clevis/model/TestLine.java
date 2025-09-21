package clevis.model;

import org.junit.Test;

public class TestLine {
    @Test
    public void test2String() {
        Point p1 = new Point(-1.0, 0.0);
        Point p2 = new Point(1.5, 5);
        Line l1 = new Line(p1, p2);
        System.out.println(l1.toString());
    }

    @Test
    public void testBoundingBox() {
        Point p1 = new Point(-1.0, 0.0);
        Point p2 = new Point(1.5, 5);
        Line l1 = new Line("goodLine", p1, p2);
        System.out.println(l1.boundingBox());
    }
}