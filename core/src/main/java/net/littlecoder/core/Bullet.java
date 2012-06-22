package net.littlecoder.core;

import playn.core.Surface;

class Bullet {

    // speed in pixels per second, time to live in milliseconds
    private static final float SPEED = 400f;
    private static final float TTL = 3000f;

    private float x;
    private float y;
    private float vx;
    private float vy;

    private float age = 0f;

    private Surface surface;
    
    public Bullet(Ship ship) {
	x = ship.tipX();
	y = ship.tipY();
	vx = -(float)Math.sin(ship.rot()) * SPEED;
	vy = -(float)Math.cos(ship.rot()) * SPEED;
	surface = ship.surface();
    }

    public void update(float delta) {
	// TODO: This is not DRY, see Ship
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

	age += delta;
    }

    public void paint(float alpha) {
	surface.fillRect(x, y, 2f, 2f);
    }

    public boolean isDead() {
	return age > TTL;
    }

}
