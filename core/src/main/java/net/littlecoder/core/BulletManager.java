package net.littlecoder.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

import playn.core.Surface;

class BulletManager {

    private ArrayList<Bullet> active;
    private ArrayList<Bullet> inactive;

    private Surface surface;

    public BulletManager(Surface surface) {
	this.surface = surface;
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

    public void paint(float alpha) {
	for (int i = 0; i < active.size(); i++)
	    active.get(i).paint(alpha);
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

	Iterator i = asteroidManager.asteroids().iterator();
	while (i.hasNext()) {
	    Asteroid a = (Asteroid)i.next();
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
