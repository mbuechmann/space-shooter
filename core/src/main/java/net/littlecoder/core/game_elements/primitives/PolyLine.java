package net.littlecoder.core.game_elements.primitives;

import playn.core.Surface;

public class PolyLine {

    private Point[] points;
    private Line[] lines;

    public PolyLine(Point[] points) {
        this.points = points;

        lines = new Line[points.length];
        for (int i = 0; i < points.length - 1; i++)
            lines[i] = new Line(points[i], points[i + 1]);
        if (points.length > 2)
            lines[points.length - 1] = new Line(points[points.length - 1], points[0]);
    }

    public void paint(Surface surface) {
        for (Line l : lines)
            l.paint(surface);
    }

    public PolyLine transform(float angle, float translationX, float translationY) {
        for (Point p : points)
            p.transform(angle, translationX, translationY);
        for (Line l : lines)
            l.recalc();

        return this;
    }

    public boolean intersectsLine(double X1, double Y1, double X2, double Y2) {
        for (Line l : lines)
            if (l.intersectsLine(X1, Y1, X2, Y2))
                return true;

        return false;
    }

    public boolean intersectsPolyline(PolyLine polyLine) {
        for (Line l : polyLine.lines)
            if (intersectsLine(l.x1, l.y1, l.x2, l.y2))
                return true;

        return false;
    }

    public PolyLine copy() {
        Point[] clonedPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++)
            clonedPoints[i] = (Point) points[i].clone();
        return new PolyLine(clonedPoints);
    }

}
