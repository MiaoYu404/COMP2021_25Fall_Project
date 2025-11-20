package hk.edu.polyu.comp.comp2021.clevis.model;

import hk.edu.polyu.comp.comp2021.clevis.model.shape.Circle;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Point;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestCircle {
    Circle c1, c2, c3, c4;

    @Before
    public void setUp() {
        c1 = new Circle();
        c2 = new Circle(new Point(1,2),3);
        c3 = new Circle(new Point(5, 6),1);
        c4 = new Circle(new Point(1,4),2);
    }
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

    @Test
    public void testEquals(){
        assertFalse(c1.equals(c2));
        assertFalse(c1.equals(c3));
        assertFalse(c1.equals(c4));
        assertTrue(c1.equals(c1));
        assertTrue(c2.equals(c2));

    }

    @Test
    public void testIntersects(){
        assertTrue(c1.intersects(c2));
        assertFalse(c1.intersects(c3));
        assertFalse(c1.intersects(c4));
        assertTrue(c1.intersects(c1));
        assertTrue(c3.intersects(c3));
    }

    @Test
    public void testMove(){
        c2.move(-2,-3);
        Point newC2 = new Point(-1,-1);
        assertEquals(c2.center(),newC2);

        c3.move(5,6);
        Point newC3 = new Point(10,12);
        assertEquals(c3.center(),newC3);

        c4.move(0,0);
        Point newC4 = new Point(1,4);
        assertEquals(c4.center(),newC4);
    }

}


