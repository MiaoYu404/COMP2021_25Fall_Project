package clevis.util;

import org.junit.Test;

public class TestCircle {
    @Test
    public void test2String() {
        Point p = new Point(0, 0);
        Circle circle = new Circle("good name", p, 1);
        System.out.println(circle);
    }

    @Test
    public void testBoundingBox() {
        Point p = new Point(0, 0);
        Circle circle = new Circle("bad name", p, 1);
        System.out.println(circle.boundingBox());
    }
}
