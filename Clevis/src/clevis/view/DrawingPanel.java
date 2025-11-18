package clevis.view;

import clevis.system.Data;
import clevis.model.shape.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import static clevis.sql.Geometry.EPS;

/**
 * Drawing Panel
 */
public class DrawingPanel extends JPanel {
    private final float AXES_STROKE = 2f;
    private final float SHAPE_STROKE_WIDTH = .05f;
    private final int AXES_X_XCOORDI = getWidth() - 25;
    private final int AXES_X_YCOORDI = (int) toScreenY(0) - 8;
    private final int AXES_Y_XCOORDI = (int) toScreenX(0) + 8;
    private final int AXES_Y_YCOORDI = 20;
    private final Font TEXT_FONT = new Font("SansSerif", Font.BOLD, 14);
    private final int TEXT_SIZE = 14;
    private final double SCALE = 50.0;
    private final Data data;

    // 平移偏移（单位：逻辑坐标）
    private double offsetX = 0;
    private double offsetY = 0;
    private final Point lastDrag = new Point();

    private static final double MAJOR_GRID = 5.0;
    private static final double MINOR_GRID = 1.0;

    /**
     * Construction
     * @param data      data storage
     */
    public DrawingPanel(Data data) {
        this.data = data;
        setBackground(Color.WHITE);

        // 只保留拖拽平移
        MouseAdapter drag = new MouseAdapter();
        addMouseListener(drag);
        addMouseMotionListener(drag);
    }

    private double toScreenX(double x) {
        return (double) getWidth() / 2 + (x + offsetX) * SCALE;
    }

    private double toScreenY(double y) {
        return (double) getHeight() / 2 - (y + offsetY) * SCALE;  // Y 轴向上
    }

    private double fromScreenX(double px) {
        return (px - (double) getWidth() / 2) / SCALE - offsetX;
    }

    private double fromScreenY(double py) {
        return -(py - (double) getHeight() / 2) / SCALE - offsetY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        double left   = fromScreenX(0);
        double right  = fromScreenX(getWidth());
        double top    = fromScreenY(0);
        double bottom = fromScreenY(getHeight());

        drawGrid(g2, left, right, top, bottom);

        drawAxes(g2);

        drawTickLabels(g2, left, right, top, bottom);

        drawOrigin(g2);

        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(SHAPE_STROKE_WIDTH));

        AffineTransform oldTransform = g2.getTransform();

        g2.translate((double)getWidth() / 2, (double)getHeight() / 2);
        g2.scale(SCALE, -SCALE);
        g2.translate(offsetX, offsetY);

        for (int i = data.size() - 1; i >= 0; i--) {
            Shape s = data.get(i);
            if (!s.haveFather()) {
                s.draw(g2);
            }
        }

        g2.setTransform(oldTransform);

        g2.dispose();
    }

    private void drawGrid(Graphics2D g2, double left, double right, double top, double bottom) {
        double xStart = Math.floor(left / MINOR_GRID) * MINOR_GRID;
        double yStart = Math.floor(bottom / MINOR_GRID) * MINOR_GRID;

        g2.setStroke(new BasicStroke(1f));
        for (double x = xStart; x <= right; x += MINOR_GRID) {
            double sx = toScreenX(x);
            g2.setColor(Color.GRAY);
            g2.draw(new Line2D.Double(sx, 0, sx, getHeight()));
        }

        for (double y = yStart; y <= top; y += MINOR_GRID) {
            double sy = toScreenY(y);
            g2.setColor(Color.GRAY);
            g2.draw(new Line2D.Double(0, sy, getWidth(), sy));
        }
    }

    private void drawAxes(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(AXES_STROKE));

        double cx = toScreenX(0);
        double cy = toScreenY(0);

        g2.draw(new Line2D.Double(0, cy, getWidth(), cy));

        g2.draw(new Line2D.Double(cx, 0, cx, getHeight()));

        drawArrow(g2, getWidth() - 10, cy, true);
        drawArrow(g2, cx, 10, false);

        g2.setFont(TEXT_FONT);
        g2.drawString("X", AXES_X_XCOORDI, AXES_X_YCOORDI);
        g2.drawString("Y", AXES_Y_XCOORDI, AXES_Y_YCOORDI);
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

    private void drawTickLabels(Graphics2D g2, double left, double right, double top, double bottom) {
        g2.setColor(Color.DARK_GRAY);
        g2.setFont(new Font("Consolas", Font.PLAIN, TEXT_SIZE));

        double x0 = Math.ceil(left / MAJOR_GRID) * MAJOR_GRID;
        double y0 = Math.ceil(bottom / MAJOR_GRID) * MAJOR_GRID;

        // X 轴刻度（底部）
        for (double x = x0; x <= right; x += MAJOR_GRID) {
            if (x == 0) continue;
            double sx = toScreenX(x);
            g2.draw(new Line2D.Double(sx, getHeight() - 10, sx, getHeight()));
            String label = String.format("%.0f", x);
            g2.drawString(label, (float)(sx - (double) g2.getFontMetrics().stringWidth(label) / 2), getHeight() - 15);
        }

        // Y 轴刻度（左侧）
        for (double y = y0; y <= top; y += MAJOR_GRID) {
            if (y == 0) continue;
            double sy = toScreenY(y);
            g2.draw(new Line2D.Double(0, sy, 10, sy));
            String label = String.format("%.0f", y);
            g2.drawString(label, TEXT_SIZE, (float)sy + 4);
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

    private class MouseAdapter extends java.awt.event.MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            lastDrag.setLocation(e.getX(), e.getY());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            double dx = (e.getX() - lastDrag.getX());
            double dy = (e.getY() - lastDrag.getY());
            offsetX += dx / SCALE;  // 像素转逻辑单位
            offsetY -= dy / SCALE;
            lastDrag.setLocation(e.getX(), e.getY());
            repaint();
        }
    }
}