package net.littlecoder.core;

import java.util.Random;

import playn.core.Surface;

class Remains extends GameElement {

    private static float MAX_ROTATION_SPEED = 30f;

    private Line line;

    public Remains(Line line, float x, float y, float vx, float vy, float rot) {
        super();
        this.line = line;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;

        Random random = new Random();
        this.rot = rot;
        vrot = (float) Math.toRadians(random.nextFloat() * 10f) * MAX_ROTATION_SPEED;
    }

    public void paint(Surface surface) {
        line.transform(rot, x, y).paint(surface);
    }

    public void update(float delta) {
        updatePosition(delta);
    }

}
