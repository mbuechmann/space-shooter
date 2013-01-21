package net.littlecoder.core;

import playn.core.Surface;

class GameElement {

    protected float x = 0f;
    protected float y = 0f;
    protected float vx = 0f;
    protected float vy = 0f;
    // rotation from 0 to 360, where 0 is pointing up
    protected float rot = 0f;
    protected float vrot = 0f;

    protected Surface surface;

    public GameElement(Surface surface) {
        this.surface = surface;
    }

    protected void updatePosition(float delta) {
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

        rot += (delta / 1000f) * vrot;
    }

}
