package net.littlecoder.core;

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

    public Remains(Ship ship, Line line) {
	this.line = line;
    }

    public void paint(float alpha) {
	line.paint(surface);
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
