package net.littlecoder.core;

import java.util.ArrayDeque;
import java.util.Iterator;

import playn.core.Surface;

class BulletManager {

    private ArrayDeque<Bullet> activeBullets;
    private ArrayDeque<Bullet> inactiveBullets;

    private Surface surface;

    public BulletManager(Surface surface) {
	this.surface = surface;
	activeBullets = new ArrayDeque<Bullet>();
	inactiveBullets = new ArrayDeque<Bullet>();
    }

    public void addBullet(Ship ship) {
	if (inactiveBullets.isEmpty())
	    activeBullets.add(new Bullet(ship));
	else {
	    Bullet b = inactiveBullets.removeFirst();
	    b.reinitialize(ship);
	    activeBullets.add(b);
	}
    }

    public void paint(float alpha) {
	Iterator i = activeBullets.iterator();
	while (i.hasNext()) {
	    Bullet b = (Bullet)i.next();
	    b.paint(alpha);
	}
    }

    public void update(float delta) {
	Iterator i = activeBullets.iterator();
	while (i.hasNext()) {
	    Bullet b = (Bullet)i.next();
	    b.update(delta);
	    if (b.isDead()) {
		inactiveBullets.add(b);
		i.remove();
	    }
	}	
    }

    public int detectCollisions(AsteroidManager asteroidManager) {
	int score = 0;

	Iterator i = asteroidManager.asteroids().iterator();
	while (i.hasNext()) {
	    Asteroid a = (Asteroid)i.next();
	    Iterator j = activeBullets.iterator();
	    while (j.hasNext()) {
		Bullet b = (Bullet)j.next();
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
