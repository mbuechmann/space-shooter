package net.littlecoder.core;

import playn.core.Surface;

/*
  TODO: Change numbers to use milliseconds and speed in pixels per second
 */

class Ship {

    private static final float MAX_SPEED = 150;

    private float x;
    private float y;
    private float vx;
    private float vy;
    private float rot;

    private float areaWidth;
    private float areaHeight;

    // Define the shape
    private static Point pointTop = new Point(0f, -20f);
    private static Point pointRight = new Point(5f, -5f);
    private static Point pointBottom = new Point(0f, -10);
    private static Point pointLeft = new Point(-5f, -5f);
    private static Line[] lines = {
	new Line(pointTop, pointRight),
	new Line(pointRight, pointBottom),
	new Line(pointBottom, pointLeft),
	new Line(pointLeft, pointTop)
    };
    
    public Ship (float areaWidth, float areaHeight) {
	this.areaWidth = areaWidth;
	this.areaHeight = areaHeight;

	x = areaWidth / 2f;
	y = areaHeight / 2f;
	vx = 0.0f;
	vy = 0.0f;
	rot = 0.0f;
    }

    public void paint(Surface surface) {
	surface.setFillColor(0xFFFFFF);

	for (Line l : lines)
	    l.rotate(rot).translate(x, y).paint(surface);
    }

    public void rotate(float angle) {
	rot += angle;
	rot %= 360.0f;
    }

    public void move(float delta) {
	x += vx * delta;
	y += vy * delta;
    }

    public void accelerate(float delta) {
	vx += Math.sin(rot) * delta;
	vy -= Math.cos(rot) * delta;
    }

    public void update(float delta) {
	limitVelocity();
	updatePosition(delta);
    }

    private void limitVelocity() {
	float v = (float)Math.sqrt(vx * vx + vy * vy);
	if (v > MAX_SPEED) {
	    float factor = v / MAX_SPEED;
	    vx /= factor;
	    vy /= factor;
	}
    }

    private void updatePosition(float delta) {
	x += vx * (delta / 1000f);
	y += vy * (delta / 1000f);

	while (x < 0)
	    x += areaWidth;
	while (x > areaWidth)
	    x -= areaWidth;
	while (y < 0)
	    y += areaHeight;
	while (y > areaHeight)
	    y -= areaHeight;
    }

}
