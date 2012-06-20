package net.littlecoder.core;

class Point {
    
    private float x;
    private float y;

    public Point(float x, float y) {
	this.x = x;
	this.y = y;
    }

    // TODO: Optimize memory usage
    public Point rotate(float angle) {
	// TODO: Does this work?
	float newX = (float)(Math.cos(angle) * x) + (float)(Math.sin(angle) * y);
	float newY = (float)(Math.sin(angle) * x) + (float)(Math.cos(angle) * y);

	return new Point(newX, newY);
    }

    // TODO: Optimize memory usage
    public Point translate(float x, float y) {
	float newX = this.x + x;
	float newY = this.y + y;

	return new Point(newX, newY);
    }

    public float x() {
	return x;
    }

    public float y() {
	return y;
    }

}
