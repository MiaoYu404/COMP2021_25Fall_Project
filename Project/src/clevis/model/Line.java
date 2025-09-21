package clevis.model;

import static clevis.model.ComputingGeometry.eps;
import static clevis.model.ComputingGeometry.sign;

public class Line {
    Point[] ps = new Point[2];

    Line() { }
    Line(Point p1, Point p2) {
        ps[0] = p1;
        ps[1] = p2;
    }

    // return the direction of the vector
    Point direction() { return Points.minus(ps[1], ps[0]); }

    // point is on the line ?
    boolean include (Point p) {
        return sign(direction().det(
                Points.minus(p, ps[0])
        )) > 0;
    }
}
