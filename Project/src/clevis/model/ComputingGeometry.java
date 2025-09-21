package clevis.model;

public class ComputingGeometry {
    public static double eps = 1e-9;

    public static int sign(double a) { return a < -eps ? -1 : ( a > eps ? 1 : 0); }

    public static boolean intersection() { return false; }
}
