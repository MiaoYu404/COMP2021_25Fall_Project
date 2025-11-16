package clevis.model;

import clevis.sql.Lines;
import clevis.util.Circle;
import clevis.util.Line;
import clevis.util.Point;
import clevis.util.Rectangle;
import org.junit.Before;
import org.junit.Test;
import static clevis.sql.Geometry.intersects;
import static org.junit.Assert.*;

import static com.google.common.truth.Truth.assertThat;

public class TestLine {
    Line l1, l2, l3, l4, l5;
    Point p1,p2,p3,p4,p5, p6;
    @Before
    public void setUp() {
        Point p1 = new Point(-3, -2);
        Point p2 = new Point(6, 9);
        Point p3 = new Point(2.0, 2.0), p4 = new Point(4.0, 0.0);
        Point p5 = new Point(2.0, 1.0), p6 = new Point(3.9999999999, 0.0);
        l1 = new Line(p3, p4);
        l2 = new Line(p1, p2);
        l3 = new Line(p5, p6);
        l4 = new Line(p4, p5);
        l5 = new Line(p2, p3);

    }
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

    @Test
    public void testDet(){
        assertThat(l1.det(l2)).isEqualTo(40);
        assertThat(l4.det(l5)).isEqualTo(18);
        assertThat(l5.det(l1)).isEqualTo(22);

    }

    @Test
    public void testReverse(){
        assertEquals(l1.reverse(), new Line(p4,p3));
        assertEquals(l4.reverse(), new Line(p5,p4));
        assertEquals(l5.reverse(),  new Line(p3,p2));
        assertEquals(l2.reverse(), new Line(p2,p1));
        assertEquals(l3.reverse(), new Line(p6,p5));
    }

    @Test
    public void testInside(){
        assertThat(l1.inside(l2)).isFalse();
        assertThat(l4.inside(l3)).isFalse();
        assertThat(l2.inside(l4)).isFalse();
        assertThat(l3.inside(l2)).isFalse();
        assertThat(l2.inside(l1)).isFalse();

    }

    @Test
    public void testEquals(){
        assertThat(l1.equals(l2)).isFalse();
        assertThat(l1.equals(l3)).isFalse();
        assertThat(l1.equals(l4)).isFalse();
        assertThat(l1.equals(l5)).isFalse();
        assertThat(l1.equals(l1)).isTrue();
        assertThat(l2.equals(l2)).isTrue();

    }

    @Test
    public void testMove(){
        l1.move(2,3);
        assertThat(l1.from()).isEqualTo(new Point(4,5));
        assertThat(l1.to()).isEqualTo(new Point(6,3));

        l2.move(0,0);
        assertThat(l2.from()).isEqualTo(new Point(-3,-2));
        assertThat(l2.to()).isEqualTo(new Point(6,9));


    }
}

