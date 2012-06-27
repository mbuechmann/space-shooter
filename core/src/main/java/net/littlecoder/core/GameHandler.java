package net.littlecoder.core;

import static playn.core.PlayN.*;

import java.util.ArrayDeque;
import java.util.Iterator;

import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Surface;

class GameHandler implements Keyboard.Listener {

    private Surface surface;

    private Ship ship;
    private ArrayDeque<Asteroid> asteroids;
    private ArrayDeque<Bullet> bullets;
    private boolean shooting = false;

    private byte lifes = 3;
    private Polyline shipPolyline;
 
    public GameHandler(Surface surface) {
	this.surface = surface;
	ship = new Ship(surface);
	asteroids = new ArrayDeque<Asteroid>();
	for (int i = 0; i < 10; i++)
	    asteroids.add(new Asteroid((byte)2, surface));
	bullets = new ArrayDeque<Bullet>();

	shipPolyline = ship.shipPolyline.clone();

	keyboard().setListener(this);
    }

    public void paint(float alpha) {
	surface.clear();
	
	if (ship != null)
	    ship.paint(alpha);

	Iterator i = bullets.iterator();
	while (i.hasNext()) {
	    Bullet b = (Bullet)i.next();
	    b.paint(alpha);
	}

	i = asteroids.iterator();
	while (i.hasNext()) {
	    Asteroid a = (Asteroid)i.next();
	    a.paint(alpha);
	}

	for (int l = 0; l < lifes; l++)
	    shipPolyline.transform(0f, 20f + l * 20f, 20f).paint(surface);
    }

    public void update(float delta) {
	updateShip(delta);
	updateBullets(delta);
	updateAsteroids(delta);
	detectCollisions(delta);
    }

    public void onKeyDown(Keyboard.Event event) {
	if (ship != null) {
	    if (event.key() == Key.UP)
		ship.accelerate(true);
	    if (event.key() == Key.RIGHT)
		ship.steerRight(true);
	    if (event.key() == Key.LEFT)
		ship.steerLeft(true);
	} else {
	    if (event.key() == Key.SPACE)
		ship = new Ship(surface);
	}

	if (ship != null && !ship.isDisabled() && event.key() == Key.SPACE)
	    shooting = true;
	
	if (event.key() == Key.ESCAPE)
	    System.exit(0);
    }

    public void onKeyUp(Keyboard.Event event) {
	if (ship != null) {
	    if (event.key() == Key.UP)
		ship.accelerate(false);
	    if (event.key() == Key.RIGHT)
		ship.steerRight(false);
	    if (event.key() == Key.LEFT)
		ship.steerLeft(false);
	}
    }
    
    public void onKeyTyped(Keyboard.TypedEvent event) {}

    private void updateBullets(float delta) {
	Iterator i = bullets.iterator();
	while (i.hasNext()) {
	    Bullet b = (Bullet)i.next();
	    b.update(delta);
	    if (b.isDead())
		i.remove();
	}

 	if (shooting) {
	    bullets.add(new Bullet(ship));
	    shooting = false;
	}
   }

    private void updateShip(float delta) {
	if (ship != null) {
	    ship.update(delta);
	    if (ship.isDead()) {
		lifes --;
		ship = null;
	    }
	}
    }

    private void updateAsteroids(float delta) {
	ArrayDeque<Asteroid> newAsteroids = new ArrayDeque<Asteroid>();

	Iterator i = asteroids.iterator();
	while (i.hasNext()) {
	    Asteroid a = (Asteroid)i.next();
	    if (a.isDead()) {
		Asteroid[] children = a.spawnChildren();
		for (Asteroid c : children)
		    newAsteroids.add(c);
		i.remove();
	    } else
		a.update(delta);
	}

	i = newAsteroids.iterator();
	while (i.hasNext())
	    asteroids.add((Asteroid)i.next());
    }

    private void detectCollisions(float delta) {
	Iterator i = asteroids.iterator();
	while (i.hasNext()) {
	    Asteroid a = (Asteroid)i.next();
	    Iterator j = bullets.iterator();
	    while (j.hasNext()) {
		Bullet b = (Bullet)j.next();
		if (!b.isDead() && a.isCollidingWith(b)) {
		    a.die();
		    b.die();
		}
	    }
	}

	if (ship != null) {
	    i = asteroids.iterator();
	    while (i.hasNext()) {
		Asteroid a = (Asteroid)i.next();
		if (a.isCollidingWith(ship))
		    ship.die();
	    }
	}

    }

}