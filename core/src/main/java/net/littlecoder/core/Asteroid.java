package net.littlecoder.core;

import java.util.Random;

import playn.core.Surface;

class Asteroid {

    private static final float SPEED = 100f;
    private static final float MIN_ROTATION_SPEED = -90f;
    private static final float MAX_ROTATION_SPEED = 90f;

    private static final Point[][][] POINTS = {
	{
	    {new Point(-10f, 0f), new Point(-5f, 1f), new Point(-3f, 8f), new Point(5f, 8f), new Point(8f, -2f), new Point(1f, -10f), new Point(-6f, -9f)},
	    {new Point(-10f, 8f), new Point(-7f, 10f), new Point(5f, 5f), new Point(9f, 1f), new Point(3f, -8f), new Point(-5f, -5f), new Point( -7f, 4f)},
	    {new Point(-6f, 0f), new Point(-8f, 4f), new Point(-3f, 8f), new Point(0f, 5f), new Point(4f, 6f), new Point(6f, 2f), new Point(4f, -1f), new Point(2f, -8f), new Point(-5f, -7f)}
	}, {
	    {new Point(-9f, 0f), new Point(-15f, 5f), new Point(-13f, 9f), new Point(-5f, 15f), new Point(0f, 11f), new Point(5f, 14f), new Point(10f, 10f), new Point(15f, -2f), new Point(5f, -15f), new Point(-12f, -14f), new Point(-15f, -5f)},
	    {new Point(-14f, 5f), new Point(-6f, 14f), new Point(-1f, 10f), new Point(1f, 13f), new Point(5f, 10f), new Point(10f, 10f), new Point(14f, -3f), new Point(7f, -8f), new Point(0f, -11f), new Point(-4f, -13f), new Point(-12f, -12f), new Point(-10f, -3f)}
	}, {
	    {new Point(-18f, 0f), new Point(-15f, 10f), new Point(-3f, 12f), new Point(0f, 20f), new Point(7f, 16f), new Point(15f, 14f), new Point(20f, 0f), new Point(18f, -3f), new Point(15f, -10f), new Point(8f, -12f), new Point(0f, -15f), new Point(-2f, -12f), new Point(-12f, -10f)}
	}
    };
    private static final Polyline[][] POLYLINES = {
	{
	    new Polyline(POINTS[0][0]),
	    new Polyline(POINTS[0][1]),
	    new Polyline(POINTS[0][2])
	}, {
	    new Polyline(POINTS[1][0]),
	    new Polyline(POINTS[1][1])
	}, {
	    new Polyline(POINTS[2][0])
	}
    };
    private byte size;
    private float x;
    private float y;
    private float rot;
    private float vx;
    private float vy;
    private float vrot;

    private Surface surface;
    private Polyline polyline;

    private boolean dead = false;

    public Asteroid(byte size, Surface surface) {
	this.size = size;
	this.surface = surface;
	
	// Randomly initialize this Asteroid
	Random random = new Random();

	int r = random.nextInt(POLYLINES[size].length);
	polyline = POLYLINES[size][r].clone();

	do {
	    x = random.nextFloat() * surface.width();
	    y = random.nextFloat() * surface.height();
        } while (
	    x > (surface.width() / 3f) &&
	    x < (surface.width() / 3f * 2f) &&
	    y > (surface.height() / 3f) &&
	    y < (surface.height() / 3f * 2f)
	);

	float angle = random.nextFloat() * 2f * (float)Math.PI;
	vx = (float)Math.sin(angle) * SPEED;
	vy = (float)Math.cos(angle) * SPEED;

	rot = random.nextFloat() * 2f * (float)Math.PI;
	vrot = MIN_ROTATION_SPEED + random.nextFloat() * (MAX_ROTATION_SPEED - MIN_ROTATION_SPEED);
    }

    public void paint(float alpha) {
	polyline.transform(rot, x, y).paint(surface);
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

	rot += Math.toRadians(vrot * (delta / 1000f));
    }

    public boolean isCollidingWith(Bullet bullet) {
	return polyline.intersectsLine(
	    (double)bullet.x(),
	    (double)bullet.y(),
	    (double)bullet.lastX(),
	    (double)bullet.lastY()
        );
    }

    public boolean isCollidingWith(Ship ship) {
	return polyline.intersectsPolyline(ship.shipPolyline);
    }

    public void die() {
	dead = true;
    }

    public boolean isDead() {
	return dead;
    }

    public boolean canSpawnChilds() {
	return (size > 0);
    }

    public Asteroid spawnChild() {
	Asteroid a = null;

	if (size > 0) {
	    a = new Asteroid((byte)(size - 1), surface);
	    a.x = x;
	    a.y = y;
	}

	return a;
    }

}
