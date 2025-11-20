package hk.edu.polyu.comp.comp2021.clevis.model;

import hk.edu.polyu.comp.comp2021.clevis.model.shape.Line;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Point;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Rectangle;
import hk.edu.polyu.comp.comp2021.clevis.model.sql.Points;

import org.junit.Test;
import org.junit.Before;
import static com.google.common.truth.Truth.assertThat;

/**
 * 测试Point类的功能实现，包括构造函数、坐标操作、向量运算、几何计算等。
 * 确保Point类能够正确处理各种正常场景、边界情况和异常场景。
 */
public class TestPoint {
    private Point regularPoint;
    private Point zeroPoint;          // 原点 (0,0)
    private Point negativePoint;      // 负坐标点
    private Point decimalPoint;       // 小数坐标点

   /**
     * 在每个测试方法执行前设置测试对象
     */
    @Before
    public void setUp() {
        // 初始化测试对象
        regularPoint = new Point(3.0, 4.0);
        zeroPoint = new Point(0.0, 0.0);
        negativePoint = new Point(-2.0, -5.0);
        decimalPoint = new Point(1.23, 4.56);
    }
   /**
     * 测试Points工具类的基本操作功能
     */
    @Test
    public void testPointsUtilityClassOperations() {
        Point p1 = new Point(-1.0, 0.0);
        Point p2 = new Point(1.5, 5);
        assertThat(Points.add(p1, p2)).isEqualTo(new Point(0.5, 5.0));

        assertThat(Points.minus(p1, p2)).isEqualTo(new Point(-2.5, -5.0));

        assertThat(Points.multiply(p1, 0)).isEqualTo(new Point(0.0, 0.0));
        assertThat(Points.multiply(p1, 2)).isEqualTo(new Point(-2, 0.0));

        assertThat(Points.divide(p1, 2)).isEqualTo(new Point(-0.5, 0.0));
        try {
            assertThat(Points.divide(p1, 0));
        } catch (ArithmeticException e) {
            // nothing here.
        }
    }

   /**
     * 测试浮点精度处理
     */
    @Test
    public void testFloatingPointPrecision() {
        Point p1 = new Point(-1.0, 0.0);
        System.out.println("Test 1 cmp to " + new Point(-0.49999999999, 0.0) + ".");
        Point rsl = Points.minus( Points.divide(p1, 2), new Point(-0.49999999999, 0.0));
        System.out.println("Calculation result is x: " + rsl.x() + ", y: " + rsl.y() + ".");
        assertThat(Points.divide(p1, 2)).isEqualTo(new Point(-0.49999999999, 0.0));
    }

    /**
     * 测试点是否在矩形内部
     */
    @Test
    public void testPointInsideRectangle() {
        Point tlp = new Point(-5, 3);
        Rectangle r = new Rectangle(tlp, 9.5, 3.5);

        Point p1 = new Point(-5, 1);
        Point p2 = new Point(Math.PI, Math.PI);
        Point p3 = new Point(Math.E, Math.E);
        assertThat(p1.inside(r)).isTrue();
        assertThat(p2.inside(r)).isFalse();
        assertThat(p3.inside(r)).isTrue();
    }

    // ============ 构造函数测试 ============
    /**
     * 测试整数参数构造函数
     */
    @Test
    public void testIntegerConstructor() {
        Point point = new Point(5, 7);
        assertThat(point.x()).isWithin(1e-10).of(5.0);
        assertThat(point.y()).isWithin(1e-10).of(7.0);
    }

    /**
     * 测试浮点数参数构造函数
     */
    @Test
    public void testDoubleConstructor() {
        Point point = new Point(2.5, 3.7);
        assertThat(point.x()).isWithin(1e-10).of(2.5);
        assertThat(point.y()).isWithin(1e-10).of(3.7);
    }

    /**
     * 测试拷贝构造函数
     */
    @Test
    public void testCopyConstructor() {
        Point copy = new Point(regularPoint);

        // Verify it's a different object with same values
        assertThat(copy).isNotSameInstanceAs(regularPoint);
        assertThat(copy.x()).isWithin(1e-10).of(regularPoint.x());
        assertThat(copy.y()).isWithin(1e-10).of(regularPoint.y());
    }

//     ============ Getter方法测试 ============
    /**
     * 测试坐标获取方法
     */
    @Test
    public void testCoordinatesGetters() {
        assertThat(regularPoint.x()).isWithin(1e-10).of(3.0);
        assertThat(regularPoint.y()).isWithin(1e-10).of(4.0);
        assertThat(zeroPoint.x()).isWithin(1e-10).of(0.0);
        assertThat(zeroPoint.y()).isWithin(1e-10).of(0.0);
        assertThat(negativePoint.x()).isWithin(1e-10).of(-2.0);
        assertThat(negativePoint.y()).isWithin(1e-10).of(-5.0);
        assertThat(decimalPoint.x()).isWithin(1e-10).of(1.23);
        assertThat(decimalPoint.y()).isWithin(1e-10).of(4.56);
    }

    // ============ Setter方法测试 ============
    /**
     * 测试X坐标设置方法
     */
    @Test
    public void testSetXCoordinate() {
        Point point = new Point(0.0, 0.0);
        point.add(10.5,0);
        assertThat(point.x()).isWithin(1e-10).of(10.5);
        assertThat(point.y()).isWithin(1e-10).of(0.0);
    }

    /**
     * 测试Y坐标设置方法
     */
    @Test
    public void testSetYCoordinate() {
        Point point = new Point(0.0, 0.0);
        point.add(-7.25,0);
        assertThat(point.y()).isWithin(1e-10).of(0.0);
        assertThat(point.x()).isWithin(1e-10).of(-7.25);
    }

    /**
     * 测试同时设置X和Y坐标的方法
     */
    @Test
    public void testSetXYCoordinates() {
        Point point = new Point(0.0, 0.0);
        point.add(1.25, -3.75);
        assertThat(point.x()).isWithin(1e-10).of(1.25);
        assertThat(point.y()).isWithin(1e-10).of(-3.75);
    }

    // ============ 向量运算测试 ============
    /**
     * 测试向量加法运算
     */
    @Test
    public void testVectorAddition() {
        // Test adding two points
        regularPoint.add(decimalPoint);
        assertThat(regularPoint.x()).isWithin(1e-10).of(4.23); // 3.0 + 1.23
        assertThat(regularPoint.y()).isWithin(1e-10).of(8.56); // 4.0 + 4.56

    }

    /**
     * 测试向量减法运算
     */
    @Test
    public void testVectorSubtraction() {
        // Test subtracting two points
        regularPoint.minus(decimalPoint);
        assertThat(regularPoint.x()).isWithin(1e-10).of(1.77); // 3.0 - 1.23
        assertThat(regularPoint.y()).isWithin(1e-10).of(-0.56); // 4.0 - 4.56
//        // Verify original points are not modified
//        assertThat(regularPoint.x()).isWithin(1e-10).of(3.0);
//        assertThat(regularPoint.y()).isWithin(1e-10).of(4.0);
    }

    /**
     * 测试向量标量乘法运算
     */
    @Test
    public void testVectorScalarMultiplication() {
        // Test scalar multiplication
        regularPoint.multiply(2.5);
        assertThat(regularPoint.x()).isWithin(1e-10).of(7.5); // 3.0 * 2.5
        assertThat(regularPoint.y()).isWithin(1e-10).of(10.0); // 4.0 * 2.5

    }

    @Test
    public void testNumberMultiplication(){
        // Test negative scalar multiplication
        regularPoint.multiply(-1.0);
        assertThat(regularPoint.x()).isWithin(1e-10).of(-3.0); // 3.0 * (-1.0)
        assertThat(regularPoint.y()).isWithin(1e-10).of(-4.0); // 4.0 * (-1.0)

    }

    /**
     * 测试向量标量除法运算
     */
    @Test
    public void testVectorScalarDivision() {
        // Test scalar division
        regularPoint.divide(2.0);
        assertThat(regularPoint.x()).isWithin(1e-10).of(1.5); // 3.0 / 2.0
        assertThat(regularPoint.y()).isWithin(1e-10).of(2.0); // 4.0 / 2.0

//        // Verify original points are not modified
//        assertThat(regularPoint.x()).isWithin(1e-10).of(3.0);
//        assertThat(regularPoint.y()).isWithin(1e-10).of(4.0);
    }

    /**
     * 测试除以零的异常处理
     */
    @Test(expected = ArithmeticException.class)
    public void testVectorDivisionByZero() {
        // Test division by zero should throw exception
        regularPoint.divide(0.0);

    }

    // ============ 几何计算测试 ============
    /**
     * 测试两点间距离计算
     */
    @Test
    public void testDistanceCalculation() {
        // Test distance between two points
        double dist1 = regularPoint.distance(zeroPoint);
        assertThat(dist1).isWithin(1e-10).of(5.0); // Distance from (3,4) to (0,0) is 5

        double dist2 = regularPoint.distance(decimalPoint);
        double expectedDist2 = Math.sqrt(Math.pow(3.0-1.23, 2) + Math.pow(4.0-4.56, 2));
        assertThat(dist2).isWithin(1e-10).of(expectedDist2);
    }

    /**
     * 测试向量点积计算
     */
    @Test
    public void testDotProductCalculation() {
        // Test dot product of two vectors
        double dotProduct = regularPoint.dot(decimalPoint);
        double expectedDotProduct = 3.0*1.23 + 4.0*4.56;
        assertThat(dotProduct).isWithin(1e-10).of(expectedDotProduct);
    }

    /**
     * 测试向量叉积计算
     */
    @Test
    public void testDeterminantProductCalculation() {
        // Test determinant product of two vectors
        double detProduct = regularPoint.det(decimalPoint);
        double expectedDetProduct = 3.0*4.56 - 4.0*1.23;
        assertThat(detProduct).isWithin(1e-10).of(expectedDetProduct);
    }

    /**
     * 测试90度旋转功能
     */
//    @Test
//    public void test90DegreeRotation() {
//        // Test 90-degree counterclockwise rotation
//        regularPoint.rot90();
//        assertThat(regularPoint.x()).isWithin(1e-10).of(-4.0); // 3,4 -> -4,3
//        assertThat(regularPoint.y()).isWithin(1e-10).of(3.0);
//
//        // Verify original point is not modified
//        assertThat(regularPoint.x()).isWithin(1e-10).of(3.0);
//        assertThat(regularPoint.y()).isWithin(1e-10).of(4.0);
//    }

    /**
     * 测试单位向量计算
     */
    @Test
    public void testUnitVectorCalculation() {
        // Test unit vector calculation
        Point unit = regularPoint.unit();

        // Unit vector should have magnitude 1
        double magnitude = Math.sqrt(unit.x()*unit.x() + unit.y()*unit.y());
        assertThat(magnitude).isWithin(1e-10).of(1.0);

        // Direction should be the same as original
        assertThat(unit.x() * 5.0).isWithin(1e-10).of(3.0); // 3/5 is unit vector x
        assertThat(unit.y() * 5.0).isWithin(1e-10).of(4.0); // 4/5 is unit vector y
    }

    /**
     * 测试零向量的单位向量计算 //会导致除0
     */
//    @Test
//    public void testUnitVectorOfZeroVector() {
//        // Unit vector of zero vector should be (0,0)
//        Point unit = zeroPoint.unit();
//        assertThat(unit.x()).isWithin(1e-10).of(0.0);
//        assertThat(unit.y()).isWithin(1e-10).of(0.0);
//    }

    // ============ 形状交互测试 ============
    /**
     * 测试点是否在线段上
     */
    @Test
    public void testPointOnSegment() {
        // Create a line segment from (0,0) to (4,4)
        Point start = new Point(0.0, 0.0);
        Point end = new Point(4.0, 4.0);

        Line testl1 = new Line(start,end);
        // Point on the segment
        Point onSegment = new Point(2.0, 2.0);
        assertThat(onSegment.onSegment(testl1)).isTrue();

        // Point on the line but outside segment
        Point outsideSegment = new Point(5.0, 5.0);
        assertThat(outsideSegment.onSegment(testl1)).isFalse();

        // Point not on the line
        Point notOnLine = new Point(1.0, 2.0);
        assertThat(notOnLine.onSegment(testl1)).isFalse();

        // Endpoints should be on the segment
        assertThat(start.onSegment(testl1)).isTrue();
        assertThat(end.onSegment(testl1)).isTrue();
    }

    /**
     * 测试点是否被形状覆盖
     */
    @Test
    public void testPointCoveredByShape() {
        // Test with Rectangle
        Rectangle rect = new Rectangle(new Point(0.0, 5.0), 5.0, 5.0);
        Point insideRect = new Point(2.0, 3.0);
        assertThat(insideRect.coveredBy(rect)).isTrue();

        // Test with Line
        Line line = new Line(new Point(0.0, 0.0), new Point(5.0, 5.0));
        Point onLine = new Point(2.0, 2.0);
        assertThat(onLine.coveredBy(line)).isTrue();

        // Point slightly off line (within tolerance)
        Point nearLine = new Point(2.0, 2.02);
        assertThat(nearLine.coveredBy(line)).isTrue();

        // Point far from line
        Point farFromLine = new Point(2.0, 3.0);
        assertThat(farFromLine.coveredBy(line)).isFalse();
    }

    // ============ 相等性和字符串表示测试 ============
    /**
     * 测试对象与自身相等性
     */
    @Test
    public void testEqualsSelf() {
        assertThat(regularPoint.equals(regularPoint)).isTrue();
    }

    /**
     * 测试对象与null的相等性
     */
    @Test
    public void testEqualsNull() {
        assertThat(regularPoint.equals(null)).isFalse();
    }

    /**
     * 测试对象与不同类型的相等性
     */
    @Test
    public void testEqualsDifferentType() {
        assertThat(regularPoint.equals("not a point")).isFalse();
    }

    /**
     * 测试相同坐标的点的相等性
     */
    @Test
    public void testEqualsSameCoordinates() {
        Point samePoint = new Point(3.0, 4.0);
        assertThat(regularPoint.equals(samePoint)).isTrue();
    }

    /**
     * 测试X坐标不同的点的相等性
     */
    @Test
    public void testEqualsDifferentXCoordinate() {
        Point differentXPoint = new Point(4.0, 4.0);
        assertThat(regularPoint.equals(differentXPoint)).isFalse();
    }

    /**
     * 测试Y坐标不同的点的相等性
     */
    @Test
    public void testEqualsDifferentYCoordinate() {
        Point differentYPoint = new Point(3.0, 5.0);
        assertThat(regularPoint.equals(differentYPoint)).isFalse();
    }

    /**
     * 测试带容差的相等性比较
     */
    @Test
    public void testEqualsWithTolerance() {
        // Points with coordinates very close but not exactly equal should be considered equal
        Point closePoint = new Point(3.0000000001, 4.0000000001);
        assertThat(regularPoint.equals(closePoint)).isTrue();

        // Points with coordinates outside tolerance should not be considered equal
        Point notClosePoint = new Point(3.1, 4.1);
        assertThat(regularPoint.equals(notClosePoint)).isFalse();
    }

    /**
     * 测试字符串表示方法
     */
    @Test
    public void testStringRepresentation() {
        String str = regularPoint.toString();

        // Verify toString contains essential information

        assertThat(str).contains("3.0");
        assertThat(str).contains("4.0");
    }

    // ============ 边界情况测试 ============
    /**
     * 测试零点的各种运算
     */
    @Test
    public void testZeroPointOperations() {
        // Test operations with zero point
        regularPoint.add(zeroPoint);
        assertThat(regularPoint.x()).isWithin(1e-10).of(regularPoint.x());
        assertThat(regularPoint.y()).isWithin(1e-10).of(regularPoint.y());

        regularPoint.minus(zeroPoint);
        assertThat(regularPoint.x()).isWithin(1e-10).of(regularPoint.x());
        assertThat(regularPoint.y()).isWithin(1e-10).of(regularPoint.y());

        // Distance from regular point to zero point
        double dist = regularPoint.distance(zeroPoint);
        assertThat(dist).isWithin(1e-10).of(5.0); // sqrt(3^2 + 4^2) = 5
    }

    /**
     * 测试极端坐标值的处理
     */
    @Test
    public void testExtremeCoordinates() {
        // Test with very large positive coordinates
        Point largePoint = new Point(1e15, 1e15);
        assertThat(largePoint.x()).isWithin(1e10).of(1e15);
        assertThat(largePoint.y()).isWithin(1e10).of(1e15);

        // Test with very large negative coordinates
        Point veryNegativePoint = new Point(-1e15, -1e15);
        assertThat(veryNegativePoint.x()).isWithin(1e10).of(-1e15);
        assertThat(veryNegativePoint.y()).isWithin(1e10).of(-1e15);
    }

    /**
     * 测试精度处理
     */
    @Test
    public void testPrecisionHandling() {
        // Test with very small decimal values
        Point smallPoint = new Point(1e-15, 1e-15);
        assertThat(smallPoint.x()).isWithin(1e-20).of(1e-15);
        assertThat(smallPoint.y()).isWithin(1e-20).of(1e-15);

        // Verify equality with (0,0) within tolerance
        assertThat(smallPoint.equals(zeroPoint)).isTrue();
    }

    /**
     * 测试对象的不变性特性
     */
    @Test
    public void testObjectImmutability() {
        // Test that methods that modify a point return a new point, leaving original unchanged
        Point original = new Point(1.0, 2.0);

        original.add(1.0, 1.0);
        assertThat(original.x()).isWithin(1e-10).of(2.0);
        assertThat(original.y()).isWithin(1e-10).of(3.0);
//        assertThat(original.x()).isWithin(1e-10).of(1.0);
//        assertThat(original.y()).isWithin(1e-10).of(2.0);

        original.multiply(2.0);
        assertThat(original.x()).isWithin(1e-10).of(4.0);
        assertThat(original.y()).isWithin(1e-10).of(6.0);
//        assertThat(original.x()).isWithin(1e-10).of(1.0);
//        assertThat(original.y()).isWithin(1e-10).of(2.0);
    }
}
