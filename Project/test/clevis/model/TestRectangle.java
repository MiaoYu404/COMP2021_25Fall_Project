package clevis.model;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

public class TestRectangle {
    @Test
    public void test2String() {
        Point p1 =  new Point(0, 0);
        Rectangle r = new Rectangle(p1, 2.0, 3.0);
        System.out.println(r.toString());
    }

    @Test
    public void testBoundingBox() {
        Point p1 =  new Point(0, 0);
        Rectangle r = new Rectangle(p1, 2.0, 3.0);
//        System.out.println(r.boundingBox());
//        System.out.println(r.boundingBox().boundingBox());
        assertThat(r.boundingBox().boundingBox()).isEqualTo(r.boundingBox());
    }
}
