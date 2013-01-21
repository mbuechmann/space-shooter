package net.littlecoder.core;

import playn.core.Surface;

import java.awt.geom.Line2D;

class Line extends Line2D.Float {

    private Point p0;
    private Point p1;

    public Line(Point p0, Point p1) {
        this.p0 = p0;
        this.p1 = p1;
        recalc();
    }

    public void paint(Surface surface) {
        surface.drawLine(x1, y1, x2, y2, 1f);
    }

    public Line transform(float angle, float translationX, float translationY) {
        p0.transform(angle, translationX, translationY);
        p1.transform(angle, translationX, translationY);
        recalc();
        return this;
    }

    public void recalc() {
        x1 = p0.x;
        y1 = p0.y;
        x2 = p1.x;
        y2 = p1.y;
    }

}