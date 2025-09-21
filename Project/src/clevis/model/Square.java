package clevis.model;

import java.util.Arrays;

public class Square extends Rectangle {
    Square() { this(new Point(0, 0), 1); }
    Square(Point p, double _side) {
        width = height = _side;

        points = new Point[4];
        points[0] = Points.add(p, new Point(_side / -2, _side / 2));
        points[1] =  Points.add(p, new Point(_side / -2, _side / -2));
        points[2] =  Points.add(p, new Point(_side / 2, _side / -2));
        points[3] =  Points.add(p, new Point(_side / 2, _side / 2));

        lines = new Line[4];
        lines[0] = new Line(points[0], points[1]);
        lines[1] = new Line(points[1], points[2]);
        lines[2] = new Line(points[2], points[3]);
        lines[3] = new Line(points[3], points[0]);
    }
    Square(String _name, Point p, double _side) { this(p, _side); name = _name; }
    Square(Square o) {
        name = o.name;
        width = height = o.width;

        points = Arrays.copyOf(o.points, 4);
        lines = Arrays.copyOf(o.lines, 4);
    }
    Square(Square o, String _name) { this(o); name = _name; }

    @Override
    public String toString() {
        String ret = "\"" + name + "\"<Square>:";
        ret += "\nPoints:";
        for (int i = 0; i < 4; i++) {
            ret = ret + "\n" + points[i].toString();
        }
        ret = ret + "\nLines:";
        for (int i = 0; i < 4; i++) {
            ret = ret + "\n" + lines[i].toString();
        }
        return ret;
    }
}
