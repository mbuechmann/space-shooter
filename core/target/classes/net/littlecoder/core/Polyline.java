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
    
    public Polyline setRotation(float angle) {
	for (Point p : points)
	    p.setRotation(angle);

	return this;
    }

    public Polyline setTranslation(float x, float y) {
	for (Point p : points)
	    p.setTranslation(x, y);

	return this;
    }

}
