package net.littlecoder.core;

import java.util.Random;

import playn.core.Sound;
import playn.core.Surface;

class Ship {

    // Speed in pixel/second,
    // accelaeration in pixel/second^2,
    // rotation in radians per second
    private static final float MAX_SPEED = 400f;
    private static final float ACCELERATION = 500f;
    private static final float ROTATION_SPEED = (float)Math.toRadians(180f);
    private static final float TTD = 3000f;

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
    private boolean dead = false;
    private float age = 0f;

    // The shape of the ship
    private static Point shipTop = new Point(0f, -10f);
    private static Point shipRight = new Point(5f, 5f);
    private static Point shipBottom = new Point(0f, 0);
    private static Point shipLeft = new Point(-5f, 5f);
    private static Point[] shipPoints = {shipTop, shipRight, shipBottom, shipLeft};
    public static Polyline shipPolyline = new Polyline(shipPoints);

    // The shape of the thruster
    private static Point[] thrusterPoints = {new Point(-3f, 2f), shipBottom, new Point(3f, 2f), new Point(0f, 7f)};
    private static Polyline thrusterPolyline = new Polyline(thrusterPoints);

    private Sound engineSound;
    private Sound dieSound;

    // The remains of the ship when it is destroyed
    private Remains[] remains;
    
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

	engineSound = SoundPlayer.getSound("Engines");
	engineSound.setLooping(true);
	dieSound = SoundPlayer.getSound("Die 2");
    }

    public float tipX() {
	return shipTop.x;
    }

    public float tipY() {
	return shipTop.y;
    }

    public float rot() {
	return rot;
    }

    public Surface surface() {
	return surface;
    }

    public void paint(float alpha) {
	if (!dead) {
	    surface.setFillColor(0xFFFFFF);
	    shipPolyline.transform(rot, x, y).paint(surface);
	    if (accelerating && !dead)
		thrusterPolyline.transform(rot, x, y).paint(surface);
	} else
	    for (Remains r : remains)
		r.paint(alpha);
    }

    public void accelerate(boolean on) {
	if (!dead) {
	    accelerating = on;
	    if (accelerating != engineSound.isPlaying()) {
		if (accelerating)
		    engineSound.play();
		else
		    engineSound.stop();
	    }
	}
	    
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
	progressDeath(delta);
	if (dead)
	    for (Remains r : remains)
		r.update(delta);
    }

    public void die() {
	initRemains();
	engineSound.stop();
	dieSound.play();
	dead = true;
    }

    public boolean isDead() {
	return (age >= TTD);
    }

    public boolean isDisabled() {
	return dead;
    }

    private void regardPiloting(float delta) {
	if (!dead) {
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

    private void progressDeath(float delta) {
	if (dead)
	    age += delta;
    }

    private void initRemains() {
	remains = new Remains[4];
	remains[0] = new Remains(new Line(shipTop, shipRight), surface, x, y, vx, vy, rot);
	remains[1] = new Remains(new Line(shipRight, shipBottom), surface, x, y, vx, vy, rot);
	remains[2] = new Remains(new Line(shipBottom, shipLeft), surface, x, y, vx, vy, rot);
	remains[3] = new Remains(new Line(shipLeft, shipTop), surface, x, y, vx, vy, rot);
    }

}
