package clevis.view;

import clevis.system.Data;
import clevis.util.shape.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import static clevis.sql.Geometry.EPS;

public class DrawingPanel extends JPanel {
    public static final float SHAPE_STROKE_WIDTH = .05f;
    public static final Font TEXT_FONT = new Font("SansSerif", Font.BOLD, 14);
    private final Data data;

    // 平移偏移（单位：逻辑坐标）
    private double offsetX = 0;
    private double offsetY = 0;
    private final Point lastDrag = new Point();

    // 网格设置（固定）
    private static final double MAJOR_GRID = 5.0;  // 大格
    private static final double MINOR_GRID = 1.0;  // 小格

    /**
     * Construction
     * @param data      data storage
     */
    public DrawingPanel(Data data) {
        this.data = data;
        setBackground(Color.WHITE);

        // 只保留拖拽平移
        MouseAdapter drag = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastDrag.setLocation(e.getX(), e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                double dx = (e.getX() - lastDrag.getX());
                double dy = (e.getY() - lastDrag.getY());
                offsetX += dx / getScale();  // 像素转逻辑单位
                offsetY -= dy / getScale();
                lastDrag.setLocation(e.getX(), e.getY());
                repaint();
            }
        };
        addMouseListener(drag);
        addMouseMotionListener(drag);
    }

    // 像素与逻辑坐标转换（固定缩放：1 单位 ≈ 50 像素）
    private double getScale() {
        return 50.0;
    }

    private double toScreenX(double x) {
        return getWidth() / 2.0 + (x + offsetX) * getScale();
    }

    private double toScreenY(double y) {
        return getHeight() / 2.0 - (y + offsetY) * getScale();  // Y 轴向上
    }

    private double fromScreenX(double px) {
        return (px - getWidth() / 2.0) / getScale() - offsetX;
    }

    private double fromScreenY(double py) {
        return -(py - getHeight() / 2.0) / getScale() - offsetY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        double scale = getScale();

        // 当前视口范围（逻辑坐标）
        double left   = fromScreenX(0);
        double right  = fromScreenX(getWidth());
        double top    = fromScreenY(0);
        double bottom = fromScreenY(getHeight());

        // 1. 绘制背景网格
        drawGrid(g2, left, right, top, bottom, scale);

        // 2. 绘制主坐标轴（始终绘制，即使在外面）
        drawAxes(g2, scale);

        // 3. 绘制边缘刻度（四边都有）
        drawTickLabels(g2, left, right, top, bottom, scale);

        // 4. 绘制原点标记
        drawOrigin(g2);

        // 5. 绘制所有形状（后画的在上）
        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(SHAPE_STROKE_WIDTH));

        // ===== 关键修改：保存当前变换，应用我们自己的坐标系，再恢复 =====
        AffineTransform oldTransform = g2.getTransform();

        g2.translate(getWidth() / 2.0, getHeight() / 2.0);
        g2.scale(getScale(), -getScale());  // 注意 Y 轴翻转
        g2.translate(offsetX, offsetY);

        for (int i = data.size() - 1; i >= 0; i--) {
            Shape s = data.get(i);
            if (!s.haveFather()) {
                s.draw(g2);  // 现在你原来的 draw() 完全兼容！
            }
        }

        g2.setTransform(oldTransform);  // 恢复变换，网格和刻度不受影响

        g2.dispose();
    }

    private void drawGrid(Graphics2D g2, double left, double right, double top, double bottom, double scale) {
        // 计算网格起始点
        double xStart = Math.floor(left / MINOR_GRID) * MINOR_GRID;
        double yStart = Math.floor(bottom / MINOR_GRID) * MINOR_GRID;

        g2.setStroke(new BasicStroke(1f));
        for (double x = xStart; x <= right; x += MINOR_GRID) {
            double sx = toScreenX(x);
            if (Math.abs(x % MAJOR_GRID) < EPS) {
                g2.setColor(new Color(200, 200, 200));
            } else {
                g2.setColor(new Color(240, 240, 240));
            }
            g2.draw(new Line2D.Double(sx, 0, sx, getHeight()));
        }

        for (double y = yStart; y <= top; y += MINOR_GRID) {
            double sy = toScreenY(y);
            if (Math.abs(y % MAJOR_GRID) < EPS) {
                g2.setColor(new Color(200, 200, 200));
            } else {
                g2.setColor(new Color(240, 240, 240));
            }
            g2.draw(new Line2D.Double(0, sy, getWidth(), sy));
        }
    }

    private void drawAxes(Graphics2D g2, double scale) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2f));

        double cx = toScreenX(0);
        double cy = toScreenY(0);

        // X 轴
        g2.draw(new Line2D.Double(0, cy, getWidth(), cy));
        // Y 轴
        g2.draw(new Line2D.Double(cx, 0, cx, getHeight()));

        // 箭头
        drawArrow(g2, getWidth() - 10, cy, true);   // X 轴箭头
        drawArrow(g2, cx, 10, false);               // Y 轴箭头

        // 轴标签
        g2.setFont(TEXT_FONT);
        g2.drawString("X", getWidth() - 25, (int) (cy - 8));
        g2.drawString("Y", (int) (cx + 8), 20);
    }

    private void drawArrow(Graphics2D g2, double x, double y, boolean horizontal) {
        Polygon arrow = new Polygon();
        if (horizontal) {
            arrow.addPoint((int)x, (int)y);
            arrow.addPoint((int)x - 10, (int)y - 6);
            arrow.addPoint((int)x - 10, (int)y + 6);
        } else {
            arrow.addPoint((int)x, (int)y);
            arrow.addPoint((int)x - 6, (int)y + 10);
            arrow.addPoint((int)x + 6, (int)y + 10);
        }
        g2.fill(arrow);
    }

    private void drawTickLabels(Graphics2D g2, double left, double right, double top, double bottom, double scale) {
        g2.setColor(Color.DARK_GRAY);
        g2.setFont(new Font("Consolas", Font.PLAIN, 14));

        double x0 = Math.ceil(left / MAJOR_GRID) * MAJOR_GRID;
        double y0 = Math.ceil(bottom / MAJOR_GRID) * MAJOR_GRID;

        // X 轴刻度（底部）
        for (double x = x0; x <= right; x += MAJOR_GRID) {
            if (x == 0) continue;
            double sx = toScreenX(x);
            g2.draw(new Line2D.Double(sx, getHeight() - 10, sx, getHeight()));
            String label = String.format("%.0f", x);
            g2.drawString(label, (float)(sx - g2.getFontMetrics().stringWidth(label) / 2.0), getHeight() - 15);
        }

        // Y 轴刻度（左侧）
        for (double y = y0; y <= top; y += MAJOR_GRID) {
            if (y == 0) continue;
            double sy = toScreenY(y);
            g2.draw(new Line2D.Double(0, sy, 10, sy));
            String label = String.format("%.0f", y);
            g2.drawString(label, 14, (float)sy + 4);
        }
    }

    private void drawOrigin(Graphics2D g2) {
        double sx = toScreenX(0);
        double sy = toScreenY(0);

        if (sx >= 0 && sx <= getWidth() && sy >= 0 && sy <= getHeight()) {
            g2.setColor(Color.RED);
            g2.fillOval((int)sx - 5, (int)sy - 5, 10, 10);
            g2.setColor(Color.BLACK);
            g2.drawOval((int)sx - 5, (int)sy - 5, 10, 10);
            g2.drawString("(0,0)", (float)sx + 8, (float)sy - 8);
        }
    }
}