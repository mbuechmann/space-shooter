package net.littlecoder.core;

import java.util.Random;

import playn.core.Surface;

class Remains {

    private Line line;
    private float x;
    private float y;
    private float vx;
    private float vy;
    private float rot;
    private float vrot;

    private Surface surface;

    public Remains(Line line, Surface surface, float x, float y, float vx, float vy, float rot) {
	this.line = line;
	this.surface = surface;
	this.x = x;
	this.y = y;
	this.vx = vx;
	this.vy = vy;

	Random random = new Random();
	this.rot = rot;
	vrot = (float)Math.toRadians(random.nextFloat() * 10f);
    }

    public void paint(float alpha) {
	line.transform(rot, x, y).paint(surface);
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

	rot += vrot;
    }

}
