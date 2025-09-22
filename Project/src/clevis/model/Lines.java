package clevis.model;

import static clevis.model.ComputingGeometry.sign;

public class Lines {

    public static boolean intersects (Line l1, Line l2) {
        double x1 = l1.points[0].x(), y1 = l1.points[0].y();
        double x2 = l1.points[1].x(), y2 = l1.points[1].y();
        double x3 = l2.points[0].x(), y3 = l2.points[0].y();
        double x4 = l2.points[1].x(), y4 = l2.points[1].y();
        if (Double.max(x1, x2) < Double.min(x3, x4)
                || Double.max(y1, y2) < Double.min(y3, y4)
                || Double.max(x3, x4) < Double.min(x1, x2)
                || Double.max(y3, y4) < Double.min(y1, y2)
        ) return false;

        Point v31 = new Point(x1 - x3, y1 - y3), v32 = new Point(x2 - x3, y2 - y3);
        Point v13 = new Point(x3 - x1, y3 - y1), v14 = new Point(x4 - x1, y4 - y1);
        // det (x) tells whether point is on vector's anticlockwise/ clockwise side, or even on the same line.
        if (sign(v31.det(l2.direction()) * v32.det(l2.direction())) > 0
                || sign(v13.det(l1.direction()) * v14.det(l1.direction())) > 0
        ) return false;

        return true;
    }
}
