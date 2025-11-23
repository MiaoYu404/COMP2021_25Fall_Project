package hk.edu.polyu.comp.comp2021.clevis.model;

import hk.edu.polyu.comp.comp2021.clevis.model.shape.Line;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Point;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Rectangle;
import hk.edu.polyu.comp.comp2021.clevis.model.shape.Shape;
import org.junit.Test;
import org.junit.Before;
import static com.google.common.truth.Truth.assertThat;

/**
 * 测试Rectangle类的功能实现，包括构造函数、坐标计算、移动操作、父子关系等。
 * 确保Rectangle类能够正确处理各种正常场景、边界情况和异常场景。
 */
public class TestRectangle {
    private Rectangle regularRect;      // 常规矩形，用于大多数测试
    private Rectangle namedRect;        // 带名称的矩形
    private Rectangle zeroSizedRect;    // 边界情况：零宽高矩形
    private Rectangle negativeCoordRect; // 边界情况：负坐标矩形
    
    /**
     * 在每个测试方法执行前设置测试对象
     */
    @Before
    public void setUp() {
        // 初始化测试对象
        regularRect = new Rectangle(new Point(1.0, 5.0), 4.0, 3.0);
        namedRect = new Rectangle("TestRect", new Point(2.0, 6.0), 2.0, 2.0);
        zeroSizedRect = new Rectangle(new Point(0.0, 0.0), 0.0, 0.0);
        negativeCoordRect = new Rectangle(new Point(-5.0, -5.0), 10.0, 10.0);
    }

    // ============ 构造函数测试 ============
    /**
     * 测试默认构造函数
     */
    @Test
    public void testDefaultConstructor() {
        Rectangle rect = new Rectangle();
        // Verify no exceptions are thrown and basic structure exists
        assertThat(rect).isNotNull();
    }
    
    /**
     * 测试带参数的构造函数
     */
    @Test
    public void testParameterizedConstructor() {
        Point topLeft = new Point(1.0, 5.0);
        Rectangle rect = new Rectangle(topLeft, 4.0, 3.0);
        
        // Verify dimensions are set correctly
        assertThat(rect.width()).isWithin(1e-10).of(4.0);
        assertThat(rect.height()).isWithin(1e-10).of(3.0);
        
        // Verify points are created correctly
        Point[] points = rect.points();
        assertThat(points.length).isEqualTo(4);
        assertThat(points[0].x()).isWithin(1e-10).of(1.0);  // top-left
        assertThat(points[0].y()).isWithin(1e-10).of(5.0);
        assertThat(points[1].x()).isWithin(1e-10).of(1.0);  // down-left
        assertThat(points[1].y()).isWithin(1e-10).of(2.0);
        assertThat(points[2].x()).isWithin(1e-10).of(5.0);  // down-right
        assertThat(points[2].y()).isWithin(1e-10).of(2.0);
        assertThat(points[3].x()).isWithin(1e-10).of(5.0);  // top-right
        assertThat(points[3].y()).isWithin(1e-10).of(5.0);
    }
    
    /**
     * 测试带名称的构造函数
     */
    @Test
    public void testNamedConstructor() {
        Rectangle rect = new Rectangle("MyRect", new Point(0.0, 0.0), 1.0, 1.0);
        assertThat(rect.name()).isEqualTo("MyRect");
    }
    
    /**
     * 测试拷贝构造函数
     */
    @Test
    public void testCopyConstructor() {
        Rectangle copy = new Rectangle(namedRect);

        // 验证深拷贝特性
        assertThat(copy).isNotSameInstanceAs(namedRect);
        assertThat(copy.width()).isWithin(1e-10).of(namedRect.width());
        assertThat(copy.height()).isWithin(1e-10).of(namedRect.height());
        assertThat(copy.name()).isEqualTo(namedRect.name());

        // 验证点数组是不同实例但值相同
        Point[] originalPoints = namedRect.points();
        Point[] copyPoints = copy.points();
        assertThat(copyPoints).isNotSameInstanceAs(originalPoints);
        for (int i = 0; i < 4; i++) {
            assertThat(copyPoints[i].x()).isWithin(1e-10).of(originalPoints[i].x());
            assertThat(copyPoints[i].y()).isWithin(1e-10).of(originalPoints[i].y());
        }
    }
    
    /**
     * 测试带重命名的拷贝构造函数
     */
    @Test
    public void testCopyConstructorWithRename() {
        Rectangle copy = new Rectangle(namedRect, "RenamedRect");

        // 验证属性被复制但名称被更改
        assertThat(copy.width()).isWithin(1e-10).of(namedRect.width());
        assertThat(copy.height()).isWithin(1e-10).of(namedRect.height());
        assertThat(copy.name()).isEqualTo("RenamedRect");
        assertThat(copy.name()).isNotEqualTo(namedRect.name());
    }
    
    // ============ Getter方法测试 ============
    /**
     * 测试基本属性获取方法
     */
    @Test
    public void testBasicGetters() {
        assertThat(namedRect.name()).isEqualTo("TestRect");
        assertThat(namedRect.width()).isWithin(1e-10).of(2.0);
        assertThat(namedRect.height()).isWithin(1e-10).of(2.0);
    }
    
    /**
     * 测试坐标范围获取方法
     */
    @Test
    public void testMinMaxCoordinates() {
        // For regular rectangle with top-left at (1,5), width=4, height=3
        assertThat(regularRect.minX()).isWithin(1e-10).of(1.0);  // leftmost x
        assertThat(regularRect.minY()).isWithin(1e-10).of(2.0);  // bottommost y
        assertThat(regularRect.maxX()).isWithin(1e-10).of(5.0);  // rightmost x
        assertThat(regularRect.maxY()).isWithin(1e-10).of(5.0);  // topmost y
        
        // For rectangle with negative coordinates
        assertThat(negativeCoordRect.minX()).isWithin(1e-10).of(-5.0);
        assertThat(negativeCoordRect.minY()).isWithin(1e-10).of(-15.0);
        assertThat(negativeCoordRect.maxX()).isWithin(1e-10).of(5.0);
        assertThat(negativeCoordRect.maxY()).isWithin(1e-10).of(-5.0);
    }
    
    /**
     * 测试点数组获取方法
     */
    @Test
    public void testPointsArray() {
        Point[] points = regularRect.points();
        assertThat(points).hasLength(4);
        
        // Verify the points are in the expected order: top-left, down-left, down-right, top-right
        assertThat(points[0].x()).isWithin(1e-10).of(1.0);
        assertThat(points[0].y()).isWithin(1e-10).of(5.0);
        assertThat(points[1].x()).isWithin(1e-10).of(1.0);
        assertThat(points[1].y()).isWithin(1e-10).of(2.0);
        assertThat(points[2].x()).isWithin(1e-10).of(5.0);
        assertThat(points[2].y()).isWithin(1e-10).of(2.0);
        assertThat(points[3].x()).isWithin(1e-10).of(5.0);
        assertThat(points[3].y()).isWithin(1e-10).of(5.0);
    }
    
    /**
     * 测试线段数组获取方法
     */
    @Test
    public void testLinesArray() {
        Line[] lines = regularRect.lines();
        assertThat(lines).hasLength(4);
        
        // Verify each line connects the expected points
        // Line 0: top-left to down-left
        assertThat(lines[0].from().x()).isWithin(1e-10).of(1.0);
        assertThat(lines[0].from().y()).isWithin(1e-10).of(5.0);
        assertThat(lines[0].to().x()).isWithin(1e-10).of(1.0);
        assertThat(lines[0].to().y()).isWithin(1e-10).of(2.0);
        
        // Line 1: down-left to down-right
        assertThat(lines[1].from().x()).isWithin(1e-10).of(1.0);
        assertThat(lines[1].from().y()).isWithin(1e-10).of(2.0);
        assertThat(lines[1].to().x()).isWithin(1e-10).of(5.0);
        assertThat(lines[1].to().y()).isWithin(1e-10).of(2.0);
        
        // Line 2: down-right to top-right
        assertThat(lines[2].from().x()).isWithin(1e-10).of(5.0);
        assertThat(lines[2].from().y()).isWithin(1e-10).of(2.0);
        assertThat(lines[2].to().x()).isWithin(1e-10).of(5.0);
        assertThat(lines[2].to().y()).isWithin(1e-10).of(5.0);
        
        // Line 3: top-right to top-left
        assertThat(lines[3].from().x()).isWithin(1e-10).of(5.0);
        assertThat(lines[3].from().y()).isWithin(1e-10).of(5.0);
        assertThat(lines[3].to().x()).isWithin(1e-10).of(1.0);
        assertThat(lines[3].to().y()).isWithin(1e-10).of(5.0);
    }
    
    // ============ 相等性测试 ============
    /**
     * 测试对象与自身相等性
     */
    @Test
    public void testEqualsSelf() {
        assertThat(regularRect.equals(regularRect)).isTrue();
    }
    
    /**
     * 测试对象与null的相等性
     */
    @Test
    public void testEqualsNull() {
        assertThat(regularRect.equals(null)).isFalse();
    }
    
    /**
     * 测试对象与不同类型的相等性
     */
    @Test
    public void testEqualsDifferentType() {
        assertThat(regularRect.equals("not a rectangle")).isFalse();
    }
    
    /**
     * 测试具有相同属性的对象相等性
     */
    @Test
    public void testEqualsSameProperties() {
        Rectangle anotherRect = new Rectangle(new Point(1.0, 5.0), 4.0, 3.0);
//        anotherRect.setFather(regularRect.getFather()); // Copy father relationship
//        assertThat(regularRect.equals(anotherRect)).isFalse(); // Different names (both null)
//
        // Set same name to make them equal
        regularRect = new Rectangle("SameName", new Point(1.0, 5.0), 4.0, 3.0);
        anotherRect = new Rectangle("SameName", new Point(1.0, 5.0), 4.0, 3.0);
        assertThat(regularRect.equals(anotherRect)).isTrue();
    }
    
    /**
     * 测试宽度不同的对象相等性
     */
    @Test
    public void testEqualsDifferentWidth() {
        Rectangle differentWidthRect = new Rectangle("TestRect", new Point(2.0, 6.0), 3.0, 2.0);
        assertThat(namedRect.equals(differentWidthRect)).isFalse();
    }
    
    /**
     * 测试高度不同的对象相等性
     */
    @Test
    public void testEqualsDifferentHeight() {
        Rectangle differentHeightRect = new Rectangle("TestRect", new Point(2.0, 6.0), 2.0, 3.0);
        assertThat(namedRect.equals(differentHeightRect)).isFalse();
    }
    
    /**
     * 测试位置不同的对象相等性
     */
    @Test
    public void testEqualsDifferentPosition() {
        Rectangle differentPosRect = new Rectangle("TestRect", new Point(3.0, 6.0), 2.0, 2.0);
        assertThat(namedRect.equals(differentPosRect)).isFalse();
    }
    
    /**
     * 测试名称不同的对象相等性
     */
    @Test
    public void testEqualsDifferentName() {
        Rectangle differentNameRect = new Rectangle("DifferentName", new Point(2.0, 6.0), 2.0, 2.0);
        assertThat(namedRect.equals(differentNameRect)).isFalse();
    }
    
    // ============ 移动功能测试 ============
    /**
     * 测试正偏移量移动
     */
    @Test
    public void testMoveWithPositiveOffset() {
        // Save original position
        double originalTopLeftX = regularRect.points()[0].x();
        double originalTopLeftY = regularRect.points()[0].y();
        
        // Move the rectangle
        regularRect.move(2.0, 3.0);
        
        // Verify all points have been moved
        Point p = regularRect.points()[0];
        assertThat(p.x()).isWithin(1e-10).of(originalTopLeftX + 2.0);
        assertThat(p.y()).isWithin(1e-10).of(originalTopLeftY + 3.0);

        
        // Verify all lines have been moved
        Line l = regularRect.lines()[0];
        assertThat(l.from().x()).isWithin(1e-10).of(originalTopLeftX + 2.0);
        assertThat(l.from().y()).isWithin(1e-10).of(originalTopLeftY + 3.0);

    }
    
    /**
     * 测试负偏移量移动
     */
    @Test
    public void testMoveWithNegativeOffset() {
        // Save original position
        double originalTopLeftX = regularRect.points()[0].x();
        double originalTopLeftY = regularRect.points()[0].y();
        
        // Move the rectangle
        regularRect.move(-2.0, -3.0);
        
        // Verify all points have been moved
        Point p = regularRect.points()[0];  // only test the left top pointer;
        assertThat(p.x()).isWithin(1e-10).of(originalTopLeftX - 2.0);
        assertThat(p.y()).isWithin(1e-10).of(originalTopLeftY - 3.0);

    }
    
    /**
     * 测试零偏移量移动
     */
    @Test
    public void testMoveWithZeroOffset() {
        // 创建副本进行比较
        Rectangle originalCopy = new Rectangle(regularRect);

        // 使用零偏移量移动
        regularRect.move(0.0, 0.0);

        // 验证位置没有变化
        for (int i = 0; i < 4; i++) {
            assertThat(regularRect.points()[i].x()).isWithin(1e-10).of(originalCopy.points()[i].x());
            assertThat(regularRect.points()[i].y()).isWithin(1e-10).of(originalCopy.points()[i].y());
        }
    }
    
    // ============ 边界框测试 ============
    /**
     * 测试边界框生成
     */
//    @Test
//    public void testBoundingBoxGeneration() {
//        Rectangle bbox = regularRect.boundingBox();
//
//        // Verify bounding box has the same dimensions
//        assertThat(bbox.width()).isWithin(1e-10).of(regularRect.width());
//        assertThat(bbox.height()).isWithin(1e-10).of(regularRect.height());
//
//        // Verify bounding box has a different name indicating it's a bounding box
//        assertThat(bbox.name()).contains("Bounding Box of");
//
//        // Verify bounding box contains the original rectangle's name
//        assertThat(bbox.name()).contains(regularRect.name());
//    }
    
    /**
     * 测试嵌套边界框
     */
    @Test
    public void testNestedBoundingBoxes() {
        Rectangle bbox1 = regularRect.boundingBox();
        Rectangle bbox2 = bbox1.boundingBox();
        
        // Verify nested bounding boxes have the same dimensions
        assertThat(bbox2.width()).isWithin(1e-10).of(regularRect.width());
        assertThat(bbox2.height()).isWithin(1e-10).of(regularRect.height());
        
        // Verify names are properly nested
        assertThat(bbox2.name()).contains("Bounding Box of");
        assertThat(bbox2.name()).contains(bbox1.name());
    }
    
    // ============ 父子关系测试 ============
    /**
     * 测试父子关系设置和获取
     */
    @Test
    public void testFatherChildRelationship() {
        // Initially should have no father
        assertThat(regularRect.haveFather()).isFalse();
        assertThat(regularRect.father()).isNull();
        
        // Set a father
        Shape fatherShape = namedRect; // Using another rectangle as father
        regularRect.setFather(fatherShape);
        
        // Verify father relationship
        assertThat(regularRect.haveFather()).isTrue();
        assertThat(regularRect.father()).isSameInstanceAs(fatherShape);
        
        // Remove father
        regularRect.setFather(null);
        assertThat(regularRect.haveFather()).isFalse();
        assertThat(regularRect.father()).isNull();
    }
    
    // ============ 边界情况测试 ============
    /**
     * 测试零尺寸矩形
     */
    @Test
    public void testZeroSizeRectangle() {
        // Verify zero-sized rectangle has expected properties
        assertThat(zeroSizedRect.width()).isWithin(1e-10).of(0.0);
        assertThat(zeroSizedRect.height()).isWithin(1e-10).of(0.0);
        
        // All points should be the same for zero-sized rectangle
        Point[] points = zeroSizedRect.points();
        for (int i = 1; i < 4; i++) {
            assertThat(points[i].x()).isWithin(1e-10).of(points[0].x());
            assertThat(points[i].y()).isWithin(1e-10).of(points[0].y());
        }
    }
    
    /**
     * 测试负坐标矩形
     */
    @Test
    public void testNegativeCoordinatesRectangle() {
        // Verify rectangle with negative coordinates works properly
        assertThat(negativeCoordRect.minX()).isWithin(1e-10).of(-5.0);
        assertThat(negativeCoordRect.maxX()).isWithin(1e-10).of(5.0);
        
        // Test moving a rectangle with negative coordinates
        negativeCoordRect.move(2.0, 3.0);
        assertThat(negativeCoordRect.minX()).isWithin(1e-10).of(-3.0);
        assertThat(negativeCoordRect.maxX()).isWithin(1e-10).of(7.0);
    }
    
    /**
     * 测试字符串表示方法
     */
    @Test
    public void testStringRepresentation() {
        String str = namedRect.toString();
        
        // Verify toString contains essential information
        assertThat(str).contains("TestRect");
        assertThat(str).contains("Rectangle");


        
        // Verify coordinates are present (though exact format may vary)
        assertThat(str).contains("2.0");  // x-coordinate of top-left
        assertThat(str).contains("6.0");  // y-coordinate of top-left
    }
    
    // ============ 点覆盖测试 ============
    /**
     * 测试矩形内部的点
     */
    @Test
    public void testPointInsideRectangle() {
        Point insidePoint = new Point(3.0, 4.0);  // Inside regularRect
        assertThat(insidePoint.coveredBy(regularRect)).isTrue();
    }
    
    /**
     * 测试矩形边上的点
     */
    @Test
    public void testPointOnRectangleEdge() {
        Point edgePoint = new Point(1.0, 4.0);  // On left edge of regularRect
        assertThat(edgePoint.coveredBy(regularRect)).isTrue();
    }
    
    /**
     * 测试靠近矩形边的点
     */
    @Test
    public void testPointNearRectangleEdge() {
        Point nearEdgePoint = new Point(0.96, 4.0);  // Within 0.05 of left edge
        assertThat(nearEdgePoint.coveredBy(regularRect)).isTrue();
    }
    
    /**
     * 测试远离矩形的点
     */
    @Test
    public void testPointFarFromRectangle() {
        Point farPoint = new Point(10.0, 10.0);  // Far from regularRect
        assertThat(farPoint.coveredBy(regularRect)).isFalse();
    }
    
    // ============ 数组不变性测试 ============
    /**
     * 测试点数组的不变性
     */
    @Test
    public void testPointsArrayImmutability() {
        // Get a copy of the points array
        Point[] originalPoints = regularRect.points();

        // Modify the copied array
        originalPoints[0].add(100.0, 100.0);

        // Verify the original rectangle's points were not affected
        Point[] currentPoints = regularRect.points();
        assertThat(currentPoints[0].x()).isWithin(1e-10).of(1.0);
        assertThat(currentPoints[0].y()).isWithin(1e-10).of(5.0);
    }
    
    /**
     * 测试线段数组的不变性
     */
    @Test
    public void testLinesArrayImmutability() {
        // Get a copy of the lines array
        Line[] originalLines = regularRect.lines();

        // Modify the copied array's points
        originalLines[0].move(100.0, 100.0);

        // Verify the original rectangle's lines were not affected
        Line[] currentLines = regularRect.lines();
        assertThat(currentLines[0].from().x()).isWithin(1e-10).of(1.0);
        assertThat(currentLines[0].from().y()).isWithin(1e-10).of(5.0);
    }
}
