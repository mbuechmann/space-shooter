package net.littlecoder.core;

import playn.core.Surface;

class Ship {

    // Speed in pixel/second,
    // accelaeration in pixel/second^2,
    // rotation in radians per second
    private static final float MAX_SPEED = 400f;
    private static final float ACCELERATION = 500f;
    private static final float ROTATION_SPEED = (float)Math.toRadians(180f);

    private float x;
    private float y;
    private float vx;
    private float vy;
    // rotation from 0 to 360, where 0 is pointing up
    private float rot;

    private Surface surface;

    private boolean accelerating;
    private boolean steeringRight;
    private boolean steeringLeft;

    // Define the shape
    private static Point pointTop = new Point(0f, -10f);
    private static Point pointRight = new Point(5f, 5f);
    private static Point pointBottom = new Point(0f, 0);
    private static Point pointLeft = new Point(-5f, 5f);
    private static Line[] lines = {
	new Line(pointTop, pointRight),
	new Line(pointRight, pointBottom),
	new Line(pointBottom, pointLeft),
	new Line(pointLeft, pointTop)
    };
    
    public Ship (Surface surface) {
	this.surface = surface;

	x = surface.width() / 2f;
	y = surface.height() / 2f;
	vx = 0.0f;
	vy = 0.0f;
	rot = 0.0f;

	accelerating = false;
	steeringRight = false;
	steeringLeft = false;
    }

    public float tipX() {
	return pointTop.x();
    }

    public float tipY() {
	return pointTop.y();
    }

    public float rot() {
	return rot;
    }

    public Surface surface() {
	return surface;
    }

    public void paint(Surface surface) {
	surface.setFillColor(0xFFFFFF);

	for (Line l : lines)
	    l.setRotation(rot).setTranslation(x, y).paint(surface);
    }

    public void accelerate(boolean on) {
	accelerating = on;
    }

    public void steerRight(boolean on) {
	steeringRight = on;
    }

    public void steerLeft(boolean on) {
	steeringLeft = on;
    }

    public void update(float delta) {
	regardPiloting(delta);
	limitVelocity();
	updatePosition(delta);
    }

    private void regardPiloting(float delta) {
	if (accelerating) {
	    vx -= Math.sin(rot) * delta/1000f * ACCELERATION;
	    vy -= Math.cos(rot) * delta/1000f * ACCELERATION;
	}

	if (steeringRight != steeringLeft) {
	    if (steeringRight)
		rot -= (delta / 1000f) * ROTATION_SPEED;
	    if (steeringLeft)
		rot += (delta / 1000f) * ROTATION_SPEED;
	}
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
	    x += surface.width();
	while (x > surface.width())
	    x -= surface.width();
	while (y < 0)
	    y += surface.height();
	while (y > surface.height())
	    y -= surface.height();
    }

}
