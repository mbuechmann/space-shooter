package net.littlecoder.core;

import java.util.ArrayDeque;
import java.util.ArrayList;

import playn.core.Surface;

class BulletManager {

    private ArrayList<Bullet> active;
    private ArrayList<Bullet> inactive;

    public BulletManager() {
        active = new ArrayList<Bullet>();
        inactive = new ArrayList<Bullet>();
    }

    public void addBullet(Ship ship) {
        if (inactive.isEmpty())
            active.add(new Bullet(ship));
        else {
            Bullet b = inactive.remove(0);
            b.reinitialize(ship);
            active.add(b);
        }
    }

    public void paint(Surface surface) {
        for (int i = 0; i < active.size(); i++)
            active.get(i).paint(surface);
    }

    public void update(float delta) {
        for (int i = active.size() - 1; i >= 0; i--) {
            Bullet b = active.get(i);
            b.update(delta);
            if (b.isDead()) {
                inactive.add(b);
                active.remove(i);
            }
        }
    }

    public int detectCollisions(AsteroidManager asteroidManager) {
        int score = 0;

        for (int i = 0; i < asteroidManager.asteroids().size(); i++) {
            Asteroid a = asteroidManager.asteroids().get(i);
            for (int j = 0; j < active.size(); j++) {
                Bullet b = active.get(j);
                if (!b.isDead() && a.isCollidingWith(b)) {
                    a.die();
                    b.die();
                    score += 3 - a.size();
                }
            }
        }

        return score;
    }

}
