package clevis.util;

import clevis.util.shape.Circle;
import clevis.util.shape.Point;
import org.junit.Test;

/**
 * test Circle
 */
public class TestCircle {
    /**
     * test Circle to String
     */
    @Test
    public void test2String() {
        Point p = new Point(0, 0);
        Circle circle = new Circle("good name", p, 1);
        System.out.println(circle);
    }

    /**
     * test bounding box
     */
    @Test
    public void testBoundingBox() {
        Point p = new Point(0, 0);
        Circle circle = new Circle("bad name", p, 1);
        System.out.println(circle.boundingBox());
    }
}
