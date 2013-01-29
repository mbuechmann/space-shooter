
package net.littlecoder.core.managers;

import java.util.ArrayList;
import java.util.Collections;

import net.littlecoder.core.game_elements.Asteroid;
import net.littlecoder.core.game_elements.Ship;
import net.littlecoder.core.util.SoundPlayer;
import playn.core.Surface;

public class AsteroidManager {

    private ArrayList<Asteroid> active;
    private ArrayList<Asteroid> inactive;

    public AsteroidManager() {
        active = new ArrayList<Asteroid>();
        inactive = new ArrayList<Asteroid>();
        SoundPlayer.loadSound("Die");
    }

    public void initLevel(int level) {
        for (int i = 0; i < level + 2; i++)
            active.add(new Asteroid());
    }

    public void paint(Surface surface) {
        for (Asteroid a : active) a.paint(surface);
    }

    public void update(float delta) {
        ArrayList<Asteroid> newAsteroids = new ArrayList<Asteroid>();

        for (int i = active.size() - 1; i >= 0; i--) {
            Asteroid a = active.get(i);
            if (a.isDead()) {
                Asteroid[] children = a.spawnChildren();
                Collections.addAll(newAsteroids, children);
                inactive.add(a);
                active.remove(i);
            } else
                a.update(delta);
        }

        for (Asteroid a : newAsteroids) active.add(a);
    }

    public boolean isCollidingWith(Ship ship) {
        for (Asteroid a : active)
            if (a.isCollidingWith(ship))
                return true;

        return false;
    }

    public boolean isEmpty() {
        return active.isEmpty();
    }

    public ArrayList<Asteroid> asteroids() {
        return active;
    }

}
