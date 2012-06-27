package net.littlecoder.core;

import java.util.Random;

import playn.core.Surface;

class Debris extends Asteroid {

    private static float TTL = 1000f;
    private float age = 0f;

    public Debris(Surface surface) {
	super(surface);
    }

    public void paint(float alpha) {
	surface.fillRect(x, y, 2f, 2f);
    }

    public void update(float delta) {
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

    public boolean isCollidingWith(Bullet bullet) {
	return false;
    }

    public boolean isCollidingWith(Ship ship) {
	return false;
    }

    public boolean isDead() {
	return (age >= TTL);
    }

    public Asteroid[] spawnChildren() {
	Asteroid[] res = {};
	return res;
    }

}
