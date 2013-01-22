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

    public GameElement() {
    }

    protected void updatePosition(float delta, int width, int height) {
        x += vx * (delta / 1000f);
        y += vy * (delta / 1000f);

        while (x < 0)
            x += width;
        while (x > width)
            x -= width;
        while (y < 0)
            y += height;
        while (y > height)
            y -= height;

        rot += (delta / 1000f) * vrot;
    }

}
