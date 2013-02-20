package net.littlecoder.core.game_elements;

import net.littlecoder.core.game_elements.primitives.Line;
import net.littlecoder.core.game_elements.primitives.Point;
import net.littlecoder.core.game_elements.primitives.PolyLine;
import net.littlecoder.core.util.SoundPlayer;
import playn.core.Sound;
import playn.core.Surface;

public class Ship extends GameElement {

    // Speed in pixel/second,
    // acceleration in pixel/second^2,
    // rotation in radians per second
    private static final float MAX_SPEED = 400f;
    private static final float ACCELERATION = 500f;
    private static final float ROTATION_SPEED = (float) Math.toRadians(180f);
    private static final float TTD = 3000f;

    private boolean accelerating = false;
    private boolean steeringRight = false;
    private boolean steeringLeft = false;
    private boolean dying = false;
    private float age;

    // The shape of the ship
    private static Point shipTop = new Point(0f, -10f);
    private static Point shipRight = new Point(5f, 5f);
    private static Point shipBottom = new Point(0f, 0);
    private static Point shipLeft = new Point(-5f, 5f);
    private static Point[] shipPoints = {shipTop, shipRight, shipBottom, shipLeft};
    public static PolyLine shipPolyLine = new PolyLine(shipPoints);

    // The shape of the thruster
    private static Point[] thrusterPoints = {new Point(-3f, 2f), shipBottom, new Point(3f, 2f), new Point(0f, 7f)};
    private static PolyLine thrusterPolyLine = new PolyLine(thrusterPoints);

    private Sound engineSound;
    private Sound dieSound;

    // The remains of the ship when it is destroyed
    private Remains[] remains;

    public Ship() {
        reinitialize();
        initSounds();
    }

    public void reinitialize() {
        x = GameElement.getWidth() / 2f;
        y = GameElement.getHeight() / 2f;
        vx = 0f;
        vy = 0f;
        rot = 0f;
        vrot = 0f;
        dying = false;
        age = 0f;
    }

    public float tipX() {
        return shipTop.x;
    }

    public float tipY() {
        return shipTop.y;
    }

    public float rot() {
        return rot;
    }

    public float getY() {
        return y;
    }

    public void paint(Surface surface) {
        if (!dying) {
            surface.setFillColor(0xFFFFFF);
            shipPolyLine.transform(rot, x, y).paint(surface);
            if (accelerating && !dying)
                thrusterPolyLine.transform(rot, x, y).paint(surface);
        } else if (!isDead())
            for (Remains r : remains)
                r.paint(surface);
    }

    public void accelerate(boolean on) {
        accelerating = on;
    }

    public void steerRight(boolean on) {
        steeringRight = on;
    }

    public void steerLeft(boolean on) {
        steeringLeft = on;
    }

    public void update(float delta) {
        if (!dying) {
            regardPiloting(delta);
            limitVelocity();
            updatePosition(delta);
        } else {
            progressDeath(delta);
            for (Remains r : remains)
                r.update(delta);
        }
    }

    public void die() {
        initRemains();
        engineSound.stop();
        dieSound.play();
        dying = true;
    }

    public boolean isDead() {
        return (age >= TTD);
    }

    public boolean isDisabled() {
        return dying;
    }

    private void regardPiloting(float delta) {
        if (accelerating) {
            vx -= Math.sin(rot) * delta / 1000f * ACCELERATION;
            vy -= Math.cos(rot) * delta / 1000f * ACCELERATION;
        }

        vrot = 0f;
        if (steeringRight)
            vrot -= ROTATION_SPEED;
        if (steeringLeft)
            vrot += ROTATION_SPEED;

        if (accelerating != engineSound.isPlaying()) {
            if (accelerating)
                engineSound.play();
            else
                engineSound.stop();
        }
    }

    private void limitVelocity() {
        float v = (float) Math.sqrt(vx * vx + vy * vy);
        if (v > MAX_SPEED) {
            float factor = v / MAX_SPEED;
            vx /= factor;
            vy /= factor;
        }
    }

    private void progressDeath(float delta) {
        age += delta;
    }

    private void initSounds() {
        engineSound = SoundPlayer.getSound("Engines");
        engineSound.setLooping(true);
        dieSound = SoundPlayer.getSound("Die 2");
    }

    private void initRemains() {
        remains = new Remains[4];
        remains[0] = new Remains(new Line(shipTop, shipRight), x, y, vx, vy, rot);
        remains[1] = new Remains(new Line(shipRight, shipBottom), x, y, vx, vy, rot);
        remains[2] = new Remains(new Line(shipBottom, shipLeft), x, y, vx, vy, rot);
        remains[3] = new Remains(new Line(shipLeft, shipTop), x, y, vx, vy, rot);
    }

}
