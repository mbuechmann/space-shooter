package net.littlecoder.core;

import playn.core.Surface;

class Line {
    
    private Point p0;
    private Point p1;

    public Line(Point p0, Point p1) {
	this.p0 = p0;
	this.p1 = p1;
    }

    public void paint(Surface surface) {
	surface.drawLine(p0.x(), p0.y(), p1.x(), p1.y(), 1f);
    }

    // TODO: Optimize memory usage
    public Line rotate(float angle) {
	return new Line(p0.rotate(angle), p1.rotate(angle));
    }

    // TODO: Optimize memory usage
    public Line translate(float x, float y) {
	return new Line(p0.translate(x, y), p1.translate(x, y));
    }

}