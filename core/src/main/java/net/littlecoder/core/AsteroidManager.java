
package net.littlecoder.core;

import java.util.ArrayList;

import playn.core.Surface;

class AsteroidManager {

    private Surface surface;
    private ArrayList<Asteroid> active;
    private ArrayList<Asteroid> inactive;

    public AsteroidManager(Surface surface) {
	this.surface = surface;
	active = new ArrayList<Asteroid>();
	inactive = new ArrayList<Asteroid>();
	SoundPlayer.loadSound("Die");
    }

    public void initLevel(int level) {
	for (int i = 0; i < level + 2; i++)
	    active.add(new Asteroid(surface));
    }

    public Asteroid createAsteroid(byte size) {
	Asteroid res;

	if (inactive.isEmpty())
	    res = new Asteroid(size, surface);
	else {
	    res = inactive.remove(0);
	    res.reinitialize(size);
	}
	    
	return res;
    }

    public Asteroid createAsteroid(Asteroid parent) {
	Asteroid res;

	if (inactive.isEmpty())
	    res = new Asteroid(parent);
	else {
	    res = inactive.remove(0);
	    res.reinitialize(parent);
	}
	    
	return res;
    }

    public void paint(float alpha) {
	for (int i = 0; i < active.size(); i++)
	    active.get(i).paint(alpha);
    }

    public void update(float delta) {
    	ArrayList<Asteroid> newAsteroids = new ArrayList<Asteroid>();

	for (int i = active.size() - 1; i >= 0; i-- ) {
	    Asteroid a = active.get(i);
	    if (a.isDead()) {
		Asteroid[] children = a.spawnChildren(this);
		for (Asteroid c : children)
		    newAsteroids.add(c);
		inactive.add(a);
		active.remove(i);
	    } else
		a.update(delta);
	}

	for (int i = 0; i < newAsteroids.size(); i++)
	    active.add(newAsteroids.get(i));
    }

    public boolean isCollidingWith(Ship ship) {
	for (int i = 0; i < active.size(); i++) 
	    if (active.get(i).isCollidingWith(ship))
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
