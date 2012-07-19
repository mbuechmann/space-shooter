
package net.littlecoder.core;

import java.util.ArrayDeque;
import java.util.Iterator;

import playn.core.Surface;

class AsteroidManager {

    private Surface surface;
    private ArrayDeque<Asteroid> activeAsteroids;
    private ArrayDeque<Asteroid> inactiveAsteroids;

    public AsteroidManager(Surface surface) {
	this.surface = surface;
	activeAsteroids = new ArrayDeque<Asteroid>();
	inactiveAsteroids = new ArrayDeque<Asteroid>();
	SoundPlayer.loadSound("Die");
    }

    public void initLevel(int level) {
	for (int i = 0; i < level + 2; i++)
	    activeAsteroids.add(new Asteroid(surface));
    }

    public Asteroid createAsteroid(byte size) {
	Asteroid res;

	if (inactiveAsteroids.isEmpty())
	    res = new Asteroid(size, surface);
	else {
	    res = inactiveAsteroids.removeFirst();
	    res.reinitialize(size);
	}
	    
	return res;
    }

    public Asteroid createAsteroid(Asteroid parent) {
	Asteroid res;

	if (inactiveAsteroids.isEmpty())
	    res = new Asteroid(parent);
	else {
	    res = inactiveAsteroids.removeFirst();
	    res.reinitialize(parent);
	}
	    
	return res;
    }

    public void paint(float alpha) {
	Iterator i = activeAsteroids.iterator();
	while (i.hasNext()) {
	    Asteroid a = (Asteroid)i.next();
	    a.paint(alpha);
	}
    }

    public void update(float delta) {
    	ArrayDeque<Asteroid> newAsteroids = new ArrayDeque<Asteroid>();

	Iterator i = activeAsteroids.iterator();
	while (i.hasNext()) {
	    Asteroid a = (Asteroid)i.next();
	    if (a.isDead()) {
		Asteroid[] children = a.spawnChildren(this);
		for (Asteroid c : children)
		    newAsteroids.add(c);
		inactiveAsteroids.add(a);
		i.remove();
	    } else
		a.update(delta);
	}

	i = newAsteroids.iterator();
	while (i.hasNext()) {
	    Asteroid a = (Asteroid)i.next();
	    activeAsteroids.add(a);		
	}
    }

    public boolean isCollidingWith(Ship ship) {
	Iterator i = activeAsteroids.iterator();
	while (i.hasNext()) {
	    Asteroid a = (Asteroid)i.next();
	    if (a.isCollidingWith(ship))
		return true;
	}

	return false;
    }

    public boolean isEmpty() {
	return activeAsteroids.isEmpty();
    }

    public ArrayDeque<Asteroid> asteroids() {
	return activeAsteroids;
    }

}
