package net.littlecoder.core;

import playn.core.Surface;

class Polyline {

    private Point[] points;
    private Line[] lines;

    public Polyline(Point[] points) {
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

    public Polyline transform(float angle, float translationX, float translationY) {
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

    public Polyline clone() {
	return new Polyline(points.clone());
    }

}
