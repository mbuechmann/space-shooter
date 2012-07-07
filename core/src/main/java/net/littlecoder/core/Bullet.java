package net.littlecoder.core;

class Bullet extends GameElement {

    // speed in pixels per second, time to live in milliseconds
    private static final float SPEED = 450f;
    private static final float TTL = 3000f;

    private float lastX;
    private float lastY;

    private float age = 0f;
    private boolean dead = false;

    public Bullet(Ship ship) {
	super(ship.surface);
	x = ship.tipX();
	y = ship.tipY();
	lastX = x;
	lastY = y;
	vx = -(float)Math.sin(ship.rot()) * SPEED;
	vy = -(float)Math.cos(ship.rot()) * SPEED;

	SoundPlayer.playSound("Lazer");
    }

    public void update(float delta) {
	lastX = x;
	lastY = y;
	updatePosition(delta);
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
	return lastX;
    }

    public float lastY() {
	return lastY;
    }

}
