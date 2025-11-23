package hk.edu.polyu.comp.comp2021.clevis.model;

import hk.edu.polyu.comp.comp2021.clevis.model.shape.Circle;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Point;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Rectangle;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Graphics;


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

    @Test
    public void testConstructorsAndAccessors() {
        // default constructor
        Circle d = new Circle();
        assertEquals(new Point(0,0), d.center());
        assertEquals(1.0, d.r(), 1e-9);

        // named constructor and copy
        Circle named = new Circle("circleA", new Point(2,3), 4);
        assertEquals("circleA", named.name());
        Circle copy = new Circle(named);
        assertEquals(named.name(), copy.name());
        assertEquals(named.center(), copy.center());
        assertEquals(named.r(), copy.r(), 1e-9);
    }

    @Test
    public void testEqualsAndShortNameAndFather() {
        Circle a = new Circle("A", new Point(0,0), 1);
        Circle b = new Circle("B", new Point(0,0), 1);
        Circle a2 = new Circle("A", new Point(0,0), 1);

        assertFalse(a.equals(b));
        assertTrue(a.equals(a));
        assertTrue(a.equals(a2));

        // equals against null and other types
        assertFalse(a.equals(null));
        assertFalse(a.equals(new Object()));

        // shortName
        assertTrue(a.shortName().contains("<Circle>"));

        // father
        assertFalse(a.haveFather());
        a.setFather(b);
        assertTrue(a.haveFather());
        assertEquals(b, a.father());
    }

    @Test
    public void testBoundingBoxNameAndToStringAndDraw() {
        Circle circ = new Circle("named", new Point(5,5), 2);
        Rectangle bb = circ.boundingBox();
        // bounding box name should include original name
        assertTrue(bb.name().contains("named"));

        // toString includes name and <Circle>
        String s = circ.toString();
        assertTrue(s.contains("named"));
        assertTrue(s.contains("<Circle>"));

        // draw should not throw - create a small buffered image to obtain Graphics2D
        BufferedImage img = new BufferedImage(10,10, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) img.getGraphics();
        circ.draw(g2);
        g2.dispose();
    }

}


