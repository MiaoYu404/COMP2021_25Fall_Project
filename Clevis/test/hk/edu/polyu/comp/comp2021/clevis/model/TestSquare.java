package hk.edu.polyu.comp.comp2021.clevis.model;

import hk.edu.polyu.comp.comp2021.clevis.model.shape.Square;
import org.junit.Before;
import org.junit.Test;

public class TestSquare {
    Square s1, s2, s3, s4;
    @Before
    public void setUp() {
        s1 = new Square();
        s2 = new Square(new hk.edu.polyu.comp.comp2021.clevis.model.shape.Point(1,1), 2);
        s3 = new Square("S3", new hk.edu.polyu.comp.comp2021.clevis.model.shape.Point(-1,2), 3);
        s4 = new Square(s3);
    }

    @Test
    public void testConstructor() {
        assert s1.side() == 1.0;
        assert s2.side() == 2.0;
        assert s3.side() == 3.0;
        // copy has same side and name
        assert s4.side() == s3.side();
    }

    @Test
    public void testToString() {
        String s = s3.toString();
        assert s.contains("<Square>");
        assert s.contains("side");

    }

    @Test
    public void testSide() {
        // center computation sanity
        hk.edu.polyu.comp.comp2021.clevis.model.shape.Point c = s2.center();
        // s2 constructed at top-left (1,1) with side 2 -> center should be (1+1,1-1) = (2,0)
        assert c.equals(new hk.edu.polyu.comp.comp2021.clevis.model.shape.Point(2,0));

    }

}
