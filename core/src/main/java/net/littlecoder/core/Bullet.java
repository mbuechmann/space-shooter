package net.littlecoder.core;

import playn.core.Surface;

class Bullet {

    // speed in pixels per second, time to live in milliseconds
    private static final float SPEED = 450f;
    private static final float TTL = 3000f;

    private float x;
    private float y;
    private float vx;
    private float vy;
    private float distanceX = 0f;
    private float distanceY = 0f;

    private float age = 0f;
    private boolean dead = false;

    private Surface surface;
    
    public Bullet(Ship ship) {
	x = ship.tipX();
	y = ship.tipY();
	vx = -(float)Math.sin(ship.rot()) * SPEED;
	vy = -(float)Math.cos(ship.rot()) * SPEED;
	surface = ship.surface();

	SoundPlayer.playSound("Lazer");
    }

    public void update(float delta) {
	// TODO: This is not DRY, see Ship
	distanceX = vx * (delta / 1000f);
	distanceY = vy * (delta / 1000f);

	x += distanceX;
	y += distanceY;

	while (x < 0)
	    x += surface.width();
	while (x > surface.width())
	    x -= surface.width();
	while (y < 0)
	    y += surface.height();
	while (y > surface.height())
	    y -= surface.height();

	age += delta;
    }

    public void paint(float alpha) {
	surface.fillRect(x, y, 2f, 2f);
    }

    public void die() {
	dead = true;
    }

    public boolean isDead() {
	return dead || age > TTL;
    }

    public float x() {
	return x;
    }

    public float y() {
	return y;
    }

    public float lastX() {
	return x - distanceX;
    }

    public float lastY() {
	return y - distanceY;
    }

}
